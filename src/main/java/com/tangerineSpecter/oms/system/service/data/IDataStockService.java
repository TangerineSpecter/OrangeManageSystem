package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface IDataStockService {

    ServiceResult queryForPage(StockQueryObject qo);
}
