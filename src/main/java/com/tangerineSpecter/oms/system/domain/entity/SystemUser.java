package com.tangerinespecter.oms.system.domain.entity;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
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
    @ApiModelProperty("管理员id")
    @TableField("uid")
    private String uid;
    @NotBlank(message = "账号名不能为空")
    @ApiModelProperty("账号名")
    @TableField("username")
    private String username;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    @TableField("password")
    private String password;
    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;
    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;
    @ApiModelProperty("性别（0：男；1：女）")
    @TableField("sex")
    private Integer sex;
    @ApiModelProperty("电话")
    @TableField("phone_number")
    private String phoneNumber;
    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;
    @ApiModelProperty("城市")
    @TableField("city")
    private String city;
    @ApiModelProperty("生日")
    @TableField("birthday")
    private String birthday;
    @ApiModelProperty("简介")
    @TableField("brief")
    private String brief;
    @ApiModelProperty("盐")
    @TableField("salt")
    private String salt;
    @ApiModelProperty("部门id")
    @TableField("dept_id")
    private Long deptId;
    @ApiModelProperty("登录次数")
    @TableField("login_count")
    private Integer loginCount;
    @ApiModelProperty("最后登录时间")
    @TableField("last_login_date")
    private String lastLoginDate;
    //TODO时间类型处理
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    @ApiModelProperty("是否超级管理员（0：不是；1：是）")
    @TableField("admin")
    private Integer admin;
    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    @TableField("is_del")
    private Integer isDel;
    @ApiModelProperty("管理员角色")
    @TableField(exist = false)
    private List<SystemRole> roles = CollUtil.newArrayList();

    public SystemUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.isDel = GlobalBoolEnum.FALSE.getValue();
        this.admin = GlobalBoolEnum.FALSE.getValue();
    }
}
