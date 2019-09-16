package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemLogQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface ISystemLogService {

    /**
     * 系统日志
     *
     * @param qo
     * @return 日志列表
     */
    ServiceResult queryForPage(SystemLogQueryObject qo);
}
