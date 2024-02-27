package com.tangerinespecter.oms.common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session在线人数监听器
 *
 * @author 丢失的橘子
 * @version 0.2.1
 * @date 2020年05月02日22:52:04
 */
@WebListener
public class OnlineListener implements HttpSessionListener {

    /**
     * 记录当前登录用户数
     */
    private int onlineNumber = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //监听到创建新的session
        onlineNumber++;
        se.getSession().setAttribute("onlineNumber", onlineNumber);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //监听销毁旧的session
        se.getSession().setAttribute("onlineNumber", onlineNumber);
    }
}
