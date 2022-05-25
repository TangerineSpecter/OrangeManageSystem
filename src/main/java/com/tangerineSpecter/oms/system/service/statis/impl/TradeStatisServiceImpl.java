package com.tangerinespecter.oms.system.service.statis.impl;

import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TradeStatisServiceImpl implements ITradeStatisService {

    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;

    @Override
    public TradeStatisIncomeInfoDto incomeValueStatisInfo() {
        TradeStatisIncomeInfoDto incomeInfo = new TradeStatisIncomeInfoDto();
        List<DataTradeRecord> dataTradeRecords = dataTradeRecordMapper.queryTotalIncomeByDay(null);
        CollUtils.forEach(dataTradeRecords, dataTradeRecord -> {
            incomeInfo.getTotalIncome().add(dataTradeRecord.getIncomeValue());
            incomeInfo.getDate().add(dataTradeRecord.getDate());
        });
        incomeInfo.setStockData(CollUtils.convertList(dataTradeRecordMapper.queryTotalIncomeByDay(TradeRecordTypeEnum.STOCK_TYPE.getValue()), DataTradeRecord::getIncomeValue));
        incomeInfo.setFuturesData(CollUtils.convertList(dataTradeRecordMapper.queryTotalIncomeByDay(TradeRecordTypeEnum.FUTURES_TYPE.getValue()), DataTradeRecord::getIncomeValue));
        incomeInfo.setForeignData(CollUtils.convertList(dataTradeRecordMapper.queryTotalIncomeByDay(TradeRecordTypeEnum.FOREIGN_EXCHANGE_TYPE.getValue()), DataTradeRecord::getIncomeValue));
        incomeInfo.setFundsData(CollUtils.convertList(dataTradeRecordMapper.queryTotalIncomeByDay(TradeRecordTypeEnum.FUND_TYPE.getValue()), DataTradeRecord::getIncomeValue));
        return incomeInfo;
    }
}
