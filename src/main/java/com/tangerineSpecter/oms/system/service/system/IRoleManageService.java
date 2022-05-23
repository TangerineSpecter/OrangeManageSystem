package com.tangerinespecter.oms.system.service.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.SystemRoleQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemRoleListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import com.tangerinespecter.oms.system.domain.vo.system.SystemRoleInfoVo;

import java.util.Set;

public interface IRoleManageService {

    /**
     * 角色列表
     *
     * @param qo
     * @return
     */
    PageInfo<SystemRoleListDto> querySystemRoleList(SystemRoleQueryObject qo);

    void insert(String name);

    void delete(Long id);

    Set<SystemPermission> getRolePermission(Long roleId);

    void authorize(SystemRoleInfoVo vo);

    void updateStatus(Long id);
}
