package com.tangerinespecter.oms.system.controller;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private ISystemUserService systemUserService;
    @Resource
    private ISystemInfoService systemInfoService;
    @Resource
    private PageResultService pageResultService;

    /**
     * 登录页
     */
    @ResponseBody
    @GetMapping(value = "/login", produces = "text/html;charset=UTF-8")
    public String loginPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getLoginPageKey, "login");
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
    @ResponseBody
    @RequestMapping(value = "/index", produces = "text/html;charset=UTF-8")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        SystemUser systemUser = SystemUtils.getCurrentUser();
        model.addAttribute("systemUser", systemUser);
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemIndexPageKey, "index");
    }

    /**
     * 内容
     */
    @ResponseBody
    @RequestMapping(value = "/home", produces = "text/html;charset=UTF-8")
    @LoggerInfo(value = "用户访问首页", event = LogOperation.EVENT_VISIT)
    public String homePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("statisticsInfo", systemInfoService.getStatisticsInfo());
        model.addAttribute("systemInfo", systemInfoService.getSystemInfo());
        model.addAttribute("noticeInfo", systemInfoService.getNoticeInfo());
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemHomePageKey, "common/home");
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