package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface IRoleManageService {

    ServiceResult querySystemRoleList(SystemRoleQueryObject qo);
}
