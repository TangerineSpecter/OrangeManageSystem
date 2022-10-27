package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemPermissionQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.service.BaseService;

public interface IPermissionManageService extends BaseService<SystemPermissionQueryObject, SystemPermission> {

    /**
     * 权限初始化
     */
    void init();

    /**
     * 删除权限
     *
     * @param code 权限code
     */
    void deleteInfo(String code);
}
