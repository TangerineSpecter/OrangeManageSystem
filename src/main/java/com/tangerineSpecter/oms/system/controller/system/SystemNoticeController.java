package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 消息中心控制
 */
@RestController
@RequestMapping("/system/notice")
public class SystemNoticeController {

    @Resource
    private ISystemNoticeService systemNoticeService;

    /**
     * 实现服务器推送
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "push", produces = "text/event-stream")
    public void push(HttpServletResponse response) {
        systemNoticeService.push(response);
    }
}
