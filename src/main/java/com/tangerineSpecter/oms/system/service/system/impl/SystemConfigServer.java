package com.tangerinespecter.oms.system.service.system.impl;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.service.system.ISystemConfigServer;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServer implements ISystemConfigServer {

    @Override
    public ServiceResult insertInfo(SystemConfig systemConfig) {
        return null;
    }

    @Override
    public SystemConfig configInfo() {
        return null;
    }
}
