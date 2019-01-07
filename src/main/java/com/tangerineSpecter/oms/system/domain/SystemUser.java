package com.tangerineSpecter.oms.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统管理员信息表
 * 
 * @author TangerineSpecter
 * @Date 2019年1月7日
 * @version v0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {
	private Long id;
	private String userName;
	private String password;
	private String nickName;
	private String phoneNumber;
	private String city;
	private String createTime;
	private String updateTime;
	private Integer admin;
	private Integer isDel;
}
