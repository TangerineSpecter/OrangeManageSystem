package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.dto.system.UserPermissionListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemPermissionMapper extends BaseMapper<SystemPermission> {

    /**
     * 获取当前用户的权限列表
     *
     * @param uid 用户ID
     * @return 权限列表
     */
    List<UserPermissionListDto> getPermissionListByUid(@Param("uid") Long uid);

    /**
     * 根据角色ID获取权限列表
     *
     * @param roleId
     * @return
     */
    List<SystemPermission> selectListByRoleId(@Param("rid") Long roleId);
}
