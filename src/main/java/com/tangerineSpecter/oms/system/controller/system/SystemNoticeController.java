package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/system/notice")
public class SystemNoticeController {

    @Resource
    private ISystemNoticeService systemNoticeService;

    /**
     * 实现服务器推送
     */
    @RequestMapping("/send")
    public ServiceResult messageSend(@Valid MessageVo vo) {
        return systemNoticeService.messageSend(vo);
    }

    @RequestMapping(value = "push", produces = "text/event-stream")
    public void push(HttpServletResponse response) {
        systemNoticeService.push(response);
    }
}
