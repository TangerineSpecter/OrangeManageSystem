package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.query.SystemPermissionQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.IPermissionManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限管理控制
 *
 * @author TangerineSpecter
 * @version 0.3.3
 * @date 2020年05月19日12:58:32
 */
@RestController
@Api(tags = "权限管理接口")
@RequestMapping("/system/permission")
public class PermissionManageController {
	
	@Resource
	private PageResultService pageResultService;
	@Resource
	private IPermissionManageService permissionManageService;
	
	/**
	 * 权限管理
	 */
	@ApiOperation("权限管理页面")
	@RequiresPermissions("system:permission:page")
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemPermissionPageKey, "system/permissionManage");
	}
	
	/**
	 * 权限列表
	 */
	@ApiOperation("权限管理列表")
	@AccessLimit(maxCount = 10)
	@GetMapping("/list")
	public ServiceResult listInfo(SystemPermissionQueryObject qo) {
		return permissionManageService.queryForPage(qo);
	}
	
	/**
	 * 权限初始化
	 */
	@ApiOperation("权限初始化")
	@AccessLimit(maxCount = 3)
	@PostMapping("/init")
	public ServiceResult init() {
		return permissionManageService.init();
	}
}
