package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.system.domain.dto.system.HomePageDataDto;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 系统相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.3
 * @DateTime 2019年1月5日 02:07:12
 */
@Controller
public class SystemInfoController {

    @Resource
    private ISystemInfoService systemInfoService;

    /**
     * 首页初始化
     */
    @ResponseBody
    @RequestMapping("/initHome")
    public HomePageDataDto initHome() {
        return systemInfoService.initHome();
    }

    /**
     * 日历
     */
    @RequestMapping("/calendar")
    public String calendar() {
        return "system/calendar";
    }

}
