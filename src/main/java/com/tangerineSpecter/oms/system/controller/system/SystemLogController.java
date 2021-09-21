package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.query.SystemLogQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.system.ISystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 系统日志控制
 *
 * @author TangerineSpecter
 * @date
 */
@RestController
@RequestMapping("/system/log")
public class SystemLogController {

	@Resource
	private ISystemLogService systemLogService;

	/**
	 * 日志
	 */
	@RequestMapping("/page")
	@RequiresPermissions("system:log:page")
	public ModelAndView pageInfo() {
		return ServiceResult.jumpPage("system/logger");
	}

	/**
	 * 日志列表
	 */
	@RequestMapping("/list")
	public ServiceResult listInfo(SystemLogQueryObject qo) {
		return systemLogService.queryForPage(qo);
	}
}
