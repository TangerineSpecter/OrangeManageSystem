package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统用户控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @DateTime 2019年1月11日
 */
@Controller
@RequestMapping("/systemUser")
public class SystemUserController {

	@Resource
	private ISystemUserService systemUserService;

	/**
	 * 后台管理员
	 */
	@RequestMapping("/page")
	@RequiresPermissions("systemUser:page")
	public String systemUserPage() {
		return "system/systemUser";
	}

	/**
	 * 后台管理员
	 */
	@RequestMapping("/addRolePage")
	public String addRolePage() {
		return "system/editSystemUserRole";
	}

	/**
	 * 后台管理员
	 */
	@ResponseBody
	@GetMapping("/list")
	public ServiceResult listInfo(SystemUserQueryObject qo) {
		return systemUserService.querySystemUserList(qo);
	}

	/**
	 * 添加系统管理员
	 *
	 * @param systemUser
	 * @return
	 */
	@ResponseBody
	@PostMapping("/insert")
	@LoggerInfo(value = "用户添加管理员", event = LogOperation.EVENT_ADD)
	public ServiceResult insert(@Valid SystemUser systemUser) {
		return systemUserService.insertSystemUserInfo(systemUser);
	}

	/**
	 * 修改系统用户信息
	 */
	@ResponseBody
	@RequestMapping("/update")
	@LoggerInfo(value = "用户更新管理员", event = LogOperation.EVENT_UPDATE)
	public ServiceResult update(@Valid SystemUserInfoVo vo) {
		return systemUserService.updateSystemUserInfo(vo);
	}

	/**
	 * 修改系统用户角色
	 */
	@ResponseBody
	@PutMapping("/update-role")
	@LoggerInfo(value = "用户更新管理员角色", event = LogOperation.EVENT_UPDATE)
	public ServiceResult updateRole(@Valid @RequestBody SystemUserInfoVo vo) {
		return systemUserService.updateSystemUserRole(vo);
	}

	/**
	 * 修改密码
	 */
	@ResponseBody
	@RequestMapping("/update-password")
	@LoggerInfo(value = "修改密码", event = LogOperation.EVENT_UPDATE)
	public ServiceResult updatePassword(@Valid SystemUserInfoVo vo) {
		return systemUserService.updatePassword(vo);
	}
}
