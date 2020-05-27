package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.domain.vo.system.SystemConfigInfoVo;

public interface ISystemConfigService {

    ServiceResult insertInfo(SystemConfigInfoVo systemConfig);

    SystemConfig configInfo();
}
