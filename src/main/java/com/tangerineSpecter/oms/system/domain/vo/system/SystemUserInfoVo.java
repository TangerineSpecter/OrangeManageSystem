package com.tangerinespecter.oms.system.domain.vo.system;

import com.tangerinespecter.oms.system.valid.Delete;
import com.tangerinespecter.oms.system.valid.Update;
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

	@NotNull(groups = {Update.class, Delete.class})
	private Long id;
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
