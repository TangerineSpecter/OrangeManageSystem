package com.tangerinespecter.oms.system.service.data.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataStock;
import com.tangerinespecter.oms.system.mapper.DataStockMapper;
import com.tangerinespecter.oms.system.service.data.IDataStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataStockServiceImpl implements IDataStockService {

    @Resource
    private DataStockMapper dataStockMapper;

    @Override
    public PageInfo<DataStock> queryForPage(StockQueryObject qo) {
        return PageMethod.startPage(qo.getPage(), qo.getLimit())
                .doSelectPageInfo(() -> dataStockMapper.queryForPage(qo));
    }
}
