package com.tangerinespecter.oms.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * 修改密码
 *
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserPwdVo implements Serializable {
	
	@NotNull(message = "旧密码不能为空")
	private String oldPassword;
	@NotNull(message = "新密码不能为空")
	private String password;
}
