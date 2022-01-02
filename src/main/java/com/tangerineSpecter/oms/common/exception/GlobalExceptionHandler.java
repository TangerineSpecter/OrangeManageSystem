package com.tangerinespecter.oms.common.exception;

import com.tangerinespecter.oms.common.result.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 定义拦截的异常类型，Exception拦截所有异常
     *
     * @param exception 异常
     * @return model
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public ModelAndView exceptionHandler(AuthorizationException exception) {
        log.error("发生异常，异常信息:[{}]", "无操作权限");
        return new ModelAndView("/unauthorized");
    }

    /**
     * 定义拦截的异常类型，Exception拦截所有异常
     *
     * @param exception 异常
     * @return model
     */
    @ExceptionHandler(value = Exception.class)
    public ServiceResult exceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ServiceResult.systemError();
    }


    /**
     * 定义拦截的异常类型，绑定异常
     *
     * @param bindException 异常
     * @return model
     */
    @ExceptionHandler(value = BindException.class)
    public ServiceResult validatorHandler(BindException bindException) {
        //获取所有的错误信息
        List<ObjectError> allErrors = bindException.getAllErrors();
        ObjectError error = allErrors.get(0);
        String defaultMessage = error.getDefaultMessage();
        log.error("发生异常，异常信息:[{}]", defaultMessage);
        return ServiceResult.validationError(defaultMessage);
    }
}
