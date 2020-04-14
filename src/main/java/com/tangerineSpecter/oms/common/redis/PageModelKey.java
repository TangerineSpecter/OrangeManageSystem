package com.tangerinespecter.oms.common.redis;

/**
 * 页面模板缓存Key
 */
public class PageModelKey extends BasePrefixKey {

    private PageModelKey(String prefix) {
        super(prefix);
    }

    public PageModelKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    /**
     * 星座页面缓存
     */
    public static PageModelKey getConstellationPageKey = new PageModelKey("constellation_page:", 5 * 60);
    /**
     * 交易数据页面缓存
     */
    public static PageModelKey getTradeRecordPageKey = new PageModelKey("trade_record_page:", 5 * 60);

    /**
     * 系统首页缓存
     */
    public static PageModelKey getSystemHomePageKey = new PageModelKey("system_home_page:", 5 * 60);

    /**
     * 系统默认页缓存
     */
    public static PageModelKey getSystemIndexPageKey = new PageModelKey("system_index_page:", 5 * 60);

    /**
     * 系统登录页缓存
     */
    public static PageModelKey getLoginPageKey = new PageModelKey("login_page:", 2 * 60 * 60);
    /**
     * 系统菜单缓存
     */
    public static PageModelKey getSystemMenuPageKey = new PageModelKey("system_menu_page:", 5 * 60);
    /**
     * 角色管理菜单缓存
     */
    public static PageModelKey getSystemRolePageKey = new PageModelKey("system_role_page:", 5 * 60);
    /**
     * 收藏管理菜单缓存
     */
    public static PageModelKey getWorkCollectionPageKey = new PageModelKey("work_collection_page:", 5 * 60);
    /**
     * 部门菜单缓存
     */
    public static PageModelKey getSystemDeptPageKey = new PageModelKey("system_dept_page:", 5 * 60);
}
