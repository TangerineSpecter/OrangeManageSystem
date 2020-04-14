package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface IDateTradeRecordServer {

    ServiceResult queryForPage(TradeRecordQueryObject qo);
}
