package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.domain.vo.system.NoticeUpdateStatusVo;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@RequestMapping("/system/notice")
public class SystemNoticeController {

    @Resource
    private ISystemNoticeService systemNoticeService;

    /**
     * 消息内容
     */
    @RequestMapping("/content")
    public String noticeCenter(Model model, @RequestParam("id") Long id) {
        SystemNotice noticeInfo = systemNoticeService.getNoticeInfo(id);
        model.addAttribute("noticeInfo", noticeInfo);
        return "system/systemNoticeContent";
    }

    /**
     * 消息中心列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult listInfo(SystemNoticeQueryObject qo) {
        return systemNoticeService.queryForPage(qo);
    }

    /**
     * 批量已读状态
     */
    @ResponseBody
    @RequestMapping("/batchReadStatus")
    public ServiceResult batchUpdateReadStatus(NoticeUpdateStatusVo vo) {
        return systemNoticeService.batchUpdateReadStatus(vo);
    }

    /**
     * 批量修改删除状态
     */
    @ResponseBody
    @RequestMapping("/batchDelete")
    public ServiceResult batchUpdateDelStatus(NoticeUpdateStatusVo vo) {
        return systemNoticeService.batchUpdateDelStatus(vo);
    }

    /**
     * 彻底清理消息
     */
    @ResponseBody
    @RequestMapping("/batchClear")
    public ServiceResult batchClear(NoticeUpdateStatusVo vo) {
        return systemNoticeService.batchClear(vo);
    }

    /**
     * 实现服务器推送
     */
    @ResponseBody
    @RequestMapping("/send")
    public ServiceResult messageSend(@Valid MessageVo vo) {
        return systemNoticeService.messageSend(vo);
    }

    @ResponseBody
    @RequestMapping(value = "push", produces = "text/event-stream")
    public void push(HttpServletResponse response) {
        systemNoticeService.push(response);
    }
}
