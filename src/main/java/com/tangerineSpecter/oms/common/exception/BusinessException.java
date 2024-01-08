package com.tangerinespecter.oms.common.exception;

import cn.hutool.core.util.StrUtil;
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

    /**
     * 错误码
     */
    protected final Integer code;
    /**
     * 错误描述
     */
    protected final String message;

    /**
     * 额外信息
     */
    protected String extraInfo;

    /**
     * 业务异常
     */
    public BusinessException(RetCode retCode) {
        this.code = retCode.getErrorCode();
        this.message = retCode.getErrorDesc();
    }

    public BusinessException(RetCode retCode, String extraInfo) {
        this.code = retCode.getErrorCode();
        this.message = retCode.getErrorDesc();
        this.extraInfo = extraInfo;
    }

    public BusinessException(RetCode retCode, String extraInfo, Object... param) {
        this.code = retCode.getErrorCode();
        this.message = retCode.getErrorDesc();
        this.extraInfo = StrUtil.format(extraInfo, param);
    }

}
