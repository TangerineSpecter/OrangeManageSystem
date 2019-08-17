package com.tangerinespecter.oms.system.web.controller.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.domain.SystemUser;
import com.tangerinespecter.oms.system.service.system.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 系统用户控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @DateTime 2019年1月11日
 */
@Controller
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 保存系统用户信息
     */
    @ResponseBody
    @RequestMapping(ServiceKey.System.SYSTEM_USER_UPDATE)
    public ServiceResult update(@Valid SystemUser systemUser) {
        return systemUserService.updateSystemUserInfo(systemUser);
    }
}
