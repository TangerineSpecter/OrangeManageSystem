package com.tangerinespecter.oms.common.filter;


import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 日志切面
 */
@Slf4j
@Aspect
@Component
public class ManageLogAspect {

    private static final Executor executor = Executors.newFixedThreadPool(20, r -> {
        Thread t = new Thread(r);
        t.setName("log-aspect-" + t.getId());
        /*打开守护线程*/
        t.setDaemon(true);
        return t;
    });

    private static List<String> serviceKey = new ArrayList<>();

    static {
        serviceKey.add(ServiceKey.System.SYSTEM_USER_PAGE_LIST);
        serviceKey.add(ServiceKey.Constellation.CONSTELLATION_PAGE_LIST);
        serviceKey.add(ServiceKey.Work.COLLECTION_PAGE_LIST);
        serviceKey.add(ServiceKey.System.SYSTEM_USER_CALENDAR);
    }

    @Pointcut("execution(* com.tangerinespecter.oms.system.web.controller..*.*(..))")
    public void controllerAopPointCut() {
    }


    @Before("controllerAopPointCut()")
    public void controllerReturnBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        executor.execute(() -> logAccess(token, joinPoint.getArgs(), request.getRequestURI()));
    }


    private void logAccess(String token, Object[] args, String url) {
        try {
            String userStr = "【TangerineSpecter】";
            if (!serviceKey.contains(url)) {
                log.info("接口日志记录, 请求url: {}, 用户信息: {}, 请求参数: {}, token：{}", url, userStr, JSON.toJSONString(args), token);
            }
        } catch (Exception e) {
            log.error("记录用户访问信息出错", e);
        }
    }

}
