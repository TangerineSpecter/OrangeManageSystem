package com.tangerineSpecter.oms.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tangerineSpecter.oms.common.constant.CommonConstant;

/**
 * 时间工具类
 * 
 * @author TangerineSpecter
 * @Date 2019年1月7日
 * @version v0.0.5
 */
public class DateUtils {

	/** 默认格式 */
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

	/**
	 * 将时间转换成指定格式 yyyy-MM-dd 精确到天
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public static String timeFormatToDay(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
		if (date != null) {
			return format.format(date);
		}
		return CommonConstant.NULL_KEY_STR;
	}

	/**
	 * 获取指定格式的当前时间
	 * 
	 * @param format
	 *            格式,如:yyyy-MM-dd
	 * @return
	 */
	public static String getSimpleFormat(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
}
