package com.tangerinespecter.oms.common.result;

import com.tangerinespecter.oms.common.constants.RetCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果
 *
 * @author 丢失的橘子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult<T> {
    /**
     * 返回状态
     */
    private boolean success;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 总数
     */
    private Long count;

    private static ServiceResult result = new ServiceResult();

    private ServiceResult(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    private ServiceResult(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 请求成功
     */
    public static ServiceResult<Object> success() {
        return new ServiceResult(true, RetCode.SUCCESS.getErrorCode(), RetCode.SUCCESS.getErrorDesc());
    }

    /**
     * 请求成功
     */
    public static <T> ServiceResult<T> success(T data) {
        return new ServiceResult<>(true, RetCode.SUCCESS.getErrorCode(), RetCode.SUCCESS.getErrorDesc(), data);
    }

    /**
     * 返回页面结果
     */
    public static <T> ServiceResult<T> pageSuccess(T data, Long total) {
        return new ServiceResult<>(true, RetCode.SUCCESS.getErrorCode(), RetCode.SUCCESS.getErrorDesc(), data, total);
    }

    /**
     * 请求失败
     */
    public static ServiceResult fail() {
        return new ServiceResult(false, RetCode.FAIL.getErrorCode(), RetCode.FAIL.getErrorDesc());
    }

    /**
     * 参数错误
     */
    public static ServiceResult<Object> paramError() {
        return new ServiceResult(false, RetCode.PARAM_ERROR.getErrorCode(), RetCode.PARAM_ERROR.getErrorDesc());
    }

    /**
     * 请求失败
     */
    public static ServiceResult<Object> error(RetCode rc) {
        return new ServiceResult(false, rc.getErrorCode(), rc.getErrorDesc());
    }
}
