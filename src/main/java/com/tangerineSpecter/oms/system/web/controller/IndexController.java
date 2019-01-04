package com.tangerineSpecter.oms.system.web.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@RequestMapping("/login")
	public String login(Map<String, Object> model) {
		System.out.println("登录");
		return "login";
	}
}
