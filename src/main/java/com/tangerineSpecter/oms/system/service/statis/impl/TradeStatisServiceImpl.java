package com.tangerinespecter.oms.system.service.statis.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.query.TradeStatisQueryObject;
import com.tangerinespecter.oms.common.redis.RedisKey;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.entity.StatisTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.mapper.DataExchangeRateMapper;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.mapper.StatisTradeRecordMapper;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 丢失的橘子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TradeStatisServiceImpl implements ITradeStatisService {

    private final ISystemUserService userService;

    private final DataExchangeRateMapper exchangeRateMapper;
    private final DataTradeRecordMapper tradeRecordMapper;
    private final StatisTradeRecordMapper statisTradeMapper;

    private final SqlSessionFactory sqlSessionFactory;
    private final ApplicationEventPublisher publisher;
    private final RedisHelper redisHelper;

    @Async
    @Override
    public void statisTradeData(Integer day, String uid) {
        //同步账号列表
        List<String> userids = userService.getUseridList(uid);
        //往前偏移，取反
        String offsetDay = day == null ? null : DateUtil.offsetDay(new Date(), -day).toDateStr();
        this.handleStatisData(offsetDay, userids);
    }

    @Async
    @Override
    public void statisTradeDataByDate(String date, String uid) {
        List<String> userids = userService.getUseridList(uid);
        this.handleStatisData(date, userids);
        //移除缓存
        redisHelper.delete(RedisKey.TRADE_STATIS_KEY.join(UserContext.getUid()));
    }

    private void handleStatisData(String date, List<String> uidList) {
        //如果数据为空，则优先初始化全部数据
        CollUtils.forEach(uidList, uid -> {
            boolean haveStatisData = statisTradeMapper.checkHaveDataByUid(uid);
            log.info("开始统计用户[{}]交易记录，是否拥有历史统计数据[{}]", uid, haveStatisData);
            String startDate = haveStatisData ? date : null;

            //获取交易记录数据
            final List<DataTradeRecord> dbTradeRecords = tradeRecordMapper.selectListByStartDate(startDate, uid);
            final List<String> recordDateList = CollUtils.convertDistinctList(dbTradeRecords, DataTradeRecord::getDate);

            //查询拥有统计数据的时间集合
            final List<StatisTradeRecord> dbStatisRecords = statisTradeMapper.selectListByStartDate(startDate, uid);
            final Map<String, StatisTradeRecord> dbStatisMap = CollUtils.convertMap(dbStatisRecords, StatisTradeRecord::getDate, s -> s);

            SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            try {
                StatisTradeRecordMapper sessionStatisMapper = sqlSession.getMapper(StatisTradeRecordMapper.class);
                //初始化开始统计时间的上一条累计收益
                StatisTradeRecord prevData = sessionStatisMapper.selectPrevOneByDate(startDate, uid);
                for (String recordDate : recordDateList) {
                    log.info("开始进行统计数据处理，时间[{}]", recordDate);
                    //数据入库
                    final StatisTradeRecord statisData = this.calStatisTrade(sqlSession, uid, recordDate, prevData);
                    //校验是否db存在数据
                    statisData.setId(dbStatisMap.getOrDefault(recordDate, new StatisTradeRecord()).getId());
                    sessionStatisMapper.insertOrUpdate(statisData);
                    prevData = statisData;
                }
                sqlSession.commit();
            } catch (Exception e) {
                log.error("用户[{}]交易统计数据处理异常，异常信息：[{}]，" + e, uid, e.getMessage());
                sqlSession.rollback();
            } finally {
                sqlSession.close();
            }
        });
    }

    /**
     * 统计数据计算
     *
     * @param uid        统计账号uid
     * @param statisDate 当前统计日期，yyyy-MM-dd
     * @param prevData   上一条统计数据
     * @return 统计数据
     */
    private StatisTradeRecord calStatisTrade(SqlSession sqlSession, String uid, String statisDate,
                                             StatisTradeRecord prevData) {
        DataTradeRecordMapper sessionTradeRecordMapper = sqlSession.getMapper(DataTradeRecordMapper.class);
        DataExchangeRateMapper sessionExchangeRateMapper = sqlSession.getMapper(DataExchangeRateMapper.class);

        //查出所有类型数据近期记录数据
        final List<DataTradeRecord> recordList = sessionTradeRecordMapper.selectRecentListByDate(uid, statisDate);
        //获取记录时间最近的利率
        List<DataExchangeRate> dataExchangeRates = sessionExchangeRateMapper.selectListByLastRecordTime(statisDate, null);
        final Map<String, BigDecimal> exchangeRateMap = CollUtils.convertMap(dataExchangeRates, DataExchangeRate::getCode, DataExchangeRate::getPrice);
        CollUtils.forEach(recordList, data -> data.setExchangeRate(NumChainCal
            .startOf(exchangeRateMap.get(data.getCurrency())).div(100).getBigDecimal()));

        //初始化统计信息
        final StatisTradeRecord statisData = new StatisTradeRecord();
        //当天合计资金由于可能无记录，采用最近所有类型合计
        final Integer todayEndMoney = this.sumMoney(recordList);
        //过滤出当天的记录数据，收益按照当天数据结算
        final List<DataTradeRecord> todayRecordList = CollUtils.filterList(recordList, data -> Objects.equals(data.getDate(), statisDate));
        final int todayIncome = this.sumIncome(todayRecordList);
        //起始资金 = 结算 - 收益
        final int todayStartMoney = todayEndMoney - todayIncome;
        final BigDecimal incomeRate = NumChainCal.startOf(todayIncome).div(todayStartMoney).mul(100).getBigDecimal(2);
        //设置数据
        statisData.setUid(uid);
        statisData.setDate(statisDate);
        statisData.setMoney(todayEndMoney);
        statisData.setIncomeValue(todayIncome);
        statisData.setDeposit(this.sumDeposit(todayRecordList));
        statisData.setWithdrawal(this.sumWithdrawal(todayRecordList));
        statisData.setTotalDeposit(prevData.getTotalDeposit() + statisData.getDeposit());
        statisData.setTotalWithdrawal(prevData.getTotalWithdrawal() + statisData.getWithdrawal());
        statisData.setIncomeRate(incomeRate);
        statisData.setTotalIncomeValue(prevData.getTotalIncomeValue() + todayIncome);
        //处理年统计
        this.calYearData(sqlSession, statisData);
        return statisData;
    }

    private void calYearData(SqlSession sqlSession, StatisTradeRecord statisData) {
        StatisTradeRecordMapper sessionStatisTradeMapper = sqlSession.getMapper(StatisTradeRecordMapper.class);

        //定义数据
        final String date = statisData.getDate();
        final String uid = statisData.getUid();
        final Date curDate = DateUtil.parseDate(date);

        int thisYear = DateUtil.year(curDate);
        int yearIncome = sessionStatisTradeMapper.sumIncomeValueByYear(thisYear, date, uid) + statisData.getIncomeValue();
        int lastYearMoney = sessionStatisTradeMapper.selectMoneyByYear(thisYear, uid);
        lastYearMoney = lastYearMoney == 0 ? (statisData.getMoney() - statisData.getIncomeValue()) : lastYearMoney;
        BigDecimal yearIncomeRate = NumChainCal.startOf(yearIncome).div(lastYearMoney).mul(100).getBigDecimal(2);

        statisData.setYear(thisYear);
        statisData.setYearIncomeValue(yearIncome);
        statisData.setYearIncomeRate(yearIncomeRate);
        //计算月统计
        this.calMonthData(sqlSession, statisData);
    }

    private void calMonthData(SqlSession sqlSession, StatisTradeRecord statisData) {
        StatisTradeRecordMapper sessionStatisTradeMapper = sqlSession.getMapper(StatisTradeRecordMapper.class);

        //定义数据
        final String date = statisData.getDate();
        final String uid = statisData.getUid();
        final Date curDate = DateUtil.parseDate(date);
        int thisYear = statisData.getYear();

        int thisMonth = DateUtil.month(curDate) + 1;
        int monthIncome = sessionStatisTradeMapper.sumIncomeValueByMonth(thisYear, thisMonth, date, uid) + statisData.getIncomeValue();
        int lastMonthMoney = sessionStatisTradeMapper.selectMoneyByMonth(curDate, uid);
        lastMonthMoney = lastMonthMoney == 0 ? (statisData.getMoney() - statisData.getIncomeValue()) : lastMonthMoney;
        BigDecimal monthIncomeRate = NumChainCal.startOf(monthIncome).div(lastMonthMoney).mul(100).getBigDecimal(2);

        statisData.setMonth(thisMonth);
        statisData.setMonthIncomeValue(monthIncome);
        statisData.setMonthIncomeRate(monthIncomeRate);
        //计算周统计
        this.calWeekData(sqlSession, statisData);
    }

    private void calWeekData(SqlSession sqlSession, StatisTradeRecord statisData) {
        StatisTradeRecordMapper sessionStatisTradeMapper = sqlSession.getMapper(StatisTradeRecordMapper.class);

        int thisYear = statisData.getYear();
        int thisMonth = statisData.getMonth();
        String date = statisData.getDate();
        String uid = statisData.getUid();

        final Date curDate = DateUtil.parseDate(date);

        int thisWeek = DateUtil.weekOfYear(curDate);
        int weekIncome = sessionStatisTradeMapper.sumIncomeValueByWeek(thisYear, thisMonth, thisWeek, date, uid) + statisData.getIncomeValue();
        int lastWeekMoney = sessionStatisTradeMapper.selectMoneyByWeek(curDate, uid);
        lastWeekMoney = lastWeekMoney == 0 ? (statisData.getMoney() - statisData.getIncomeValue()) : lastWeekMoney;
        BigDecimal weekIncomeRate = NumChainCal.startOf(weekIncome).div(lastWeekMoney).mul(100).getBigDecimal(2);

        statisData.setWeekIncomeValue(weekIncome);
        statisData.setWeek(thisWeek);
        statisData.setWeekIncomeRate(weekIncomeRate);
    }

    @Override
    public TradeStatisIncomeInfoDto incomeValueStatisInfo(Integer type, TradeRecordTypeEnum tradeType) {
        TradeStatisIncomeInfoDto incomeInfo = new TradeStatisIncomeInfoDto();
        //统计数据
        final List<StatisTradeRecord> statisTradeRecords = statisTradeMapper.selectListByType(UserContext.getUid(), type);
        CollUtils.forEach(statisTradeRecords, incomeInfo::addTradeStatis);
        //查询单项
        final List<DataTradeRecord> tradeRecords = tradeRecordMapper.selectListByType(tradeType.getValue(), 30, false);
        CollUtils.forEach(tradeRecords, incomeInfo::addTradeData);
        return incomeInfo;
    }

    @Override
    public StatisTradeRecord getStatisData(String date) {
        return statisTradeMapper.selectOneByDate(date);
    }

    @Override
    public boolean deleteRefreshByDate(String date) {
        try {
            statisTradeMapper.deleteByDate(date);
            //重新计算数据
            publisher.publishEvent(date);
        } catch (Exception e) {
            log.error("处理统计数据异常，异常信息：{}，异常：" + e, e.getMessage());
        }
        return false;
    }

    @Override
    public List<StatisTradeRecord> list(TradeStatisQueryObject qo) {
        return statisTradeMapper.queryForPage(qo);
    }

    /**
     * 总资金计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public Integer sumMoney(List<DataTradeRecord> tradeRecords) {
        return tradeRecords.stream().mapToInt(DataTradeRecord::sumEndMoney).sum();
    }

    /**
     * 总出金计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public Integer sumWithdrawal(List<DataTradeRecord> tradeRecords) {
        return tradeRecords.stream()
            .mapToInt(data -> data.sumMoney(data.getWithdrawal(), data.getWithdrawalRate()).getInteger()).sum();
    }

    /**
     * 总入金计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public Integer sumDeposit(List<DataTradeRecord> tradeRecords) {
        return tradeRecords.stream()
            .mapToInt(data -> data.sumMoney(data.getDeposit(), data.getDepositRate()).getInteger()).sum();
    }

    /**
     * 总收益计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public int sumIncome(List<DataTradeRecord> tradeRecords) {
        return CollUtils.convertSumList(tradeRecords, DataTradeRecord::sumMoney2Int);
    }

}
