package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.StockPortfolioQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface IDataStockPortfolioService {

    ServiceResult queryForPage(StockPortfolioQueryObject qo);
}
