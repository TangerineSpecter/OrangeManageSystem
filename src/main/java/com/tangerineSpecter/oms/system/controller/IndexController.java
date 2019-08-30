package com.tangerinespecter.oms.system.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountsInfo;
import com.tangerinespecter.oms.system.service.system.SystemInfoService;
import com.tangerinespecter.oms.system.service.system.SystemUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 默认控制
 *
 * @author tangerinespecter
 * @version V0.0.1
 * @date 2019年1月3日 22:25:33
 */
@Controller
public class IndexController {

    @Resource
    private SystemUserService systemUserService;
    @Resource
    private SystemInfoService systemInfoService;

    /**
     * 登录页
     */
    @GetMapping(ServiceKey.System.SYSTEM_LOGIN)
    public String loginPage() {
        return "login";
    }

    /**
     * 默认页
     */
    @RequestMapping(ServiceKey.System.SYSTEM_DEFAULT)
    public String defaultPage() {
        return "redirect:/index";
    }

    /**
     * 首页
     */
    @RequestMapping(ServiceKey.System.SYSTEM_INDEX_PAGE)
    public String index(Model model) {
        SystemUser systemUser = (SystemUser) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("systemUser", systemUser);
        return "index";
    }

    /**
     * 内容
     */
    @RequestMapping(ServiceKey.System.SYSTEM_HOME)
    public String homePage(Model model) {
        model.addAttribute("systemInfo", systemInfoService.getSystemInfo());
        model.addAttribute("managerInfo", systemInfoService.getManagerInfo());
        System.out.println(model);
        return "home.html_bak";
    }

    /**
     * 登录
     */
    @ResponseBody
    @PostMapping(ServiceKey.System.SYSTEM_LOGIN)
    public ServiceResult login(HttpServletResponse response, @Valid AccountsInfo model) {
        return systemUserService.verifyLogin(response, model);
    }

    /**
     * 注册页
     */
    @RequestMapping(ServiceKey.System.SYSTEM_REGISTER)
    public String register() {
        return "register";
    }

    /**
     * 帐号设置
     */
    @RequestMapping(value = ServiceKey.System.SYSTEM_USER_SETTING, method = RequestMethod.GET)
    public String accountSetting(Model model, @RequestParam(name = "id") Long id) {
        systemUserService.getSystemInfo(model, id);
        return "system/accountSetting";
    }

    /**
     * 错误跳转
     */
    @RequestMapping("/errorPage")
    public String errorPage() {
        return "system/404";
    }
}
