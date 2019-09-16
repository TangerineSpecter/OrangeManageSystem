package com.tangerinespecter.oms.system.service.system;

import javax.servlet.http.HttpServletResponse;

public interface ISystemNoticeService {

    /**
     * 消息推送
     *
     * @param response
     */
    void push(HttpServletResponse response);
}
