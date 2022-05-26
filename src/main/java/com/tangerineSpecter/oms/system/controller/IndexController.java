package com.tangerinespecter.oms.system.controller;

import cn.hutool.core.map.MapUtil;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ParamUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.mapper.SystemNoticeMapper;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * 默认控制
 *
 * @author tangerinespecter
 * @version V0.0.1
 * @date 2019年1月3日 22:25:33
 */
@Controller
@Api(tags = "系统核心接口")
public class IndexController {

    @Resource
    private ISystemUserService systemUserService;
    @Resource
    private ISystemInfoService systemInfoService;
    @Resource
    private PageResultService pageResultService;
    @Resource
    private SystemNoticeMapper systemNoticeMapper;

    /**
     * 登录页
     */
    @ResponseBody
    @ApiOperation("登录页")
    @GetMapping(value = "/login", produces = "text/html;charset=UTF-8")
    public String loginPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getLoginPageKey, "login");
    }

    /**
     * 默认页
     */
    @ApiOperation("默认页")
    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/index";
    }

    /**
     * 首页
     */
    @ResponseBody
    @ApiOperation("首页内容")
    @GetMapping(value = "/index", produces = "text/html;charset=UTF-8")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("systemUser", SystemUtils.getCurrentUser());
        model.addAttribute("webTitle", SystemConstant.systemConfig.getWebTitle());
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemIndexPageKey, "index");
    }

    /**
     * 内容
     */
    @ResponseBody
    @ApiOperation("首页页面")
    @GetMapping(value = "/home", produces = "text/html;charset=UTF-8")
    @LoggerInfo(value = "用户访问首页", event = LogOperation.EVENT_VISIT)
    public String homePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("systemInfo", systemInfoService.getSystemInfo());
        model.addAttribute("noticeInfo", systemInfoService.getNoticeInfo());
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemHomePageKey, "common/home");
    }

    /**
     * 登录
     */
    @ResponseBody
    @ApiOperation("登录操作")
    @PostMapping("/login")
    public ServiceResult login(HttpServletRequest request, HttpServletResponse response, @Valid AccountInfo model) {
        return systemUserService.verifyLogin(request, response, model);
    }

    /**
     * 注册页
     */
    @ApiOperation("注册页面")
    @GetMapping("/register")
    public ModelAndView register() {
        return ServiceResult.jumpPage("register");
    }

    /**
     * 错误跳转
     */
    @ApiOperation("错误页面")
    @GetMapping("/errorPage")
    public ModelAndView errorPage() {
        return ServiceResult.jumpPage("error/404");
    }

    /**
     * 异常页面
     */
    @ApiOperation("异常页面")
    @GetMapping("/exceptionPage")
    public ModelAndView exceptionPage() {
        return ServiceResult.jumpPage("error/500");
    }

    /**
     * 接口文档
     */
    @ApiOperation("接口文档")
    @GetMapping("/api-doc")
    public String apiDocPage() {
        return "redirect:/doc.html";
    }

    /**
     * 消息中心
     */
    @ApiOperation("消息中心")
    @GetMapping("/noticeCenter")
    public ModelAndView noticeCenter(Model model) {
        int notReadNoticeCount = systemNoticeMapper.queryNotReadNoticeCount(SystemUtils.getSystemUserId());
        HashMap<String, Integer> data = MapUtil.newHashMap();
        data.put(ParamUtils.NOT_READ_NOTICE_COUNT, notReadNoticeCount);
        model.addAllAttributes(data);
        return ServiceResult.jumpPage("system/systemNotice");
    }

}