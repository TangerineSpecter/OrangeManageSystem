package com.tangerineSpecter.oms.system.domain;

import lombok.Data;

@Data
public class SystemUser {
	private Long id;
	private String userName;
	private String password;
	private String nickName;
	private String phoneNumber;
	private Long createTime;
	private Long updateTime;
	private Integer admin;
	private Integer isDel;
}
