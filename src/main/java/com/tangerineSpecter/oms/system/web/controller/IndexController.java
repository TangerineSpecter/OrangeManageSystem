package com.tangerineSpecter.oms.system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangerineSpecter.oms.common.service.ServiceResult;
import com.tangerineSpecter.oms.system.domain.pojo.AccountsInfo;
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
	 */
	@RequestMapping(value = { "/", "login" })
	public String loginPage() {
		return "login";
	}

	/**
	 * 首页
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 内容
	 */
	@RequestMapping("/home")
	public String homePage() {
		return "common/home";
	}

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping("/userLogin")
	public ServiceResult login(AccountsInfo model) {
		return systemUserService.verifyLogin(model);
	}

	/**
	 * 注册页
	 */
	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	/**
	 * 错误跳转
	 */
	@RequestMapping("/errorPage")
	public String errorPage() {
		return "system/404";
	}
}
