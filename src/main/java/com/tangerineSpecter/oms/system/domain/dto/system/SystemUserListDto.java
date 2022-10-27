package com.tangerinespecter.oms.system.domain.dto.system;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserListDto {

    private Long id;
    @ApiModelProperty("用户id")
    private String uid;
    @ApiModelProperty("帐号")
    private String username;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("性别（0：男；1：女）")
    private Integer sex;
    @ApiModelProperty("电话")
    private String phoneNumber;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("生日")
    private String birthday;
    @ApiModelProperty("简介")
    private String brief;
    @ApiModelProperty("部门id")
    private Long deptId;
    @ApiModelProperty("登录次数")
    private Integer loginCount;
    @ApiModelProperty("最后登录时间")
    private String lastLoginDate;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("是否超级管理员（0：不是；1：是）")
    private Integer admin;
    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;
    @ApiModelProperty("全部角色")
    private List<SystemRole> roles = CollUtil.newArrayList();
    @ApiModelProperty("管理员拥有角色")
    private List<SystemRole> haveRoles = CollUtil.newArrayList();
    @ApiModelProperty("管理员角色Id")
    private List<Long> haveRoleIds = CollUtil.newArrayList();
}
