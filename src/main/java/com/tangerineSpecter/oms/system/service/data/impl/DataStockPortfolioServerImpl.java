package com.tangerinespecter.oms.system.service.data.impl;

import com.tangerinespecter.oms.common.query.StockPortfolioQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.data.IDataStockPortfolioServer;
import org.springframework.stereotype.Service;

@Service
public class DataStockPortfolioServerImpl implements IDataStockPortfolioServer {

    @Override
    public ServiceResult queryForPage(StockPortfolioQueryObject qo) {
        return ServiceResult.success();
    }
}
