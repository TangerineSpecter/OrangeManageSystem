package com.tangerinespecter.oms.common.filter;


import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.config.ThreadPoolConfig;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemLog;
import com.tangerinespecter.oms.system.mapper.SystemLogMapper;
import lombok.RequiredArgsConstructor;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 日志切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ManageLogAspect {

    private final SystemLogMapper systemLogMapper;
    private final ThreadPoolConfig threadPoolConfig;

    @Pointcut("execution(* com.tangerinespecter.oms.system.controller..*.*(..))")
    public void controllerAopPointCut() {
    }

    @Around("controllerAopPointCut()")
    public Object controllerReturnBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Method methodSignature = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LoggerInfo loggerInfo = methodSignature.getAnnotation(LoggerInfo.class);
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + methodSignature.getName();
        String hostAddress = ServletUtil.getClientIP(request);
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        final ExecutorService executorService = threadPoolConfig.getExecutorService();
        executorService.execute(() -> logAccess(joinPoint.getArgs(), request.getRequestURI(), method, hostAddress, (endTime - startTime), loggerInfo));
        return proceed;
    }


    private void logAccess(Object[] args, String url, String method, String ipAddress, long requestTime,
                           LoggerInfo loggerInfo) {
        try {
            for (int index = 0; index < args.length; index++) {
                if (args[index] instanceof MultipartFile) {
                    args[index] = ((MultipartFile) args[index]).getOriginalFilename();
                }
            }
            if (loggerInfo != null && !loggerInfo.ignore()) {
                SystemLog systemLog = SystemLog.builder().method(method).username(UserContext.getNickName())
                    .operation(loggerInfo.value())
                    .params(JSON.toJSONString(handlerParams(args))).time(requestTime)
                    .event(loggerInfo.event().getValue()).ip(ipAddress).build();
                systemLogMapper.insert(systemLog);
                log.info("接口日志记录, 请求url: {}, 用户信息: {}, 请求参数: {}", url, UserContext.getNickName(), JSON.toJSONString(handlerParams(args)));
            }
        } catch (Exception e) {
            log.error("记录用户访问信息出错", e);
        }
    }

    /**
     * 处理参数
     *
     * @param args
     * @return
     */
    private List<Object> handlerParams(Object[] args) {
        List<Object> params = new ArrayList<>();
        for (Object param : args) {
            if (param instanceof HttpServletRequest || param instanceof HttpServletResponse) {
                continue;
            }
            params.add(param);
        }
        return params;
    }

}
