package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.domain.vo.system.SystemConfigInfoVo;
import com.tangerinespecter.oms.system.service.system.ISystemConfigServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统配置空值
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Resource
    private ISystemConfigServer systemConfigServer;

    /**
     * 添加系统配置
     *
     * @param vo
     */
    @ResponseBody
    @RequestMapping("/insert")
    @LoggerInfo(value = "添加系统配置", event = LogOperation.EVENT_ADD)
    public ServiceResult insertInfo(@Valid SystemConfigInfoVo vo) {
        return systemConfigServer.insertInfo(vo);
    }

}
