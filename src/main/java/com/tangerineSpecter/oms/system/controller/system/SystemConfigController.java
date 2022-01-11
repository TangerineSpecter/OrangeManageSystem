package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.system.SystemConfigInfoVo;
import com.tangerinespecter.oms.system.service.system.ISystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统配置控制
 */
@RestController
@Api(tags = "系统配置接口")
@RequestMapping("/system/config")
public class SystemConfigController {
	
	@Resource
	private ISystemConfigService systemConfigServer;
	
	/**
	 * 添加系统配置
	 */
	@ApiOperation("添加系统配置")
	@PostMapping("/insert")
	@LoggerInfo(value = "添加系统配置", event = LogOperation.EVENT_ADD)
	public ServiceResult insertInfo(@Valid SystemConfigInfoVo vo) {
		return systemConfigServer.insertInfo(vo);
	}
	
}
