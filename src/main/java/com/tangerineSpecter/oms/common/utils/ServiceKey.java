package com.tangerineSpecter.oms.common.utils;

/**
 * 请求地址
 * 
 * @author TangerineSpecter
 * @Date 2019年1月9日
 * @version v0.0.5
 */
public class ServiceKey {

	public class System {
		/** 系统默认路径 */
		public static final String SYSTEM_DEFAULT = "/";
		/** 系统登录 */
		public static final String SYSTEM_LOGIN = "/login";
		/** 系统注册 */
		public static final String SYSTEM_REGISTER = "/register";
		/** 系统主页 */
		public static final String SYSTEM_INDEX_PAGE = "/index";
		/** 管理员页面 */
		public static final String SYSTEM_USER_PAGE_LIST = "/systemUser";
		/** 管理员信息更新 */
		public static final String SYSTEM_USER_UPDATE = "/systemUser/update";
		/** 管理员帐号设置 */
		public static final String SYSTEM_USER_SETTING = "/accountSetting";
		/** 系统主页 */
		public static final String SYSTEM_HOME = "/home";
	}

	public class Constellation {
		/** 星座页面 */
		public static final String CONSTELLATION_PAGE_LIST = "/constellaction";
	}

}
