package com.tangerinespecter.oms.common.exception;

import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.result.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常拦截器
 *
 * @author TangerineSpecter
 * @date 2019年08月26日23:42:05
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    //定义拦截的异常类型，Exception拦截所有异常
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception exception) {
        if (exception instanceof AuthorizationException) {
            return new ModelAndView("/unauthorized");
        }
        if (exception instanceof GlobalException) {
            return new ModelAndView("/system/404");
        }
        //是否是绑定异常
        if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            //获取所有的错误信息
            List<ObjectError> allErrors = bindException.getAllErrors();
            ObjectError error = allErrors.get(0);
            String defaultMessage = error.getDefaultMessage();
            log.error("发生异常，异常信息{}", defaultMessage);
            return new ModelAndView("/system/404");
        }
        log.error("【操作失败】，系统发生异常：{}", exception);
        return new ModelAndView("/system/404");
    }
}
