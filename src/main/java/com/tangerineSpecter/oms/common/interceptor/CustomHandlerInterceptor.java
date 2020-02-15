package com.tangerinespecter.oms.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义Http拦截器
 *
 * @author TangerineSpecter
 * @version v0.0.1
 * @Datetime 2019年1月4日 12:55:35
 */
@Service
public class CustomHandlerInterceptor implements HandlerInterceptor {

    /**
     * 请求之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long start = System.currentTimeMillis();
        // 一定得为true，否则拦截器就无法生效了
        return true;
    }

    /**
     * 请求成功执行
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object handler, ModelAndView modelAndView) throws Exception {
        // System.out.println("======================");
        // System.out.println("开始处理拦截");
        // System.out.println("【拦截器】耗时 " + (System.currentTimeMillis() -
        // start));
    }

    /**
     * 请求结束执行
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object handler, Exception e) throws Exception {
        // System.out.println("处理拦截之后");
        // System.out.println("【拦截器】耗时 " + (new Date().getTime() - start));
    }
}
