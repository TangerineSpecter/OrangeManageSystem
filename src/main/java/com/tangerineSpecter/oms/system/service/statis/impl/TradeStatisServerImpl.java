package com.tangerinespecter.oms.system.service.statis.impl;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.statis.TradeStatisIncomeInfoDto;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeStatisServerImpl implements ITradeStatisServer {

    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;

    @Override
    public ServiceResult incomeValueStatisInfo() {
        List<DataTradeRecord> dataTradeRecords = dataTradeRecordMapper.queryTotalIncomeByDay();
        List<BigDecimal> totalIncome = dataTradeRecords.stream().map(DataTradeRecord::getIncomeValue).collect(Collectors.toList());
        List<String> date = dataTradeRecords.stream().map(DataTradeRecord::getDate).collect(Collectors.toList());
        TradeStatisIncomeInfoDto infoDto = TradeStatisIncomeInfoDto.builder().totalIncome(totalIncome).date(date).build();
        return ServiceResult.success(infoDto);
    }
}
