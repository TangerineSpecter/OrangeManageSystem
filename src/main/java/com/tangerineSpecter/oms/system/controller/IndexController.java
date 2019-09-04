package com.tangerinespecter.oms.system.controller;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.service.system.SystemInfoService;
import com.tangerinespecter.oms.system.service.system.SystemUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 默认页
     */
    @RequestMapping("/")
    public String defaultPage() {
        return "redirect:/index";
    }

    /**
     * 首页
     */
    @RequestMapping("/index")
    public String index(Model model) {
        SystemUser systemUser = (SystemUser) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("systemUser", systemUser);
        return "index";
    }

    /**
     * 内容
     */
    @RequestMapping("/home")
    @LoggerInfo(value = "用户访问首页", event = LogOperation.EVENT_VISIT)
    public String homePage(Model model) {
        //model.addAttribute("systemInfo", systemInfoService.getSystemInfo());
        //model.addAttribute("managerInfo", systemInfoService.getManagerInfo());
        return "common/home";
    }

    /**
     * 登录
     */
    @ResponseBody
    @PostMapping("/login")
    public ServiceResult login(HttpServletResponse response, @Valid AccountInfo model) {
        return systemUserService.verifyLogin(response, model);
    }

    /**
     * 注册页
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 错误跳转
     */
    @RequestMapping("/errorPage")
    public String errorPage() {
        return "system/404";
    }

}