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
import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * 问题管理控制
 *
 * @author TangerineSpecter
 * @version 0.1.1
 * @date 2020年04月24日00:29:41
 */
@RestController
@Api(tags = "问题管理接口")
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
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getQuestionPageKey, "data/question");
	}
	
	/**
	 * 添加页面
	 */
	@GetMapping("/addPage")
	public ModelAndView addQuestionPage(Model model) {
		return ServiceResult.jumpPage("data/addEditQuestion");
	}
	
	/**
	 * 问题列表
	 */
	@AccessLimit(maxCount = 10)
	@GetMapping("/list")
	public ServiceResult listInfo(QuestionQueryObject qo) {
		return dataQuestionService.queryForPage(qo);
	}
	
	/**
	 * 添加问题
	 */
	@PostMapping("/insert")
	@LoggerInfo(value = "添加问题", event = LogOperation.EVENT_ADD)
	public ServiceResult insertInfo(@Validated @RequestBody QuestionInfoVo vo) {
		return dataQuestionService.insertInfo(vo);
	}
	
	/**
	 * 修改问题
	 */
	@PutMapping("/update")
	@LoggerInfo(value = "修改问题", event = LogOperation.EVENT_UPDATE)
	public ServiceResult update(@Validated(Update.class) @RequestBody QuestionInfoVo vo) {
		return dataQuestionService.update(vo);
	}
	
	/**
	 * 删除问题
	 */
	@DeleteMapping("/delete/{id}")
	@LoggerInfo(value = "删除问题", event = LogOperation.EVENT_DELETE)
	public ServiceResult delete(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
		return dataQuestionService.delete(id);
	}
}
