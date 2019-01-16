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
	/** 星座 */
	private static final String STAR = "摩羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";

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

	/**
	 * 根据时间获取星座
	 * 
	 * @param date
	 *            时间 格式：yyyy-MM-dd
	 * @return
	 */
	public static String getStarNameByDate(String date) {
		String[] dateArr = date.split("-");
		if (dateArr.length != 3) {
			return null;
		}
		Integer month = Integer.valueOf(dateArr[1]);
		Integer day = Integer.valueOf(dateArr[2]);
		Integer[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		Integer num = month * 2 - (day < arr[month - 1] ? 2 : 0);
		return STAR.substring(num, num + 2);
	}
}
