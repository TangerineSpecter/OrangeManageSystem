package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
