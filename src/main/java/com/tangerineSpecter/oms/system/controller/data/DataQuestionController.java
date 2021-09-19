package com.tangerinespecter.oms.system.controller.data;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.QuestionQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.data.QuestionInfoVo;
import com.tangerinespecter.oms.system.service.data.IDataQuestionService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 问题管理控制
 *
 * @author TangerineSpecter
 * @version 0.1.1
 * @date 2020年04月24日00:29:41
 */
@RestController
@RequestMapping("/data/question")
public class DataQuestionController {

	@Resource
	private PageResultService pageResultService;
	@Resource
	private IDataQuestionService dataQuestionService;

	/**
	 * 问题管理页面
	 */
	@RequiresPermissions("data:question:page")
	@RequestMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getQuestionPageKey, "data/question");
	}

	/**
	 * 添加页面
	 */
	@RequestMapping("/addPage")
	public String addQuestionPage(Model model) {
		return "data/addEditQuestion";
	}

	/**
	 * 问题列表
	 */
	@AccessLimit(maxCount = 10)
	@RequestMapping("/list")
	public ServiceResult listInfo(QuestionQueryObject qo) {
		return dataQuestionService.queryForPage(qo);
	}

	/**
	 * 添加问题
	 */
	@RequestMapping("/insert")
	@LoggerInfo(value = "添加问题", event = LogOperation.EVENT_ADD)
	public ServiceResult insertInfo(@Valid() QuestionInfoVo vo) {
		return dataQuestionService.insertInfo(vo);
	}

	/**
	 * 修改问题
	 */
	@RequestMapping("/update")
	@LoggerInfo(value = "修改问题", event = LogOperation.EVENT_UPDATE)
	public ServiceResult update(@Valid() QuestionInfoVo vo) {
		return dataQuestionService.update(vo);
	}

	/**
	 * 删除问题
	 */
	@RequestMapping("/delete")
	@LoggerInfo(value = "删除问题", event = LogOperation.EVENT_DELETE)
	public ServiceResult delete(@RequestParam("id") Long id) {
		return dataQuestionService.delete(id);
	}
}
