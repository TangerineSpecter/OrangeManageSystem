package com.tangerinespecter.oms.system.web.controller.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.dao.entity.SystemUser;
import com.tangerinespecter.oms.system.service.system.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统用户控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @DateTime 2019年1月11日
 */
@RestController
public class SystemUserController {

    @Resource
    private SystemUserService systemUserService;

    @RequestMapping(ServiceKey.System.SYSTEM_USER_INSERT)
    public ServiceResult insert(@Valid SystemUser systemUser) throws Exception {
        return systemUserService.insertSystemUserInfo(systemUser);
    }

    /**
     * 保存系统用户信息
     */
    @RequestMapping(ServiceKey.System.SYSTEM_USER_UPDATE)
    public ServiceResult update(@Valid SystemUser systemUser) {
        return systemUserService.updateSystemUserInfo(systemUser);
    }
}
