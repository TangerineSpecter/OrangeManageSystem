package com.tangerineSpecter.oms.system.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangerineSpecter.oms.common.service.ServiceResult;
import com.tangerineSpecter.oms.system.domain.pojo.SystemInfoBean;
import com.tangerineSpecter.oms.system.service.system.SystemInfoService;

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

	@Autowired
	private SystemInfoService systemInfoService;

	/**
	 * 后台管理员
	 */
	@RequestMapping("/systemUser")
	public String loginPage() {
		return "system/systemUser";
	}

	/**
	 * 系统信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/systemInfo")
	public ServiceResult getSystemInfo() {
		SystemInfoBean systemInfoBean = systemInfoService.getSystemInfo();
		return ServiceResult.success(systemInfoBean);
	}

}
