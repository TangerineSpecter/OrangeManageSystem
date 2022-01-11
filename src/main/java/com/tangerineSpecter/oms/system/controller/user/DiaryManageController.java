package com.tangerinespecter.oms.system.controller.user;

import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日记管理
 *
 * @version 0.3.3
 * @date 2020年05月14日09:28:49
 */
@RestController
@Api(tags = "日记管理接口")
@RequestMapping("/user/diary")
public class DiaryManageController {
	
	@Resource
	private PageResultService pageResultService;
	
	/**
	 * 日记管理页面
	 */
	@ApiOperation(value = "日记管理页面")
	@RequiresPermissions("user:diary:page")
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String userHealthPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getUserDiaryPageKey, "user/diaryManage");
	}
}
