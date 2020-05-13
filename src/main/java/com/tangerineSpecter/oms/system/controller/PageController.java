package com.tangerinespecter.oms.system.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.QiNiuConfig;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.service.system.ISystemConfigServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * 页面控制
 *
 * @author TangerineSpecter
 * @date 2019年09月03日00:25:54
 */
@Controller
@RequestMapping("/page")
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

    /**
     * 帐号设置
     */
    @RequestMapping(value = "/accountSetting", method = RequestMethod.GET)
    public String accountSetting(Model model) {
        model.addAttribute("systemUser", SystemUtils.getCurrentUser());
        return "system/accountSetting";
    }

    /**
     * 系统配置
     */
    @RequestMapping("/userPassword")
    public String userPassword(Model model) {
        SystemUser systemUser = SystemUtils.getCurrentUser();
        model.addAttribute("systemUser", systemUser);
        return "system/userPassword";
    }

}
