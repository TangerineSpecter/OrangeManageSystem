package com.tangerinespecter.oms.system.domain.entity;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

/**
 * 系统管理员信息表
 *
 * @author TangerineSpecter
 * @version v0.0.1
 * @Date 2019年1月7日
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "system_user", resultMap = "systemUserMap")
public class SystemUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 帐号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
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
     * 盐
     */
    private String salt;
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
     * 管理员角色
     */
    private List<SystemRole> roles = CollUtil.newArrayList();

}
