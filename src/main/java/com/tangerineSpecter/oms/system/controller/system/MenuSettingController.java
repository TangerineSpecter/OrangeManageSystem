package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.system.MenuSettingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 菜单设置控制
 *
 * @author TangerineSpecter
 * @date 2019年09月01日18:40:56
 */
@RestController
@RequestMapping("/system/menu")
public class MenuSettingController {

    @Resource
    private MenuSettingService menuSettingService;

    @RequestMapping("/list")
    public ServiceResult<Object> listInfo() {
        return menuSettingService.listInfo();
    }
}
