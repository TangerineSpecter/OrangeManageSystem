package com.tangerinespecter.oms.common.utils;

/**
 * 请求地址
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月9日
 */
public class ServiceKey {

    public class System {
        /**
         * 系统登录
         */
        public static final String SYSTEM_LOGIN = "/login";
        /**
         * 系统注册
         */
        public static final String SYSTEM_REGISTER = "/register";
        /**
         * 管理员页面
         */
        public static final String SYSTEM_USER_PAGE_LIST = "/systemUser";
        /**
         * 日历
         */
        public static final String SYSTEM_USER_CALENDAR = "/calendar";
        /**
         * 系统主页
         */
        public static final String SYSTEM_HOME = "/home";
    }

    public static class Work {
        /**
         * 收藏页面
         */
        public static final String COLLECTION_PAGE_LIST = "/collection";
        /**
         * 新增收藏
         */
        public static final String COLLECTION_ADD = "/collection/add";
        /**
         * 删除收藏
         */
        public static final String COLLECTION_DELETE = "/collection/delete";
    }
}
