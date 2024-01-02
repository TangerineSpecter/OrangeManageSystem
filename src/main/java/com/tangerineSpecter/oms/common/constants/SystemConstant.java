package com.tangerinespecter.oms.common.constants;

import com.tangerinespecter.oms.system.domain.entity.SystemConfig;

/**
 * 系统相关常量类
 *
 * @author tangerineSpecter
 * @version 0.0.6
 */
public class SystemConstant {

    /**
     * 系统默认密码最小长度
     */
    public static final int PASSWORD_DEFAULT_MIN_LENGTH = 6;
    /**
     * 系统信息
     */
    public static final SystemConfig SYSTEM_CONFIG = new SystemConfig();
    /**
     * 不设置缓存
     */
    public static final Integer NO_CACHE = 0;
    /**
     * 系统菜单置顶数量阈值
     */
    public static final Integer SYSTEM_MENU_TOP_COUNT_THRESHOLD = 8;

    /**
     * 超级管理员：是
     */
    public static final Integer IS_SYSTEM_ADMIN = 1;

}
