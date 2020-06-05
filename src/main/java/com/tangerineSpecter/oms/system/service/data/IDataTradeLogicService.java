package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface IDataTradeLogicService {

    ServiceResult queryForPage(TradeLogicQueryObject qo);
}
