package com.tangerineSpecter.oms.common.service;

import com.tangerineSpecter.oms.common.constant.RetCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果
 * 
 * @author 丢失的橘子
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult {
	/**
	 * 返回状态
	 */
	private boolean success;
	/**
	 * 错误码
	 */
	private Integer errorCode;
	/**
	 * 错误信息
	 */
	private String errorDesc;
	/**
	 * 返回数据
	 */
	private Object data;

	private static ServiceResult result = new ServiceResult();

	/**
	 * 请求成功
	 */
	public static ServiceResult success() {
		result.setSuccess(true);
		result.setErrorCode(RetCode.SUCCESS_CODE);
		result.setErrorDesc(RetCode.SUCCESS_DESC);
		return result;
	}

	/**
	 * 请求成功
	 */
	public static ServiceResult success(Object data) {
		result.setSuccess(true);
		result.setErrorCode(RetCode.SUCCESS_CODE);
		result.setErrorDesc(RetCode.SUCCESS_DESC);
		result.setData(data);
		return result;
	}

	/**
	 * 请求失败
	 */
	public static ServiceResult fail() {
		result.setSuccess(false);
		result.setErrorCode(RetCode.FAIL_CODE);
		result.setErrorDesc(RetCode.FAIL_DESC);
		return result;
	}

	/**
	 * 请求失败
	 */
	public static ServiceResult fail(Integer errorCode, String errorDesc) {
		result.setSuccess(false);
		result.setErrorCode(errorCode);
		result.setErrorDesc(errorDesc);
		return result;
	}
}
