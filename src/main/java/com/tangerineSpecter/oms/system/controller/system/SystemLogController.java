package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.SystemLogQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.LoggerListDto;
import com.tangerinespecter.oms.system.service.system.ISystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统日志控制
 *
 * @author TangerineSpecter
 * @date
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "系统日志接口")
@RequestMapping("/system/log")
public class SystemLogController {

    private final ISystemLogService systemLogService;

    /**
     * 日志
     */
    @ApiOperation(value = "系统日志页面")
    @GetMapping("/page")
    @RequiresPermissions("system:log:page")
    public ModelAndView pageInfo() {
        return ServiceResult.jumpPage("system/logger");
    }

    /**
     * 日志列表
     */
    @ApiOperation(value = "系统日志列表")
    @PostMapping("/list")
    public PageInfo<LoggerListDto> listInfo(@RequestBody QueryObject<SystemLogQueryObject> qo) {
        return systemLogService.queryForPage(qo);
    }
}
