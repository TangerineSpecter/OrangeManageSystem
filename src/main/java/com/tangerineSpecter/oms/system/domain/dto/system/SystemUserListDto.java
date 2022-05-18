package com.tangerinespecter.oms.system.domain.dto.system;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserListDto {

    private Long id;

    private String uid;
    /**
     * 帐号
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别（0：男；1：女）
     */
    private Integer sex;
    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 城市
     */
    private String city;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 简介
     */
    private String brief;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 登录次数
     */
    private Integer loginCount;
    /**
     * 最后登录时间
     */
    private String lastLoginDate;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 是否超级管理员（0：不是；1：是）
     */
    private Integer admin;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
    /**
     * 全部角色
     */
    private List<SystemRole> roles = CollUtil.newArrayList();

    /**
     * 管理员拥有角色
     */
    private List<SystemRole> haveRoles = CollUtil.newArrayList();
    /**
     * 管理员角色Id
     */
    private List<Long> haveRoleIds = CollUtil.newArrayList();
}
