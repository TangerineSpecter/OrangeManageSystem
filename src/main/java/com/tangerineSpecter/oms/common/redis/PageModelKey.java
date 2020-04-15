package com.tangerinespecter.oms.common.redis;

/**
 * 页面模板缓存Key
 */
public class PageModelKey extends BasePrefixKey {

    /**
     * 过期时间（单位：秒）
     */
    private static final Integer EXPIRE_TIME = 3;

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
     * 股票组合页面缓存
     */
    public static PageModelKey getStockPortfolioPageKey = new PageModelKey("stock_portfolio_page:", EXPIRE_TIME);
    /**
     * 股票池页面缓存
     */
    public static PageModelKey getStockPageKey = new PageModelKey("stock_page:", EXPIRE_TIME);

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
     * 角色管理菜单缓存
     */
    public static PageModelKey getSystemRolePageKey = new PageModelKey("system_role_page:", EXPIRE_TIME);
    /**
     * 收藏管理菜单缓存
     */
    public static PageModelKey getWorkCollectionPageKey = new PageModelKey("work_collection_page:", EXPIRE_TIME);
    /**
     * 部门菜单缓存
     */
    public static PageModelKey getSystemDeptPageKey = new PageModelKey("system_dept_page:", EXPIRE_TIME);
}
