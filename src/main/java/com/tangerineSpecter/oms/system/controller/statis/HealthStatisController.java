package com.tangerinespecter.oms.system.controller.statis;

import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.statis.IHealthStatisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 健康管理
 *
 * @author 丢失的橘子
 * @version 0.3.1
 * @date 2020年05月09日23:02:39
 */
@RestController
@Api(tags = "健康统计接口")
@RequestMapping("/statis/health")
@RequiredArgsConstructor
public class HealthStatisController {
	
	private final PageResultService pageResultService;
	private final IHealthStatisService healthStatisService;
	
	/**
	 * 健康统计页面
	 */
	@ApiOperation("健康统计页面")
	@RequiresPermissions("statis:health:page")
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("healthInfo", healthStatisService.healthStatisInfo());
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getHealthStatisPageKey, "statis/healthStatis");
	}
	
}
