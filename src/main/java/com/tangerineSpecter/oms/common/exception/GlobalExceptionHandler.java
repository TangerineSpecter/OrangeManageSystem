package com.tangerinespecter.oms.common.exception;

import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.result.ServiceResult;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    //定义拦截的异常类型，Exception拦截所有异常
    @ExceptionHandler(value = Exception.class)
    public ServiceResult exceptionHandler(HttpServletRequest request, Exception exception) {
        if (exception instanceof GlobalException) {
            GlobalException globalException = (GlobalException) exception;
            return ServiceResult.error(globalException.getRetCode());
        }
        //是否是绑定异常
        if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            //获取所有的错误信息
            List<ObjectError> allErrors = bindException.getAllErrors();
            ObjectError error = allErrors.get(0);
            String defaultMessage = error.getDefaultMessage();
            return ServiceResult.error(RetCode.PARAM_ERROR.fillArgs(defaultMessage));
        }
        log.error("【操作失败】，系统发生异常：{}", exception.getMessage());
        return ServiceResult.fail();
    }
}
