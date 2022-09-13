package com.tangerinespecter.oms.system.controller;

import cn.hutool.core.map.MapUtil;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ParamUtils;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.mapper.SystemNoticeMapper;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 默认控制
 *
 * @author tangerinespecter
 * @version V0.0.1
 * @date 2019年1月3日 22:25:33
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "系统核心接口")
public class IndexController {

    private final ISystemUserService systemUserService;
    private final ISystemInfoService systemInfoService;
    private final PageResultService pageResultService;
    private final SystemNoticeMapper systemNoticeMapper;

    @ApiOperation("登录页")
    @GetMapping(value = "/login", produces = "text/html;charset=UTF-8")
    public String loginPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getLoginPageKey, "login");
    }

    @ApiOperation("默认页")
    @GetMapping("/")
    public ModelAndView defaultPage() {
        return ServiceResult.jumpPage("redirect:/index");
    }

    @ApiOperation("首页内容")
    @GetMapping(value = "/index", produces = "text/html;charset=UTF-8")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("systemUser", UserContext.getCurrentUser());
        model.addAttribute("webTitle", SystemConstant.systemConfig.getWebTitle());
        model.addAttribute(ParamUtils.NOT_READ_NOTICE_COUNT, systemNoticeMapper.queryNotReadNoticeCount());
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemIndexPageKey, "index");
    }

    @ApiOperation("首页页面")
    @GetMapping(value = "/home", produces = "text/html;charset=UTF-8")
    @LoggerInfo(value = "用户访问首页", event = LogOperation.EVENT_VISIT)
    public String homePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("systemInfo", systemInfoService.getSystemInfo());
        model.addAttribute("noticeInfo", systemInfoService.getNoticeInfo());
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getSystemHomePageKey, "common/home");
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ServiceResult<Object> login(HttpServletRequest request, HttpServletResponse response, @Validated AccountInfo model) {
        systemUserService.verifyLogin(request, response, model);
        return ServiceResult.success();
    }

    @ApiOperation("注册页面")
    @GetMapping("/register")
    public ModelAndView register() {
        return ServiceResult.jumpPage("register");
    }

    @ApiOperation("错误页面")
    @GetMapping("/errorPage")
    public ModelAndView errorPage() {
        return ServiceResult.jumpPage("error/404");
    }

    @ApiOperation("异常页面")
    @GetMapping("/exceptionPage")
    public ModelAndView exceptionPage() {
        return ServiceResult.jumpPage("error/500");
    }

    @ApiOperation("接口文档")
    @GetMapping("/api-doc")
    public ModelAndView apiDocPage() {
        return ServiceResult.jumpPage("redirect:/doc.html");
    }

    @ApiOperation("消息中心")
    @GetMapping("/noticeCenter")
    public ModelAndView noticeCenter(Model model) {
        model.addAttribute(ParamUtils.NOT_READ_NOTICE_COUNT, systemNoticeMapper.queryNotReadNoticeCount());
        return ServiceResult.jumpPage("system/noticeCenter");
    }

}