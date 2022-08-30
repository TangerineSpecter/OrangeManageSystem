package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemUserListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.FileInfoBean;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserPwdVo;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@ReWriteBody
@RestController
@Api(tags = "系统用户管理接口")
@RequestMapping("/systemUser")
public class SystemUserController {

    @Resource
    private ISystemUserService systemUserService;

    @ApiOperation(value = "后台管理员页面")
    @GetMapping("/page")
    @RequiresPermissions("systemUser:page")
    public ModelAndView systemUserPage() {
        return ServiceResult.jumpPage("system/systemUser");
    }

    @ApiOperation(value = "用户个人中心页面")
    @GetMapping("/center")
    public ModelAndView userCenter(Model model) {
        model.addAttribute("userInfo", UserContext.getCurrentUser());
        return ServiceResult.jumpPage("system/userCenter");
    }

    @ApiOperation(value = "裁剪图片页面")
    @GetMapping("/profile")
    public ModelAndView profile() {
        return ServiceResult.jumpPage("system/profile");
    }

    @ApiOperation(value = "编辑管理员角色页面")
    @GetMapping("/addRolePage")
    public ModelAndView addRolePage() {
        return ServiceResult.jumpPage("system/editSystemUserRole");
    }

    @ApiOperation(value = "修改密码页面")
    @GetMapping("/updatePwdPage")
    public ModelAndView updatePwdPage() {
        return ServiceResult.jumpPage("system/userPassword");
    }

    @ApiOperation(value = "后台管理员列表")
    @GetMapping("/list")
    public PageInfo<SystemUserListDto> listInfo(SystemUserQueryObject qo) {
        return systemUserService.querySystemUserList(qo);
    }

    @ApiOperation(value = "新增后台管理员")
    @PostMapping("/insert")
    @LoggerInfo(value = "用户添加管理员", event = LogOperation.EVENT_ADD)
    public void insert(@Validated @RequestBody SystemUser systemUser) {
        systemUserService.insertSystemUserInfo(systemUser);
    }

    @ApiOperation(value = "修改后台管理员")
    @PutMapping("/update")
    @LoggerInfo(value = "用户更新管理员", event = LogOperation.EVENT_UPDATE)
    public void update(@RequestBody SystemUserInfoVo vo) {
        systemUserService.updateSystemUserInfo(vo);
    }

    @ApiOperation(value = "修改管理员角色")
    @PutMapping("/update-role")
    @LoggerInfo(value = "用户更新管理员角色", event = LogOperation.EVENT_UPDATE)
    public void updateRole(@Validated @RequestBody SystemUserInfoVo vo) {
        systemUserService.updateSystemUserRole(vo);
    }

    @ApiOperation(value = "修改账号密码")
    @PutMapping("/update-password")
    @LoggerInfo(value = "修改密码", event = LogOperation.EVENT_UPDATE)
    public void updatePassword(@Validated @RequestBody SystemUserPwdVo vo) {
        systemUserService.updatePassword(vo);
    }

    @ApiOperation(value = "更新头像")
    @PutMapping("/updateAvatar")
    @LoggerInfo(value = "更新头像", event = LogOperation.EVENT_UPDATE)
    public void updateAvatar(@ApiParam("头像文件信息") @RequestBody FileInfoBean avatarInfo) {
        systemUserService.updateAvatar(avatarInfo);
    }
}

