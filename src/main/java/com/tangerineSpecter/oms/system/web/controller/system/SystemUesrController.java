package com.tangerineSpecter.oms.system.web.controller.system;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangerineSpecter.oms.common.result.ServiceResult;
import com.tangerineSpecter.oms.system.domain.SystemUser;
import com.tangerineSpecter.oms.system.service.system.SystemUserService;

/**
 * 系统用户控制
 * 
 * @author TangerineSpecter
 * @Date 2019年1月11日
 * @version v0.0.5
 */
@Controller
public class SystemUesrController {

	@Autowired
	private SystemUserService systemUserService;

	/**
	 * 保存系统用户信息
	 */
	@ResponseBody
	@RequestMapping("/systemUser/update")
	public ServiceResult update(@Valid SystemUser systemUser) {
		return systemUserService.updateSystemUserInfo(systemUser);
	}
}
