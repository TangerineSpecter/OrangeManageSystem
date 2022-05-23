package com.tangerinespecter.oms.common.exception;

import com.tangerinespecter.oms.common.constants.RetCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业 务 异 常
 *
 * @author Tangerinespecter
 */
@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    protected final Integer code;
    /**
     * 异常消息
     */
    protected final String message;

    /**
     * 业务异常
     */
    public BusinessException(RetCode retCode) {
        this.code = retCode.getErrorCode();
        this.message = retCode.getErrorDesc();
    }

}
