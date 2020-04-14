package com.tangerinespecter.oms.system.service.data.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataStock;
import com.tangerinespecter.oms.system.mapper.DataStockMapper;
import com.tangerinespecter.oms.system.service.data.IDataStockServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataStockServerImpl implements IDataStockServer {

    @Resource
    private DataStockMapper dataStockMapper;

    @Override
    public ServiceResult queryForPage(StockQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<DataStock> pageList = dataStockMapper.queryForPage(qo);
        PageInfo<DataStock> stockPageInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, stockPageInfo.getTotal());
    }
}
