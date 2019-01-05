package com.tangerineSpecter.oms.system.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsInfo {
	/** 帐号 */
	private String username;
	/** 密码 */
	private String password;
}