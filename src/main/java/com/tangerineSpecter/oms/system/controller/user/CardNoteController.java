package com.tangerinespecter.oms.system.controller.user;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.user.ICardNoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 卡片笔记
 *
 * @author 丢失的橘子
 * @date 2022年1月16日 21:56:51
 */
@Api(tags = "卡片笔记模块")
@RestController
@RequestMapping("/user/card-note")
public class CardNoteController {
	
	@Resource
	private PageResultService pageResultService;
	@Resource
	private ICardNoteService cardNoteService;
	
	/**
	 * 卡片笔记界面
	 */
	@ApiOperation(value = "卡片笔记界面")
	@RequiresPermissions("user:card-note:page")
	@GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
	public String cardNotePage(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("noteList", cardNoteService.queryForPage(new UserCardNoteQueryObject()).getData());
		System.out.println(cardNoteService.queryForPage(new UserCardNoteQueryObject()).getData());
		return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getCardNotePageKey, "user/cardNoteManage");
	}
	
	/**
	 * 卡片日记内容
	 */
	@ApiOperation(value = "卡片笔记列表")
	@GetMapping(value = "/list")
	public ServiceResult queryForPage(Model model, UserCardNoteQueryObject qo) {
		model.addAttribute("noteList", cardNoteService.queryForPage(new UserCardNoteQueryObject()).getData());
		return cardNoteService.queryForPage(qo);
	}
	
	/**
	 * 新增卡片笔记
	 */
	@ApiOperation(value = "新增卡片笔记")
	@LoggerInfo(value = "新增卡片笔记", event = LogOperation.EVENT_ADD)
	@PostMapping(value = "/insert")
	public ServiceResult insert(@RequestBody @Validated CardNoteInfoVo vo) {
		return cardNoteService.insert(vo);
	}
	
	/**
	 * 删除卡片笔记
	 */
	@ApiOperation(value = "删除卡片笔记")
	@LoggerInfo(value = "新增卡片笔记", event = LogOperation.EVENT_ADD)
	@DeleteMapping(value = "/delete/{id}")
	public ServiceResult delete(@PathVariable("id") Long id) {
		return cardNoteService.delete(id);
	}
}
