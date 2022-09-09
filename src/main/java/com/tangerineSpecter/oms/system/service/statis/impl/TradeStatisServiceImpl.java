package com.tangerinespecter.oms.system.service.statis.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.PeriodEnum;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 丢失的橘子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TradeStatisServiceImpl implements ITradeStatisService {

    private final DataTradeRecordMapper tradeRecordMapper;

    @Override
    public TradeStatisIncomeInfoDto incomeValueStatisInfo(Integer type) {
        TradeStatisIncomeInfoDto incomeInfo = new TradeStatisIncomeInfoDto();
        Map<String, List<DataTradeRecord>> tradeRecordMap;
        //时间倒序排，最近的时间在前面，默认每日，2为每月
        if (PeriodEnum.MONTHLY.getValue().equals(type)) {
            tradeRecordMap = this.getMonthlyDataMap(incomeInfo);
        } else {
            tradeRecordMap = this.getDailyDataMap(incomeInfo);
        }
        CollUtils.forEach(tradeRecordMap.keySet(), date -> incomeInfo.initAllIncome(date, tradeRecordMap.get(date)));
        return incomeInfo;
    }

    /**
     * 获取每天数据映射
     *
     * @return 数据映射
     */
    private Map<String, List<DataTradeRecord>> getDailyDataMap(TradeStatisIncomeInfoDto incomeInfo) {
        List<DataTradeRecord> tradeRecords = tradeRecordMapper.selectRecentListByType(UserContext.getUid(), 30);
        List<String> dates = tradeRecords.stream().map(DataTradeRecord::getDate).distinct().limit(30).collect(Collectors.toList());
        incomeInfo.setDate(dates);
        return CollUtils.convertMultiLinkedHashMap(tradeRecords, DataTradeRecord::getDate);
    }

    /**
     * 获取月度数据映射
     *
     * @return 数据映射
     */
    private Map<String, List<DataTradeRecord>> getMonthlyDataMap(TradeStatisIncomeInfoDto incomeInfo) {
        //最近1年的月统计
        DateTime offsetMonth = DateUtil.offsetMonth(new Date(), -11);
        DateTime beginOfMonth = DateUtil.beginOfMonth(offsetMonth);
        List<DataTradeRecord> tradeRecords = tradeRecordMapper.selectListByDate(beginOfMonth.toString(), DateUtil.now());
        List<DateTime> monthDate = DateUtil.rangeToList(beginOfMonth, new Date(), DateField.MONTH);
        CollUtils.convertList(CollUtil.reverse(monthDate), dateTime -> incomeInfo.getDate().add(DateUtil.format(dateTime, DatePattern.NORM_MONTH_PATTERN)));
        return CollUtils.convertMultiLinkedHashMap(tradeRecords, tradeRecord -> DateUtil.parse(tradeRecord.getDate()).toString(DatePattern.NORM_MONTH_PATTERN));
    }

    /**
     * 总资金计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public Integer sumMoney(List<DataTradeRecord> tradeRecords) {
        return tradeRecords.stream().mapToInt(tradeRecord -> tradeRecord.sumMoney(tradeRecord.getEndMoney(), tradeRecord.getCurrency()).getInteger()).sum();
    }

    /**
     * 总收益计算
     *
     * @param tradeRecords 资金数据列表
     * @return 总资金
     */
    public int sumIncome(List<DataTradeRecord> tradeRecords) {
        return CollUtils.convertSumList(tradeRecords, tradeRecord -> tradeRecord.sumMoney(tradeRecord.getIncomeValue(), tradeRecord.getCurrency()).getInteger());
    }

}
