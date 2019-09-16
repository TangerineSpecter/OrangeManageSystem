package com.tangerinespecter.oms.common.exception;

import com.tangerinespecter.oms.common.constants.RetCode;
import lombok.Getter;

/**
 * 全局异常
 *
 * @author TangerineSpecter
 * @date 2019年08月27日00:11:04
 */
@Getter
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private RetCode retCode;

    public GlobalException(RetCode retCode) {
        super(retCode.toString());
        this.retCode = retCode;
    }

}
