package com.tangerinespecter.oms.common.exception;

import com.tangerinespecter.oms.common.constant.RetCode;
import com.tangerinespecter.oms.common.result.ServiceResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常拦截器
 *
 * @author TangerineSpecter
 * @date 2019年08月26日23:42:05
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    //定义拦截的异常类型，Exception拦截所有异常
    @ExceptionHandler(value = Exception.class)
    public ServiceResult exceptionHandler(HttpServletRequest request, Exception exception) {
        //是否是绑定异常
        if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            //获取所有的错误信息
            List<ObjectError> allErrors = bindException.getAllErrors();
            ObjectError error = allErrors.get(0);
            String defaultMessage = error.getDefaultMessage();
            return ServiceResult.error(RetCode.PARAM_ERROR.fillArgs(defaultMessage));
        }
        return ServiceResult.fail();
    }
}
