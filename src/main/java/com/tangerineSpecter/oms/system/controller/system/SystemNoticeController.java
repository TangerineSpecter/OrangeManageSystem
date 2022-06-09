package com.tangerinespecter.oms.system.controller.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.domain.vo.system.NoticeUpdateStatusVo;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@ReWriteBody
@RestController
@RequiredArgsConstructor
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
        model.addAttribute("noticeInfo", systemNoticeService.getNoticeInfo(id));
        return ServiceResult.jumpPage("system/systemNoticeContent");
    }

    /**
     * 消息中心列表
     */
    @ApiOperation(value = "消息中心列表")
    @GetMapping("/list")
    public PageInfo<SystemNotice> listInfo(SystemNoticeQueryObject qo) {
        return systemNoticeService.queryForPage(qo);
    }

    /**
     * 批量已读状态
     */
    @ApiOperation(value = "批量更新消息状态")
    @PutMapping("/batch/read-status")
    public void batchUpdateReadStatus(NoticeUpdateStatusVo vo) {
        systemNoticeService.batchUpdateReadStatus(vo);
    }

    /**
     * 批量修改删除状态
     */
    @ApiOperation(value = "批量删除消息")
    @DeleteMapping("/batch/delete")
    public void batchUpdateDelStatus(NoticeUpdateStatusVo vo) {
        systemNoticeService.batchUpdateDelStatus(vo);
    }

    /**
     * 彻底清理消息
     */
    @ApiOperation(value = "彻底清除消息")
    @DeleteMapping("/batch/clear")
    public void batchClear(NoticeUpdateStatusVo vo) {
        systemNoticeService.batchClear(vo);
    }

    /**
     * 实现服务器推送
     */
    @ApiOperation(value = "消息发送")
    @PostMapping("/send")
    public void messageSend(@Valid MessageVo vo) {
        systemNoticeService.messageSend(vo);
    }

    @ApiOperation(value = "消息推送")
    @PostMapping(value = "push", produces = "text/event-stream")
    public void push(HttpServletResponse response) {
        systemNoticeService.push(response);
    }
}
