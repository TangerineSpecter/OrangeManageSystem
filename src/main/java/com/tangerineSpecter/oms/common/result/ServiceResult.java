package com.tangerinespecter.oms.common.result;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.RetCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 响应结果
 *
 * @author 丢失的橘子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings({"unchecked"})
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
    public static <R> ServiceResult<R> success() {
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
     * 返回页面结果
     */
    public static <T> ServiceResult<T> pageSuccess(PageInfo<T> pageInfo) {
        return new ServiceResult(true, RetCode.SUCCESS.getErrorCode(), RetCode.SUCCESS.getErrorDesc(), pageInfo.getList(), pageInfo.getTotal());
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
    public static <R> ServiceResult<R> paramError() {
        return new ServiceResult(false, RetCode.PARAM_ERROR.getErrorCode(), RetCode.PARAM_ERROR.getErrorDesc());
    }

    /**
     * 系统错误
     */
    public static <R> ServiceResult<R> systemError() {
        return new ServiceResult(false, RetCode.SYSTEM_ERROR.getErrorCode(), RetCode.SYSTEM_ERROR.getErrorDesc());
    }

    /**
     * 请求失败
     */
    public static <R> ServiceResult<R> error(RetCode rc) {
        return new ServiceResult(false, rc.getErrorCode(), rc.getErrorDesc());
    }

    /**
     * 请求失败
     */
    public static <R> ServiceResult<R> validationError(String message) {
        return new ServiceResult(false, RetCode.DATA_EXCEPTION.getErrorCode(), message);
    }

    /**
     * 页面跳转
     *
     * @param pagePath 跳转路径
     */
    public static ModelAndView jumpPage(String pagePath) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(pagePath);
        return modelAndView;
    }
}
