package com.tangerinespecter.oms.system.service.statis.impl;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeStatisServiceImpl implements ITradeStatisService {

    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;

    @Override
    public ServiceResult incomeValueStatisInfo() {
        List<DataTradeRecord> dataTradeRecords = dataTradeRecordMapper.queryTotalIncomeByDay(null);
        List<DataTradeRecord> stockRecords = dataTradeRecordMapper.queryTotalIncomeByDay(TradeRecordTypeEnum.STOCK_TYPE.getType());
        List<DataTradeRecord> futuresRecords = dataTradeRecordMapper.queryTotalIncomeByDay(TradeRecordTypeEnum.FUTURES_TYPE.getType());
        List<DataTradeRecord> foreignRecords = dataTradeRecordMapper.queryTotalIncomeByDay(TradeRecordTypeEnum.FOREIGN_EXCHANGE_TYPE.getType());
        List<BigDecimal> totalIncome = dataTradeRecords.stream().map(DataTradeRecord::getIncomeValue).collect(Collectors.toList());
        List<BigDecimal> stockData = stockRecords.stream().map(DataTradeRecord::getIncomeValue).collect(Collectors.toList());
        List<BigDecimal> futuresData = futuresRecords.stream().map(DataTradeRecord::getIncomeValue).collect(Collectors.toList());
        List<BigDecimal> foreignData = foreignRecords.stream().map(DataTradeRecord::getIncomeValue).collect(Collectors.toList());
        List<String> date = dataTradeRecords.stream().map(DataTradeRecord::getDate).collect(Collectors.toList());
        TradeStatisIncomeInfoDto infoDto = TradeStatisIncomeInfoDto.builder().totalIncome(totalIncome)
                .stockData(stockData).futuresData(futuresData).foreignData(foreignData).date(date).build();
        return ServiceResult.success(infoDto);
    }
}
