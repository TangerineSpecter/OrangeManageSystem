package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemPermissionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;

public interface IPermissionManageService {

    ServiceResult queryForPage(SystemPermissionQueryObject qo);

    ServiceResult init();

    ServiceResult deleteInfo(String cpde);
}
