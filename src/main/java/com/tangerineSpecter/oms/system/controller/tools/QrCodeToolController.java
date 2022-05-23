package com.tangerinespecter.oms.system.controller.tools;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.QrCodeInfoVo;
import com.tangerinespecter.oms.system.service.tools.IQrCodeToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 二维码生成工具
 *
 * @author liangjun.zhou
 * @date 2019年09月08日00:01:21
 */
@RestController
@Api(tags = "二维码生成工具接口")
@RequestMapping("/tools/qr-code")
public class QrCodeToolController {
	
	@Resource
	private IQrCodeToolService qrCodeToolService;
	
	@GetMapping("/page")
	@ApiOperation(value = "二维码页面跳转")
	@RequiresPermissions("tools:qr-code:page")
	public ModelAndView pageInfo() {
		return ServiceResult.jumpPage("tools/qrCode");
	}
	
	/**
	 * 生成二维码
	 */
	@PostMapping("/create")
	@ApiOperation("创建二维码")
	@LoggerInfo(value = "创建二维码", event = LogOperation.EVENT_ADD)
	public ServiceResult createQrCode(@Validated QrCodeInfoVo vo) {
		return qrCodeToolService.createQrCode(vo);
	}
}
