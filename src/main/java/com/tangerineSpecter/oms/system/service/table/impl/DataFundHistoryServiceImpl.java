package com.tangerinespecter.oms.system.service.table.impl;

import com.tangerinespecter.oms.common.query.FundHistoryQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.mapper.DataFundHistoryMapper;
import com.tangerinespecter.oms.system.service.table.IDataFundHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataFundHistoryServiceImpl implements IDataFundHistoryService {

    private final DataFundHistoryMapper dataFundHistoryMapper;

    @Override
    public List<DataFundHistory> list(FundHistoryQueryObject qo) {
        return dataFundHistoryMapper.queryForPage(qo);
    }
}
