package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

public interface IDateTradeRecordServer {

    ServiceResult queryForPage(TradeRecordQueryObject qo);

    ServiceResult init();

    ServiceResult excelInfo(MultipartFile file);
}
