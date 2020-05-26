package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统角色表
 *
 * @author TangerineSpecter
 * @date 2019年09月16日01:11:57
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "system_role")
public class SystemRole {

    @TableId(type = IdType.AUTO)
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
    private Set<SystemPermission> permissions = new HashSet<>();

    private List<SystemMenu> menus;

}
