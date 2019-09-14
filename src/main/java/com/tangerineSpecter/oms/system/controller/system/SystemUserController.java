package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.service.system.SystemUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private SystemUserService systemUserService;

    /**
     * 帐号设置
     */
    @RequestMapping(value = "/accountSetting", method = RequestMethod.GET)
    public String accountSetting(Model model) {
        SystemUser systemUser = SystemUtils.getCurrentUser();
        model.addAttribute("systemUser", systemUser);
        return "system/accountSetting";
    }

    /**
     * 后台管理员
     */
    @RequestMapping("/page")
    public String systemUserPage() {
        return "system/systemUser";
    }

    /**
     * 后台管理员
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult listInfo(SystemUserQueryObject qo) {
        return systemUserService.querySystemUserList(qo);
    }

    /**
     * 添加系统管理员
     *
     * @param systemUser
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/insert")
    @LoggerInfo(value = "用户添加管理员", event = LogOperation.EVENT_ADD)
    public ServiceResult insert(@Valid SystemUser systemUser) throws Exception {
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
}
