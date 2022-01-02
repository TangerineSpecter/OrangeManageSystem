package com.tangerinespecter.oms.common.exception;

/**
 * 业 务 异 常
 *
 * @author Tangerinespecter
 */
public class BusinessException extends RuntimeException {

    /**
     * 异常消息
     */
    protected final String message;

    /**
     * 业务异常
     */
    public BusinessException(String message) {
        this.message = message;
    }

    /**
     * 异常获取
     */
    @Override
    public String getMessage() {
        return message;
    }

}
