package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.domain.vo.system.NoticeUpdateStatusVo;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 消息中心控制
 *
 * @author TangerineSpecter
 * @version 0.4.0
 * @date 2020年05月27日22:42:32
 */
@RestController
@Api(tags = "消息中心接口")
@RequestMapping("/system/notice")
public class SystemNoticeController {
	
	@Resource
	private ISystemNoticeService systemNoticeService;
	
	/**
	 * 消息内容
	 */
	@ApiOperation(value = "消息中心页面")
	@GetMapping("/content")
	public ModelAndView noticeCenter(Model model, @RequestParam("id") Long id) {
		SystemNotice noticeInfo = systemNoticeService.getNoticeInfo(id);
		model.addAttribute("noticeInfo", noticeInfo);
		return ServiceResult.jumpPage("system/systemNoticeContent");
	}
	
	/**
	 * 消息中心列表
	 */
	@ApiOperation(value = "消息中心列表")
	@GetMapping("/list")
	public ServiceResult listInfo(SystemNoticeQueryObject qo) {
		return systemNoticeService.queryForPage(qo);
	}
	
	/**
	 * 批量已读状态
	 */
	@ApiOperation(value = "批量更新消息状态")
	@PutMapping("/batchReadStatus")
	public ServiceResult batchUpdateReadStatus(NoticeUpdateStatusVo vo) {
		return systemNoticeService.batchUpdateReadStatus(vo);
	}
	
	/**
	 * 批量修改删除状态
	 */
	@ApiOperation(value = "批量删除消息")
	@DeleteMapping("/batchDelete")
	public ServiceResult batchUpdateDelStatus(NoticeUpdateStatusVo vo) {
		return systemNoticeService.batchUpdateDelStatus(vo);
	}
	
	/**
	 * 彻底清理消息
	 */
	@ApiOperation(value = "彻底清除消息")
	@DeleteMapping("/batchClear")
	public ServiceResult batchClear(NoticeUpdateStatusVo vo) {
		return systemNoticeService.batchClear(vo);
	}
	
	/**
	 * 实现服务器推送
	 */
	@ApiOperation(value = "消息发送")
	@PostMapping("/send")
	public ServiceResult messageSend(@Valid MessageVo vo) {
		return systemNoticeService.messageSend(vo);
	}
	
	@ApiOperation(value = "消息推送")
	@PostMapping(value = "push", produces = "text/event-stream")
	public void push(HttpServletResponse response) {
		systemNoticeService.push(response);
	}
}
