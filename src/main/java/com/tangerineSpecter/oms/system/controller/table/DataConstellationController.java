package com.tangerinespecter.oms.system.controller.table;

import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.table.IDataConstellationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 星座数据相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @date 2019年1月8日
 */
@RestController
@Api(tags = "星座数据接口")
@RequiredArgsConstructor
@RequestMapping("/table/constellation")
public class DataConstellationController {
	
	private final IDataConstellationService dataConstellationService;

	private final PageResultService pageResultService;
	
	/**
	 * 星座页面
	 */
	@ApiOperation(value = "星座页面")
	@RequiresPermissions("table:constellation:page")
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getConstellationPageKey, "data/constellation");
	}
	
	/**
	 * 星座列表
	 */
	@AccessLimit(maxCount = 10)
	@ApiOperation("星座列表")
	@GetMapping("/list")
	public ServiceResult<List<DataConstellation>> listInfo(QueryObject<ConstellationQueryObject> qo) {
		return ServiceResult.pageSuccess(dataConstellationService.queryForPage(qo));
	}
}
