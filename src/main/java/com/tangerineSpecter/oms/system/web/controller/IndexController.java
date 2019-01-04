package com.tangerineSpecter.oms.system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangerineSpecter.oms.common.service.ServiceResult;
import com.tangerineSpecter.oms.system.domain.pojo.AccountsParam;
import com.tangerineSpecter.oms.system.service.system.SystemUserService;

/**
 * 默认控制
 * 
 * @author TangerineSpecter
 * @Datetime 2019年1月3日 22:25:33
 * @version V0.0.1
 *
 */
@Controller
public class IndexController {

	@Autowired
	private SystemUserService systemUserService;

	/**
	 * 默认页
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String loginPage() {
		return "login";
	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping("/login")
	public ServiceResult login(AccountsParam model) {
		return systemUserService.verifyLogin(model);
	}
}
