package com.tangerinespecter.oms.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 系统管理员信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserInfoVo {

	@NotNull(message = "id不能为空")
	private Long id;

	private String uid;
	/**
	 * 头像地址
	 */
	private String avatarUrl;

	private String phoneNumber;

	private String nickName;

	private Integer sex;

	private String email;

	private String city;

	private String birthday;

	private String brief;
	/**
	 * 旧密码
	 */
	private String oldPassword;

	private String password;

	private String roleIds;
}
