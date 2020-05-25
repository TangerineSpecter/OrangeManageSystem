package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.system.SystemRoleInfoVo;

public interface IRoleManageService {

    /**
     * 角色列表
     *
     * @param qo
     * @return
     */
    ServiceResult querySystemRoleList(SystemRoleQueryObject qo);

    ServiceResult insert(String name);

    ServiceResult update(SystemRoleInfoVo vo);

    ServiceResult delete(Long id);

    ServiceResult getRolePermission(Long roleId);

    ServiceResult authorize(SystemRoleInfoVo vo);

    ServiceResult updateStatus(Long id);
}
