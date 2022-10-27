package com.tangerinespecter.oms.system.service.data.impl;

import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataStock;
import com.tangerinespecter.oms.system.mapper.DataStockMapper;
import com.tangerinespecter.oms.system.service.data.IDataStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataStockServiceImpl implements IDataStockService {

    @Resource
    private DataStockMapper dataStockMapper;

    @Override
    public List<DataStock> list(StockQueryObject qo) {
        return dataStockMapper.queryForPage(qo);
    }
}
