package com.tangerinespecter.oms.system.controller.tools;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.GoodsFuturesInfoVo;
import com.tangerinespecter.oms.system.service.tools.IGoodsFuturesToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 商品期货小工具
 *
 * @author TangerineSpecter
 * @version 0.3.1
 * @date 2020年05月09日14:16:53
 */
@RestController
@Api(tags = "商品期货小工具接口")
@RequestMapping("/tools/futures")
public class GoodsFuturesToolController {
	
	@Resource
	private IGoodsFuturesToolService goodsFuturesToolService;

	@ApiOperation("商品期货小工具页面")
	@GetMapping("/page")
	@RequiresPermissions("tools:futures:page")
	public String pageInfo() {
		return "tools/goodsFutures";
	}
	
	/**
	 * 获取期货手续费
	 */
	@ApiOperation("获取期货手续费")
	@PostMapping("/create")
	public ServiceResult createFuturesInfo(@Valid GoodsFuturesInfoVo vo) {
		return goodsFuturesToolService.createFuturesInfo(vo);
	}
}
