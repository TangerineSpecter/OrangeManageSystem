package com.tangerinespecter.oms.common.redis;

import com.tangerinespecter.oms.common.constants.SystemConstant;

import java.io.Serializable;

/**
 * 页面模板缓存Key
 */
public class PageModelKey extends BasePrefixKey implements Serializable {

    /**
     * 过期时间（单位：秒）
     */
    private static final Integer EXPIRE_TIME = SystemConstant.SYSTEM_CONFIG.getCacheTime();

    private PageModelKey(String prefix) {
        super(prefix);
    }

    public PageModelKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    /**
     * 星座页面缓存
     */
    public static final PageModelKey CONSTELLATION_PAGE_KEY = new PageModelKey("constellation_page:", EXPIRE_TIME);
    /**
     * 交易数据页面缓存
     */
    public static final PageModelKey TRADE_RECORD_PAGE_KEY = new PageModelKey("trade_record_page:", EXPIRE_TIME);
    /**
     * 交易逻辑页面缓存
     */
    public static final PageModelKey TRADE_LOGIC_PAGE_KEY = new PageModelKey("trade_logic_page:", EXPIRE_TIME);
    /**
     * 交易统计页面缓存
     */
    public static final PageModelKey TRADE_STATIS_PAGE_KEY = new PageModelKey("trade_statis_page:", EXPIRE_TIME);
    /**
     * 健康统计页面缓存
     */
    public static final PageModelKey HEALTH_STATIS_PAGE_KEY = new PageModelKey("health_statis_page:", EXPIRE_TIME);
    /**
     * 股票组合页面缓存
     */
    public static final PageModelKey STOCK_PORTFOLIO_PAGE_KEY = new PageModelKey("stock_portfolio_page:", EXPIRE_TIME);
    /**
     * 股票池页面缓存
     */
    public static final PageModelKey STOCK_PAGE_KEY = new PageModelKey("stock_page:", EXPIRE_TIME);
    /**
     * 股票池页面缓存
     */
    public static final PageModelKey QUESTION_PAGE_KEY = new PageModelKey("question_page:", EXPIRE_TIME);
    /**
     * 系统首页缓存
     */
    public static final PageModelKey SYSTEM_HOME_PAGE_KEY = new PageModelKey("system_home_page:", EXPIRE_TIME);

    /**
     * 系统默认页缓存
     */
    public static final PageModelKey SYSTEM_INDEX_PAGE_KEY = new PageModelKey("system_index_page:", EXPIRE_TIME);

    /**
     * 系统登录页缓存
     */
    public static final PageModelKey LOGIN_PAGE_KEY = new PageModelKey("login_page:", EXPIRE_TIME);
    /**
     * 系统菜单缓存
     */
    public static final PageModelKey SYSTEM_MENU_PAGE_KEY = new PageModelKey("system_menu_page:", EXPIRE_TIME);
    /**
     * 系统公告缓存
     */
    public static final PageModelKey SYSTEM_BULLETIN_PAGE_KEY = new PageModelKey("system_bulletin_page:", EXPIRE_TIME);
    /**
     * 角色管理菜单缓存
     */
    public static final PageModelKey SYSTEM_ROLE_PAGE_KEY = new PageModelKey("system_role_page:", EXPIRE_TIME);
    /**
     * 权限管理菜单缓存
     */
    public static final PageModelKey SYSTEM_PERMISSION_PAGE_KEY = new PageModelKey("system_permission_page:", EXPIRE_TIME);
    /**
     * 健康管理菜单缓存
     */
    public static final PageModelKey USER_HEALTH_PAGE_KEY = new PageModelKey("user_health_page:", EXPIRE_TIME);
    /**
     * 卡片笔记菜单缓存
     */
    public static final PageModelKey CARD_NOTE_PAGE_KEY = new PageModelKey("user_card_note_page:", EXPIRE_TIME);
    /**
     * 日记管理菜单缓存
     */
    public static final PageModelKey USER_DIARY_PAGE_KEY = new PageModelKey("user_diary_page:", EXPIRE_TIME);
    /**
     * 收藏管理菜单缓存
     */
    public static final PageModelKey WORK_COLLECTION_PAGE_KEY = new PageModelKey("work_collection_page:", EXPIRE_TIME);
    /**
     * 部门菜单缓存
     */
    public static final PageModelKey SYSTEM_DEPT_PAGE_KEY = new PageModelKey("system_dept_page:", EXPIRE_TIME);
    /**
     * 基金页面缓存
     */
    public static final PageModelKey FUND_PAGE_KEY = new PageModelKey("data_fund_page:", EXPIRE_TIME);

    /**
     * 食物库页面缓存
     */
    public static final PageModelKey FOOD_LIBRARY_PAGE_KEY = new PageModelKey("data_food_library:", EXPIRE_TIME);

    /**
     * 基金分析页面缓存
     */
    public static final PageModelKey FUND_ANALYSIS_PAGE_KEY = new PageModelKey("fund_analysis_page:", EXPIRE_TIME);

    /**
     * 令牌管理菜单缓存
     */
    public static final PageModelKey SYSTEM_TOKEN_PAGE_KEY = new PageModelKey("system_token_page:", EXPIRE_TIME);

    /**
     * 定时任务菜单缓存
     */
    public static final PageModelKey SYSTEM_SCHEDULED_PAGE_KEY = new PageModelKey("system_scheduled_page:", EXPIRE_TIME);

}
