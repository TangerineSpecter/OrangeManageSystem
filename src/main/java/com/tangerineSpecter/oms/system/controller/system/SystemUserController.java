package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@Api("系统用户管理模块")
@RestController
@RequestMapping("/systemUser")
public class SystemUserController {

    @Resource
    private ISystemUserService systemUserService;

    /**
     * 后台管理员
     */
    @GetMapping("/page")
    @RequiresPermissions("systemUser:page")
    public ModelAndView systemUserPage() {
        return ServiceResult.jumpPage("system/systemUser");
    }

    /**
     * 后台管理员
     */
    @GetMapping("/addRolePage")
    public ModelAndView addRolePage() {
        return ServiceResult.jumpPage("system/editSystemUserRole");
    }

    /**
     * 后台管理员
     */
    @GetMapping("/list")
    public ServiceResult listInfo(SystemUserQueryObject qo) {
        return systemUserService.querySystemUserList(qo);
    }

    /**
     * 添加系统管理员
     *
     * @param systemUser
     */
    @PostMapping("/insert")
    @LoggerInfo(value = "用户添加管理员", event = LogOperation.EVENT_ADD)
    public ServiceResult insert(@Validated @RequestBody SystemUser systemUser) {
        return systemUserService.insertSystemUserInfo(systemUser);
    }

    /**
     * 修改系统用户信息
     */
    @PutMapping("/update")
    @LoggerInfo(value = "用户更新管理员", event = LogOperation.EVENT_UPDATE)
    public ServiceResult update(@Validated @RequestBody SystemUserInfoVo vo) {
        return systemUserService.updateSystemUserInfo(vo);
    }

    /**
     * 修改系统用户角色
     */
    @PutMapping("/update-role")
    @LoggerInfo(value = "用户更新管理员角色", event = LogOperation.EVENT_UPDATE)
    public ServiceResult updateRole(@Validated @RequestBody SystemUserInfoVo vo) {
        return systemUserService.updateSystemUserRole(vo);
    }

    /**
     * 修改密码
     */
    @PutMapping("/update-password")
    @LoggerInfo(value = "修改密码", event = LogOperation.EVENT_UPDATE)
    public ServiceResult updatePassword(@Validated @RequestBody SystemUserInfoVo vo) {
        return systemUserService.updatePassword(vo);
    }
}
