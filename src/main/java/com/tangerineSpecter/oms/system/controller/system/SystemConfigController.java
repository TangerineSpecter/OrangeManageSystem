package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.system.domain.vo.system.SystemConfigInfoVo;
import com.tangerinespecter.oms.system.service.system.ISystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 系统配置控制
 *
 * @author 丢失的橘子
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "系统配置接口")
@RequestMapping("/system/config")
public class SystemConfigController {

    private final ISystemConfigService systemConfigServer;

    @ApiOperation("添加系统配置")
    @PostMapping("/insert")
    @LoggerInfo(value = "添加系统配置", event = LogOperation.EVENT_ADD)
    public void insertInfo(@Valid @RequestBody SystemConfigInfoVo vo) {
        systemConfigServer.insertInfo(vo);
    }

}
