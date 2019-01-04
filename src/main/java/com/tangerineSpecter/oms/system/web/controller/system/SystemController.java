package com.tangerineSpecter.oms.system.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统相关控制
 * 
 * @author TangerineSpecter
 * @DateTime 2019年1月5日 02:07:12
 * @version v0.0.3
 *
 */
@Controller
public class SystemController {

	/**
	 * 后台管理员
	 */
	@RequestMapping(value = { "/systemUesr" })
	public String loginPage() {
		return "system/systemUesr";
	}
}
