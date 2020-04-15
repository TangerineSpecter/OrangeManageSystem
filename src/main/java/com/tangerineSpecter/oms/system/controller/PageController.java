package com.tangerinespecter.oms.system.controller;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.service.system.ISystemConfigServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 页面控制
 *
 * @author TangerineSpecter
 * @date 2019年09月03日00:25:54
 */
@Controller
public class PageController {
    @Resource
    private ISystemConfigServer systemConfigServer;

    /**
     * 系统配置
     */
    @RequestMapping("/systemSetting")
    public String systemSetting(Model model) {
        SystemConfig systemConfig = systemConfigServer.configInfo();
        model.addAttribute("systemConfigInfo", systemConfig);
        return "system/systemSetting";
    }
}
