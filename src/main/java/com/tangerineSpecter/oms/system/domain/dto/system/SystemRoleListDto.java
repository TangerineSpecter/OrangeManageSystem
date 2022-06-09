package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemRoleListDto {

    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 状态（0：冻结；1：可用）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 权限列表
     */
    private List<SystemPermission> permissions = new ArrayList<>();
    /**
     * 角色拥有权限id列表
     */
    private List<Long> havePermissionIds;

    /**
     * 初始化角色用有的权限
     *
     * @param permissions       权限列表
     * @param havePermissionIds 拥有的权限ids
     */
    public void initPermission(List<SystemPermission> permissions, List<Long> havePermissionIds) {
        this.permissions = permissions;
        this.havePermissionIds = havePermissionIds;
    }
}
