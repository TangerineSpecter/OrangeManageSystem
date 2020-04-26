package com.tangerinespecter.oms.common.constants;

import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import org.springframework.beans.factory.annotation.Value;

/**
 * 系统相关常量类
 *
 * @author tangerineSpecter
 * @version 0.0.6
 */
public class SystemConstant {

    /**
     * 系统信息
     */
    public static SystemConfig systemConfig;
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
    /**
     * 是否管理员角色：是
     */
    public static final Integer IS_SYSTEM_ADMIN_ROLE = 1;

    /**
     * 是否有效：冻结
     */
    public static final Integer IS_NOT_EFFECTIVE = 0;
    /**
     * 是否有效：可用
     */
    public static final Integer IS_EFFECTIVE = 1;

}
