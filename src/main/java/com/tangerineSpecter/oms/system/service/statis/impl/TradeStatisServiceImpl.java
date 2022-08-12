package com.tangerinespecter.oms.system.service.statis.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 丢失的橘子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TradeStatisServiceImpl implements ITradeStatisService {

    private final DataTradeRecordMapper tradeRecordMapper;

    public static void main(String[] args) {
        DateTime dateTime = DateUtil.offsetMonth(new Date(), -11);
        System.out.println(dateTime);
        final DateTime dateTime1 = DateUtil.beginOfMonth(dateTime);
        System.out.println(dateTime1);
        final List<DateTime> dateTimes = CollUtil.reverse(DateUtil.rangeToList(dateTime1, new Date(), DateField.MONTH));
        System.out.println(dateTimes);
    }

    @Override
    public TradeStatisIncomeInfoDto incomeValueStatisInfo(Integer type) {
        TradeStatisIncomeInfoDto incomeInfo = new TradeStatisIncomeInfoDto();
        List<DataTradeRecord> tradeRecords;
        Map<String, List<DataTradeRecord>> tradeRecordMap;
        //时间倒序排，最近的时间在前面，默认每日，2为每月
        if (type == 2) {
            //最近1年的月统计
            DateTime offsetMonth = DateUtil.offsetMonth(new Date(), -11);
            DateTime beginOfMonth = DateUtil.beginOfMonth(offsetMonth);
            tradeRecords = tradeRecordMapper.selectListByDate(beginOfMonth.toString(), DateUtil.now(), UserContext.getUid());
            List<DateTime> monthDate = DateUtil.rangeToList(beginOfMonth, new Date(), DateField.MONTH);
            CollUtils.convertList(CollUtil.reverse(monthDate), dateTime -> incomeInfo.getDate().add(DateUtil.format(dateTime, DatePattern.NORM_MONTH_PATTERN)));
            tradeRecordMap = CollUtils.convertMultiLinkerHashMap(tradeRecords, tradeRecord -> DateUtil.parse(tradeRecord.getDate()).toString(DatePattern.NORM_MONTH_PATTERN));
        } else {
            tradeRecords = tradeRecordMapper.selectRecentListByType(UserContext.getUid(), 30);
            List<String> dates = tradeRecords.stream().map(DataTradeRecord::getDate).distinct().limit(30).collect(Collectors.toList());
            incomeInfo.setDate(dates);
            tradeRecordMap = CollUtils.convertMultiLinkerHashMap(tradeRecords, DataTradeRecord::getDate);
        }

        CollUtils.forEach(incomeInfo.getDate(), date -> {
            NumChainCal numChainCal = NumChainCal.startOf(0);
            CollUtils.forEach(tradeRecordMap.get(date), tradeRecord -> {
                switch (TradeRecordTypeEnum.getType(tradeRecord.getType())) {
                    case STOCK_TYPE:
                        numChainCal.add(incomeInfo.initStockIncome(date, this.sumMoney(tradeRecord), this.sumTotalMoney(tradeRecord)));
                        break;
                    case FUTURES_TYPE:
                        numChainCal.add(incomeInfo.initFuturesIncome(date, this.sumMoney(tradeRecord), this.sumTotalMoney(tradeRecord)));
                        break;
                    case FOREIGN_EXCHANGE_TYPE:
                        numChainCal.add(incomeInfo.initForeignIncome(date, this.sumMoney(tradeRecord), this.sumTotalMoney(tradeRecord)));
                        break;
                    case FUND_TYPE:
                        numChainCal.add(incomeInfo.initFundsIncome(date, this.sumMoney(tradeRecord), this.sumTotalMoney(tradeRecord)));
                        break;
                    default:
                        break;
                }
            });
            incomeInfo.getTotalIncome().add(numChainCal.getBigDecimal());
        });
        return incomeInfo;
    }

    /**
     * 总资金计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public Integer sumMoney(List<DataTradeRecord> tradeRecords) {
        return tradeRecords.stream().mapToInt(tradeRecord -> sumMoney(tradeRecord.getEndMoney(), tradeRecord.getCurrency()).getInteger()).sum();
    }

    /**
     * 总收益计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public int sumIncome(List<DataTradeRecord> tradeRecords) {
        return CollUtils.convertSumList(tradeRecords, tradeRecord -> this.sumMoney(tradeRecord.getIncomeValue(), tradeRecord.getCurrency()).getInteger());
    }

    /**
     * 单条数据计算
     *
     * @param tradeRecord 资金数据
     * @return 计算结果
     */
    private BigDecimal sumMoney(DataTradeRecord tradeRecord) {
        tradeRecord = Optional.ofNullable(tradeRecord).orElse(new DataTradeRecord());
        return this.sumMoney(tradeRecord.getIncomeValue(), tradeRecord.getCurrency()).getBigDecimal(2);
    }

    /**
     * 单条累计数据计算
     *
     * @param tradeRecord 资金数据
     * @return 计算结果
     */
    private BigDecimal sumTotalMoney(DataTradeRecord tradeRecord) {
        tradeRecord = Optional.ofNullable(tradeRecord).orElse(new DataTradeRecord());
        return this.sumMoney(tradeRecord.getTotalIncomeValue(), tradeRecord.getCurrency()).getBigDecimal(2);
    }

    /**
     * 单条数据计算
     *
     * @param money    资金数据
     * @param currency 币种
     * @return 计算结果
     */
    private NumChainCal sumMoney(Integer money, String currency) {
        return NumChainCal.startOf(money)
                .mul(CommonConstant.EXCHANGE_RATE_MAP.getOrDefault(currency, new BigDecimal(1)))
                //单位分转元 除100
                .div(100);
    }

}
