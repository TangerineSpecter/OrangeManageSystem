package com.tangerinespecter.oms.common.exception;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.job.schedule.SendMsgBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;

/**
 * 全局异常拦截器
 *
 * @author TangerineSpecter
 * @date 2019年08月26日23:42:05
 */
@Slf4j
@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SendMsgBot botService;

    /**
     * 定义拦截的异常类型，Exception拦截所有异常
     *
     * @param exception 异常
     * @return model
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public ModelAndView exceptionHandler(AuthorizationException exception) {
        log.error("发生异常，异常信息:[无操作权限]");
        return new ModelAndView("/error/403");
    }

    /**
     * 定义拦截的异常类型，Exception拦截所有异常
     *
     * @param exception 异常
     * @return 异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public ServiceResult<Object> exceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ServiceResult.systemError();
    }

    /**
     * 定义拦截的异常类型，模板异常
     *
     * @param exception 异常
     * @return 异常信息
     */
    @ExceptionHandler(value = TemplateInputException.class)
    public ModelAndView templateException(TemplateInputException exception) {
        log.error(exception.getMessage(), exception);
        return ServiceResult.jumpPage("/error/500");
    }

    /**
     * 业务异常类型
     *
     * @param exception 业务异常
     * @return 异常信息
     */
    @ExceptionHandler(value = BusinessException.class)
    public ServiceResult<Object> businessExceptionHandler(BusinessException exception) {
        botService.sendErrorMsg(exception.getCode(), exception.getMessage(), exception.getExtraInfo(), exception);
        log.error(exception.getMessage() + "额外信息：" + exception.getExtraInfo(), exception);
        return ServiceResult.error(exception.getMessage());
    }


    /**
     * 定义拦截的异常类型，绑定异常
     *
     * @param bindException 绑定异常
     * @return 异常信息
     */
    @ExceptionHandler(value = BindException.class)
    public ServiceResult<Object> validatorHandler(BindException bindException) {
        //获取所有的错误信息
        List<ObjectError> allErrors = bindException.getAllErrors();
        ObjectError error = allErrors.get(0);
        String defaultMessage = error.getDefaultMessage();
        log.error("发生异常，异常信息:[{}]", defaultMessage);
        return ServiceResult.validationError(defaultMessage);
    }

    /**
     * 定义拦截的异常类型，校验异常
     *
     * @param validException 校验异常
     * @return 异常信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ServiceResult<Object> validatorHandler(MethodArgumentNotValidException validException) {
        //获取所有的错误信息
        List<ObjectError> allErrors = validException.getAllErrors();
        ObjectError error = allErrors.get(0);
        String defaultMessage = error.getDefaultMessage();
        log.error("发生异常，异常信息:[{}]", defaultMessage);
        return ServiceResult.validationError(defaultMessage);
    }
}

