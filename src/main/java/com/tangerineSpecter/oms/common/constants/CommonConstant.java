package com.tangerinespecter.oms.common.constants;

import cn.hutool.core.map.MapUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 默认常量类
 *
 * @author TangerineSpecter
 * @version v0.0.2
 * @DateTime 2019年1月4日 23:02:18
 */
public class CommonConstant {

    /**
     * 用户key
     */
    public static final String SALT = "orange666";
    /**
     * 菜单code
     */
    public static final String MENU_CODE = "smallPotatoIsMyCat";
    /**
     * 权限code
     */
    public static final String PERMISSION_CODE = "myCatIsSiamese";

    /**
     * 空定义
     */
    public static final String NULL_KEY_STR = "";

    /**
     * 一小时
     */
    public static final Integer ONE_HOUR = 60 * 60 * 1000;

    /**
     * 未删除
     */
    public static final Integer IS_DEL_NO = 0;
    /**
     * 已删除
     */
    public static final Integer IS_DEL_YES = 1;

    /**
     * 冻结
     */
    public static final Integer STATUS_FREEZE = 0;
    /**
     * 可用
     */
    public static final Integer STATUS_USABLE = 1;

    /**
     * 置顶：否
     */
    public static final Integer IS_NOT_TOP = 0;
    /**
     * 置顶：是
     */
    public static final Integer IS_TOP = 1;
    /**
     * 默认格式
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    /**
     * 时间格式——精确到秒
     */
    public static final String DEFAULT_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 汇率map，将100R换算存储为1R换算
     */
    public static final Map<String, BigDecimal> EXCHANGE_RATE_MAP = MapUtil.newHashMap();

    /**
     * 基本常量
     */
    public static class Number {
        /**
         * 数字0
         */
        public static final Integer COMMON_NUMBER_ZERO = 0;
        /**
         * 数字1
         */
        public static final Integer COMMON_NUMBER_FIRST = 1;
        /**
         * 数字2
         */
        public static final Integer COMMON_NUMBER_SECOND = 2;
        /**
         * 数字3
         */
        public static final Integer COMMON_NUMBER_THIRD = 3;
    }
}
