package com.tangerinespecter.oms.system.controller.system;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.MessageDto;
import com.tangerinespecter.oms.system.domain.dto.system.HomePageDataDto;
import com.tangerinespecter.oms.system.domain.dto.system.MenuChildInfo;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.3
 * @DateTime 2019年1月5日 02:07:12
 */
@Api(tags = "系统相关管理接口")
@RestController
public class SystemInfoController {

    @Resource
    private ISystemInfoService systemInfoService;

    /**
     * 首页初始化
     */
    @ApiOperation(value = "首页信息初始化")
    @GetMapping("/initHome")
    public HomePageDataDto initHome() {
        return systemInfoService.initHome();
    }

    /**
     * 菜单初始化
     */
    @ApiOperation(value = "菜单信息初始化")
    @GetMapping("/initMenu")
    public List<MenuChildInfo> initMenu() {
        return systemInfoService.initMenu();
    }

    /**
     * 消息通知
     */
    @ApiOperation(value = "消息通知")
    @GetMapping("/message")
    public List<MessageDto> message() {
        return systemInfoService.message();
    }

    /**
     * 日历
     */
    @ApiOperation(value = "日历页面")
    @GetMapping("/calendar")
    public ModelAndView calendar() {
        return ServiceResult.jumpPage("system/calendar");
    }

}
