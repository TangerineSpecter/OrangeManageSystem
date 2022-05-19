package com.tangerinespecter.oms.common.filter;

import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.exception.GlobalException;
import com.tangerinespecter.oms.common.listener.AccessLimit;
import com.tangerinespecter.oms.common.redis.AccessKey;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求校验拦截器
 *
 * @author TangerineSpecter
 * @date 2019年09月14日16:17:43
 */
@Service
public class AccessInterceptor implements HandlerInterceptor {

    @Resource
    private RedisHelper redisHelper;

    /**
     * 方法执行前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            SystemUser currentUser = SystemUtils.getCurrentUser();
            if (accessLimit.needLogin()) {
                if (currentUser == null) {
                    throw new GlobalException(RetCode.LOGIN_TIMEOUT);
                }
            }
            String requestUrl = request.getRequestURI();
            String key = requestUrl + "_" + currentUser.getId();
            Integer count = (Integer) redisHelper.get(AccessKey.access, key);
            if (count == null) {
                redisHelper.set(AccessKey.access, key, 1, accessLimit.seconds());
            } else if (count < accessLimit.maxCount()) {
                redisHelper.incr(AccessKey.access, key, 1);
            } else {
                throw new BusinessException(RetCode.BUSY.getErrorDesc());
            }
        }
        return true;
    }
}
