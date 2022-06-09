package com.tangerinespecter.oms.system.controller;

import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.service.system.ISystemConfigService;
import com.tangerinespecter.oms.system.service.system.ISystemVersionHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面控制
 *
 * @author TangerineSpecter
 * @date 2019年09月03日00:25:54
 */
@Controller
@RequiredArgsConstructor
@Api(tags = "页面接口")
@RequestMapping("/page")
public class PageController {

    private final ISystemConfigService systemConfigServer;
    private final ISystemVersionHistoryService systemVersionHistoryService;

    @ApiOperation("系统配置页面")
    @RequiresPermissions("page:systemSetting")
    @GetMapping("/systemSetting")
    public String systemSetting(Model model) {
        SystemConfig systemConfig = systemConfigServer.configInfo();
        model.addAttribute("systemConfigInfo", systemConfig);
        return "system/systemSetting";
    }

    @ApiOperation("账号设置页面")
    @GetMapping(value = "/accountSetting")
    public String accountSetting(Model model) {
        model.addAttribute("systemUser", UserContext.getCurrentUser());
        return "system/accountSetting";
    }

    @ApiOperation("修改密码页面")
    @GetMapping("/userPassword")
    public String userPassword(Model model) {
        model.addAttribute("systemUser", UserContext.getCurrentUser());
        return "system/userPassword";
    }

    @ApiOperation("版本更新历史页面")
    @GetMapping("/versionHistory")
    public ModelAndView versionHistory(Model model) {
        model.addAttribute("versionList", systemVersionHistoryService.getVersionList());
        return ServiceResult.jumpPage("system/versionHistory");
    }
}
