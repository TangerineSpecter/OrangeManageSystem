package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserPwdVo;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 系统用户控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @DateTime 2019年1月11日
 */
@RestController
@Api(tags = "系统用户管理接口")
@RequestMapping("/systemUser")
public class SystemUserController {
	
	@Resource
	private ISystemUserService systemUserService;
	
	/**
	 * 后台管理员
	 */
	@ApiOperation(value = "用户健康管理接口", hidden = true)
	@GetMapping("/page")
	@RequiresPermissions("systemUser:page")
	public ModelAndView systemUserPage() {
		return ServiceResult.jumpPage("system/systemUser");
	}
	
	/**
	 * 个人中心
	 */
	@ApiOperation(value = "用户个人中心页面", hidden = true)
	@GetMapping("/center")
	public ModelAndView userCenter(Model model) {
		SystemUser systemUser = (SystemUser) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("userInfo", systemUser);
		return ServiceResult.jumpPage("system/userCenter");
	}
	
	/**
	 * 裁剪图片
	 */
	@ApiOperation(value = "裁剪图片页面", hidden = true)
	@GetMapping("/profile")
	public ModelAndView profile() {
		return ServiceResult.jumpPage("system/profile");
	}
	
	/**
	 * 后台管理员
	 */
	@ApiOperation(value = "编辑管理员角色页面", hidden = true)
	@GetMapping("/addRolePage")
	public ModelAndView addRolePage() {
		return ServiceResult.jumpPage("system/editSystemUserRole");
	}
	
	/**
	 * 修改密码页面
	 */
	@ApiOperation(value = "修改密码页面", hidden = true)
	@GetMapping("/updatePwdPage")
	public ModelAndView updatePwdPage(Model model) {
		return ServiceResult.jumpPage("system/userPassword");
	}
	
	/**
	 * 后台管理员
	 */
	@ApiOperation(value = "后台管理员列表")
	@GetMapping("/list")
	public ServiceResult listInfo(SystemUserQueryObject qo) {
		return systemUserService.querySystemUserList(qo);
	}
	
	/**
	 * 添加系统管理员
	 */
	@ApiOperation(value = "新增后台管理员")
	@PostMapping("/insert")
	@LoggerInfo(value = "用户添加管理员", event = LogOperation.EVENT_ADD)
	public ServiceResult insert(@Validated @RequestBody SystemUser systemUser) {
		return systemUserService.insertSystemUserInfo(systemUser);
	}
	
	/**
	 * 修改系统用户信息
	 */
	@ApiOperation(value = "修改后台管理员")
	@PutMapping("/update")
	@LoggerInfo(value = "用户更新管理员", event = LogOperation.EVENT_UPDATE)
	public ServiceResult update(@Validated @RequestBody SystemUserInfoVo vo) {
		return systemUserService.updateSystemUserInfo(vo);
	}
	
	/**
	 * 修改系统用户角色
	 */
	@ApiOperation(value = "修改管理员角色")
	@PutMapping("/update-role")
	@LoggerInfo(value = "用户更新管理员角色", event = LogOperation.EVENT_UPDATE)
	public ServiceResult updateRole(@Validated @RequestBody SystemUserInfoVo vo) {
		return systemUserService.updateSystemUserRole(vo);
	}
	
	/**
	 * 修改密码
	 */
	@ApiOperation(value = "修改账号密码")
	@PutMapping("/update-password")
	@LoggerInfo(value = "修改密码", event = LogOperation.EVENT_UPDATE)
	public ServiceResult updatePassword(@Validated @RequestBody SystemUserPwdVo vo) {
		return systemUserService.updatePassword(vo);
	}
}

