package com.tangerinespecter.oms.system.service.data.impl;

import com.tangerinespecter.oms.common.query.StockPortfolioQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.data.IDataStockPortfolioService;
import org.springframework.stereotype.Service;

@Service
public class DataStockPortfolioServiceImpl implements IDataStockPortfolioService {

    @Override
    public ServiceResult queryForPage(StockPortfolioQueryObject qo) {
        return ServiceResult.success();
    }
}
