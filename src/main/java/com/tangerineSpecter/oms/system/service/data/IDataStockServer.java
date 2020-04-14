package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface IDataStockServer {

    ServiceResult queryForPage(StockQueryObject qo);
}
