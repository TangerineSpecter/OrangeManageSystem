package com.tangerinespecter.oms.common.filter;


import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemLog;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.mapper.SystemLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 日志切面
 */
@Slf4j
@Aspect
@Component
public class ManageLogAspect {

    @Resource
    private SystemLogMapper systemLogMapper;

    private static final Executor executor = Executors.newFixedThreadPool(20, r -> {
        Thread t = new Thread(r);
        t.setName("log-aspect-" + t.getId());
        /*打开守护线程*/
        t.setDaemon(true);
        return t;
    });

    @Pointcut("execution(* com.tangerinespecter.oms.system.controller..*.*(..))")
    public void controllerAopPointCut() {
    }

    @Around("controllerAopPointCut()")
    public Object controllerReturnBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        SystemUser systemUser = SystemUtils.getCurrentUser();

        Method methodSignature = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LoggerInfo loggerInfo = methodSignature.getAnnotation(LoggerInfo.class);
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + methodSignature.getName();
        String hostAddress = SystemUtils.getIpAddr(request);
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        executor.execute(() -> {
            logAccess(systemUser, joinPoint.getArgs(), request.getRequestURI(), method, hostAddress, (endTime - startTime), loggerInfo);
        });
        return proceed;
    }


    private void logAccess(SystemUser systemUser, Object[] args, String url, String method, String ipAddress, long requestTime, LoggerInfo loggerInfo) {
        if (systemUser == null) {
            return;
        }
        try {
            for (int index = 0; index < args.length; index++) {
                if (args[index] instanceof MultipartFile) {
                    args[index] = ((MultipartFile) args[index]).getOriginalFilename();
                }
            }
            if (loggerInfo != null && !loggerInfo.ignore()) {
                SystemLog systemLog = SystemLog.builder().method(method).username(systemUser.getUsername()).operation(loggerInfo.value())
                        .params(JSON.toJSONString(args)).time(requestTime).event(loggerInfo.event().getValue()).ip(ipAddress).build();
                systemLogMapper.insert(systemLog);
                log.info("接口日志记录, 请求url: {}, 用户信息: {}, 请求参数: {}", url, systemUser.getUsername(), JSON.toJSONString(args));
            }
        } catch (Exception e) {
            log.error("记录用户访问信息出错", e);
        }
    }

}
