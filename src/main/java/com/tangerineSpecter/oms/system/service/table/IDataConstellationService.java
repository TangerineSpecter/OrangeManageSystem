package com.tangerinespecter.oms.system.service.table;

import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface IDataConstellationService {

    /**
     * 分页查询
     */
    ServiceResult queryForPage(ConstellationQueryObject qo);
}
