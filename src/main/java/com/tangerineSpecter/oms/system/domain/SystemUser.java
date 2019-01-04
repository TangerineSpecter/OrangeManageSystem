package com.tangerineSpecter.oms.system.domain;

import java.util.Date;

import lombok.Data;

@Data
public class SystemUser {
	private Long id;
	private String userName;
	private String password;
	private String nickName;
	private String phoneNumber;
	private String city;
	private Date createTime;
	private Date updateTime;
	private Integer admin;
	private Integer isDel;
}
