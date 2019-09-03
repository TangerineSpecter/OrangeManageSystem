package com.tangerinespecter.oms.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制
 *
 * @author TangerineSpecter
 * @date 2019年09月03日00:25:54
 */
@Controller
public class PageController {

    /**
     * 星座页面
     */
    @RequestMapping("/constellation")
    public String constellationPage() {
        return "data/constellation";
    }

    /**
     * 系统配置
     */
    @RequestMapping("/systemSetting")
    public String systemSetting() {
        return "system/systemSetting";
    }
}
