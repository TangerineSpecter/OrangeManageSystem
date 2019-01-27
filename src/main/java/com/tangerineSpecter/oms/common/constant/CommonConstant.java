package com.tangerineSpecter.oms.common.constant;

/**
 * 默认常量类
 * 
 * @author TangerineSpecter
 * @DateTime 2019年1月4日 23:02:18
 * @version v0.0.2
 *
 */
public class CommonConstant {
	/**
	 * 聚合数据接口Key
	 */
	public static final String JUHE_API_USER_KEY = "JH3975ebd2d5d3aeb9153b448d3befb861";
	/** 聚合数据--星座key */
	public static final String JUHE_API_CONSTELLATION_KEY = "47f6796c7b8b99c47c176d56adf4f0a8";
	/** 聚合数据--星座Url */
	public static final String JUHE_API_CONSTELLATION_URL = "http://web.juhe.cn:8080/constellation/getAll";
	/** key */
	public static final String SALT = "orange";

	/** 空定义 */
	public static final String NULL_KEY_STR = "";

	/** 一小时 */
	public static final Integer ONE_HOUR = 60 * 60 * 1000;

	/** 未删除 */
	public static final Integer IS_DEL_NO = 0;
	/** 已删除 */
	public static final Integer IS_DEL_YES = 1;

	/** 默认格式 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
	/** 时间格式——精确到秒 */
	public static final String DEFAULT_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 基本常量
	 */
	public static class Number {
		/** 数字0 */
		public static final Integer COMMON_NUMBER_ZERO = 0;
		/** 数字1 */
		public static final Integer COMMON_NUMBER_FIRST = 1;
		/** 数字2 */
		public static final Integer COMMON_NUMBER_SECOND = 2;
		/** 数字3 */
		public static final Integer COMMON_NUMBER_THIRD = 3;
	}
}
