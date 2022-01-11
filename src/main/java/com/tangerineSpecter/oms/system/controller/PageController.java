package com.tangerinespecter.oms.system.controller;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.system.VersionHistoryListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.service.system.ISystemConfigService;
import com.tangerinespecter.oms.system.service.system.ISystemVersionHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 页面控制
 *
 * @author TangerineSpecter
 * @date 2019年09月03日00:25:54
 */
@Controller
@Api(tags = "页面接口")
@RequestMapping("/page")
public class PageController {
	@Resource
	private ISystemConfigService systemConfigServer;
	@Resource
	private ISystemVersionHistoryService systemVersionHistoryService;
	
	/**
	 * 系统配置
	 */
	@ApiOperation("系统配置页面")
	@RequiresPermissions("page:systemSetting")
	@GetMapping("/systemSetting")
	public String systemSetting(Model model) {
		SystemConfig systemConfig = systemConfigServer.configInfo();
		model.addAttribute("systemConfigInfo", systemConfig);
		return "system/systemSetting";
	}
	
	/**
	 * 帐号设置
	 */
	@ApiOperation("账号设置页面")
	@GetMapping(value = "/accountSetting")
	public String accountSetting(Model model) {
		model.addAttribute("systemUser", SystemUtils.getCurrentUser());
		return "system/accountSetting";
	}
	
	/**
	 * 修改密码
	 */
	@ApiOperation("修改密码页面")
	@GetMapping("/userPassword")
	public String userPassword(Model model) {
		SystemUser systemUser = SystemUtils.getCurrentUser();
		model.addAttribute("systemUser", systemUser);
		return "system/userPassword";
	}
	
	/**
	 * 版本更新历史
	 */
	@ApiOperation("版本更新历史页面")
	@GetMapping("/versionHistory")
	public ModelAndView versionHistory(Model model) {
		List<VersionHistoryListDto> versionList = systemVersionHistoryService.getVersionList();
		model.addAttribute("versionList", versionList);
		return ServiceResult.jumpPage("system/versionHistory");
	}
}
