package com.tangerinespecter.oms.system.controller.data;

import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.query.StockQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.service.data.IDataStockService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 股票池控制层
 *
 * @author tangerineSpecter
 * @version 0.0.7
 * @date 2020年04月15日01:39:16
 */
@RestController
@Api(tags = "股票池接口")
@RequestMapping("/data/stock")
public class DataStockController {
	
	@Resource
	private PageResultService pageResultService;
	@Resource
	private IDataStockService dataStockService;
	
	/**
	 * 股票池页面
	 */
	@ApiOperation("股票池页面")
	@RequiresPermissions("data:stock:page")
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getStockPageKey, "data/stock");
	}
	
	/**
	 * 股票池列表
	 */
	@ApiOperation("股票池列表")
	@AccessLimit(maxCount = 10)
	@GetMapping("/list")
	public ServiceResult listInfo(StockQueryObject qo) {
		return dataStockService.queryForPage(qo);
	}
}
