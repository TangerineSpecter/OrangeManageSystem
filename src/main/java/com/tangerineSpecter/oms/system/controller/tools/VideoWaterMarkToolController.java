package com.tangerinespecter.oms.system.controller.tools;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.VideoWatermarkInfoVo;
import com.tangerinespecter.oms.system.service.tools.IVideoWaterMarkToolService;
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
 * 视频水印处理小工具
 *
 * @author liangjun.zhou
 * @version 0.5.0
 * @date 2022年01月20日17:30:35
 */
@RestController
@Api(tags = "视频水印处理工具接口")
@RequestMapping("/tools/video-watermark")
public class VideoWaterMarkToolController {
	
	@Resource
	private IVideoWaterMarkToolService videoWaterMarkToolService;
	
	@GetMapping("/page")
	@ApiOperation(value = "视频水印页面跳转")
	@RequiresPermissions("tools:video-watermark:page")
	public ModelAndView pageInfo() {
		return ServiceResult.jumpPage("tools/videoWatermark");
	}
	
	/**
	 * 去除视频水印
	 */
	@PostMapping("/clear-watermark")
	@ApiOperation("去除视频水印")
	@LoggerInfo(value = "去除视频水印", event = LogOperation.EVENT_ADD)
	public ServiceResult clearVideoWatermark(@Validated VideoWatermarkInfoVo vo) throws Exception {
		return videoWaterMarkToolService.clearVideoWatermark(vo);
	}
}
