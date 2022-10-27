package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.SystemRoleListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.domain.vo.system.SystemRoleInfoVo;
import com.tangerinespecter.oms.system.service.BaseService;

import java.util.Set;

public interface IRoleManageService extends BaseService<SystemRoleQueryObject, SystemRoleListDto> {

    void insert(String name);

    void delete(Long id);

    Set<SystemPermission> getRolePermission(Long roleId);

    void authorize(SystemRoleInfoVo vo);

    void updateStatus(Long id);
}
