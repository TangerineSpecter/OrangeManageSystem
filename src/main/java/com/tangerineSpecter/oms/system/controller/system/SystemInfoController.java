package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.system.domain.dto.system.HomePageDataDto;
import com.tangerinespecter.oms.system.domain.dto.system.MenuChildInfo;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.3
 * @DateTime 2019年1月5日 02:07:12
 */
@Api(tags = "系统相关管理接口", hidden = true)
@RestController
public class SystemInfoController {
	
	@Resource
	private ISystemInfoService systemInfoService;
	
	/**
	 * 首页初始化
	 */
	@ApiOperation(value = "首页信息初始化")
	@GetMapping("/initHome")
	public HomePageDataDto initHome() {
		return systemInfoService.initHome();
	}
	
	/**
	 * 菜单初始化
	 */
	@ApiOperation(value = "菜单信息初始化")
	@GetMapping("/initMenu")
	public List<MenuChildInfo> initMenu() {
		return systemInfoService.initMenu();
	}
	
	/**
	 * 日历
	 */
	@ApiOperation(value = "日历页面")
	@RequestMapping("/calendar")
	public String calendar() {
		return "system/calendar";
	}
	
}
