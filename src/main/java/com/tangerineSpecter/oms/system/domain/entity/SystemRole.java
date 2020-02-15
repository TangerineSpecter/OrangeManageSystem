package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashSet;
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
     * 角色类型（0：普通；1：管理员；2：其他）
     */
    private Integer type;
    /**
     * 状态（0：冻结；1：可用）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作时间
     */
    private String operateTime;
    /**
     * 操作ip
     */
    private Long operateIp;
    /**
     * 权限列表
     */
    private Set<SystemPermission> permissions = new HashSet<>();
    /**
     * 用户列表
     */
    private Set<SystemUser> users = new HashSet<>();
}
