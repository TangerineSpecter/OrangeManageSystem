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
    public static PageModelKey getConstellationPageKey = new PageModelKey("constellation_page:", 60);

    /**
     * 系统首页缓存
     */
    public static PageModelKey getSystemHomePageKey = new PageModelKey("system_home_page:", 60);

    /**
     * 系统默认页缓存
     */
    public static PageModelKey getSystemIndexPageKey = new PageModelKey("system_index_page:", 60);

    /**
     * 系统登录页缓存
     */
    public static PageModelKey getLoginPageKey = new PageModelKey("login_page:", 2 * 60 * 60);
    /**
     * 系统菜单缓存
     */
    public static PageModelKey getSystemMenuPageKey = new PageModelKey("system_menu_page:", 60);
}
