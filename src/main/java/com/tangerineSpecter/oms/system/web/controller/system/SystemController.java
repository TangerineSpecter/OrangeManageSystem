package com.tangerinespecter.oms.system.web.controller.system;

import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.service.system.SystemUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 系统相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.3
 * @DateTime 2019年1月5日 02:07:12
 */
@Controller
public class SystemController {

    @Resource
    private SystemUserService systemUserService;

    /**
     * 后台管理员
     */
    @RequestMapping(ServiceKey.System.SYSTEM_USER_PAGE_LIST)
    public String systemUserPage(Model model, SystemUserQueryObject qo) {
        systemUserService.querySystemUserList(model, qo);
        return "system/systemUser";
    }

    /**
     * 日历
     */
    @RequestMapping("/calendar")
    public String calendar() {
        return "system/calendar";
    }

}
