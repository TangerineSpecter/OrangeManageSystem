package com.tangerinespecter.oms.common.redis;

import com.tangerinespecter.oms.common.constants.SystemConstant;

/**
 * 页面模板缓存Key
 */
public class PageModelKey extends BasePrefixKey {

    /**
     * 过期时间（单位：秒）
     */
    private static final Integer EXPIRE_TIME = SystemConstant.systemConfig.getCacheTime();

    private PageModelKey(String prefix) {
        super(prefix);
    }

    public PageModelKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    /**
     * 星座页面缓存
     */
    public static PageModelKey getConstellationPageKey = new PageModelKey("constellation_page:", EXPIRE_TIME);
    /**
     * 交易数据页面缓存
     */
    public static PageModelKey getTradeRecordPageKey = new PageModelKey("trade_record_page:", EXPIRE_TIME);
    /**
     * 交易逻辑页面缓存
     */
    public static PageModelKey getTradeLogicPageKey = new PageModelKey("trade_logic_page:", EXPIRE_TIME);
    /**
     * 交易统计页面缓存
     */
    public static PageModelKey getTradeStatisPageKey = new PageModelKey("trade_statis_page:", EXPIRE_TIME);
    /**
     * 健康统计页面缓存
     */
    public static PageModelKey getHealthStatisPageKey = new PageModelKey("health_statis_page:", EXPIRE_TIME);
    /**
     * 股票组合页面缓存
     */
    public static PageModelKey getStockPortfolioPageKey = new PageModelKey("stock_portfolio_page:", EXPIRE_TIME);
    /**
     * 股票池页面缓存
     */
    public static PageModelKey getStockPageKey = new PageModelKey("stock_page:", EXPIRE_TIME);
    /**
     * 股票池页面缓存
     */
    public static PageModelKey getQuestionPageKey = new PageModelKey("question_page:", EXPIRE_TIME);
    /**
     * 系统首页缓存
     */
    public static PageModelKey getSystemHomePageKey = new PageModelKey("system_home_page:", EXPIRE_TIME);

    /**
     * 系统默认页缓存
     */
    public static PageModelKey getSystemIndexPageKey = new PageModelKey("system_index_page:", EXPIRE_TIME);

    /**
     * 系统登录页缓存
     */
    public static PageModelKey getLoginPageKey = new PageModelKey("login_page:", EXPIRE_TIME);
    /**
     * 系统菜单缓存
     */
    public static PageModelKey getSystemMenuPageKey = new PageModelKey("system_menu_page:", EXPIRE_TIME);
    /**
     * 系统公告缓存
     */
    public static PageModelKey getSystemBulletinPageKey = new PageModelKey("system_bulletin_page:", EXPIRE_TIME);
    /**
     * 角色管理菜单缓存
     */
    public static PageModelKey getSystemRolePageKey = new PageModelKey("system_role_page:", EXPIRE_TIME);
    /**
     * 权限管理菜单缓存
     */
    public static PageModelKey getSystemPermissionPageKey = new PageModelKey("system_permission_page:", EXPIRE_TIME);
    /**
     * 健康管理菜单缓存
     */
    public static PageModelKey getUserHealthPageKey = new PageModelKey("user_health_page:", EXPIRE_TIME);
    /**
     * 卡片笔记菜单缓存
     */
    public static PageModelKey getCardNotePageKey = new PageModelKey("user_card_note_page:", EXPIRE_TIME);
    /**
     * 日记管理菜单缓存
     */
    public static PageModelKey getUserDiaryPageKey = new PageModelKey("user_diary_page:", EXPIRE_TIME);
    /**
     * 收藏管理菜单缓存
     */
    public static PageModelKey getWorkCollectionPageKey = new PageModelKey("work_collection_page:", EXPIRE_TIME);
    /**
     * 部门菜单缓存
     */
    public static PageModelKey getSystemDeptPageKey = new PageModelKey("system_dept_page:", EXPIRE_TIME);
    /**
     * 基金页面缓存
     */
    public static PageModelKey getFundPageKey = new PageModelKey("data_fund_page:", EXPIRE_TIME);
}
