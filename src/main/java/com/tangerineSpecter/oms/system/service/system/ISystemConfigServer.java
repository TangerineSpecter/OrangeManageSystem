package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;

public interface ISystemConfigServer {

    ServiceResult insertInfo(SystemConfig systemConfig);

    SystemConfig configInfo();
}
