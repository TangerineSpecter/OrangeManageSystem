package com.tangerinespecter.oms.system.service.statis.impl;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TradeStatisServiceImpl implements ITradeStatisService {

    @Resource
    private DataTradeRecordMapper tradeRecordMapper;

    @Override
    public TradeStatisIncomeInfoDto incomeValueStatisInfo() {
        TradeStatisIncomeInfoDto incomeInfo = new TradeStatisIncomeInfoDto();
        //时间倒序排，最近的时间在前面
        incomeInfo.setDate(tradeRecordMapper.selectTradeDateList(UserContext.getUid(), 30));
        List<DataTradeRecord> tradeRecords = tradeRecordMapper.selectListByDate(CollUtil.getLast(incomeInfo.getDate()), CollUtil.getFirst(incomeInfo.getDate()), UserContext.getUid());
        Map<String, DataTradeRecord> tradeRecordMap = CollUtils.convertMap(tradeRecords, tradeRecord -> tradeRecord.getDate() + tradeRecord.getType());
        CollUtils.forEach(incomeInfo.getDate(), date -> incomeInfo.setTradeData(
                this.sumMoney(tradeRecordMap.get(date + TradeRecordTypeEnum.STOCK_TYPE.getValue())),
                this.sumMoney(tradeRecordMap.get(date + TradeRecordTypeEnum.FUTURES_TYPE.getValue())),
                this.sumMoney(tradeRecordMap.get(date + TradeRecordTypeEnum.FOREIGN_EXCHANGE_TYPE.getValue())),
                this.sumMoney(tradeRecordMap.get(date + TradeRecordTypeEnum.FUND_TYPE.getValue())))
        );
        return incomeInfo;
    }

    /**
     * 总资金计算
     *
     * @param records 资金数据列表
     * @return 总资金
     */
    public Integer sumMoney(List<DataTradeRecord> records) {
        return records.stream().mapToInt(record -> sumMoney(record.getEndMoney(), record.getCurrency()).getInteger()).sum();
    }

    /**
     * 总收益计算
     *
     * @param records 资金数据列表
     * @return 总资金
     */
    public int sumIncome(List<DataTradeRecord> records) {
        return CollUtils.convertSumList(records, record -> this.sumMoney(record.getIncomeValue(), record.getCurrency()).getInteger());
    }

    /**
     * 单条数据计算
     *
     * @param tradeRecord 资金数据
     * @return 计算结果
     */
    private BigDecimal sumMoney(DataTradeRecord tradeRecord) {
        tradeRecord = Optional.ofNullable(tradeRecord).orElse(new DataTradeRecord());
        return this.sumMoney(tradeRecord.getIncomeValue(), tradeRecord.getCurrency()).getBigDecimal();
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
