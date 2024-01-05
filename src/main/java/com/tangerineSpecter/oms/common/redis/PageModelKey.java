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
    public static final PageModelKey getConstellationPageKey = new PageModelKey("constellation_page:", EXPIRE_TIME);
    /**
     * 交易数据页面缓存
     */
    public static final PageModelKey getTradeRecordPageKey = new PageModelKey("trade_record_page:", EXPIRE_TIME);
    /**
     * 交易逻辑页面缓存
     */
    public static final PageModelKey getTradeLogicPageKey = new PageModelKey("trade_logic_page:", EXPIRE_TIME);
    /**
     * 交易统计页面缓存
     */
    public static final PageModelKey getTradeStatisPageKey = new PageModelKey("trade_statis_page:", EXPIRE_TIME);
    /**
     * 健康统计页面缓存
     */
    public static final PageModelKey getHealthStatisPageKey = new PageModelKey("health_statis_page:", EXPIRE_TIME);
    /**
     * 股票组合页面缓存
     */
    public static final PageModelKey getStockPortfolioPageKey = new PageModelKey("stock_portfolio_page:", EXPIRE_TIME);
    /**
     * 股票池页面缓存
     */
    public static final PageModelKey getStockPageKey = new PageModelKey("stock_page:", EXPIRE_TIME);
    /**
     * 股票池页面缓存
     */
    public static final PageModelKey getQuestionPageKey = new PageModelKey("question_page:", EXPIRE_TIME);
    /**
     * 系统首页缓存
     */
    public static final PageModelKey getSystemHomePageKey = new PageModelKey("system_home_page:", EXPIRE_TIME);

    /**
     * 系统默认页缓存
     */
    public static final PageModelKey getSystemIndexPageKey = new PageModelKey("system_index_page:", EXPIRE_TIME);

    /**
     * 系统登录页缓存
     */
    public static final PageModelKey getLoginPageKey = new PageModelKey("login_page:", EXPIRE_TIME);
    /**
     * 系统菜单缓存
     */
    public static final PageModelKey getSystemMenuPageKey = new PageModelKey("system_menu_page:", EXPIRE_TIME);
    /**
     * 系统公告缓存
     */
    public static final PageModelKey getSystemBulletinPageKey = new PageModelKey("system_bulletin_page:", EXPIRE_TIME);
    /**
     * 角色管理菜单缓存
     */
    public static final PageModelKey getSystemRolePageKey = new PageModelKey("system_role_page:", EXPIRE_TIME);
    /**
     * 权限管理菜单缓存
     */
    public static final PageModelKey getSystemPermissionPageKey = new PageModelKey("system_permission_page:", EXPIRE_TIME);
    /**
     * 健康管理菜单缓存
     */
    public static final PageModelKey getUserHealthPageKey = new PageModelKey("user_health_page:", EXPIRE_TIME);
    /**
     * 卡片笔记菜单缓存
     */
    public static final PageModelKey getCardNotePageKey = new PageModelKey("user_card_note_page:", EXPIRE_TIME);
    /**
     * 日记管理菜单缓存
     */
    public static final PageModelKey getUserDiaryPageKey = new PageModelKey("user_diary_page:", EXPIRE_TIME);
    /**
     * 收藏管理菜单缓存
     */
    public static final PageModelKey getWorkCollectionPageKey = new PageModelKey("work_collection_page:", EXPIRE_TIME);
    /**
     * 部门菜单缓存
     */
    public static final PageModelKey getSystemDeptPageKey = new PageModelKey("system_dept_page:", EXPIRE_TIME);
    /**
     * 基金页面缓存
     */
    public static final PageModelKey getFundPageKey = new PageModelKey("data_fund_page:", EXPIRE_TIME);

    /**
     * 食物库页面缓存
     */
    public static final PageModelKey getFoodLibraryPageKey = new PageModelKey("data_food_library:", EXPIRE_TIME);

    /**
     * 基金分析页面缓存
     */
    public static final PageModelKey getFundAnalysisPageKey = new PageModelKey("fund_analysis_page:", EXPIRE_TIME);

    /**
     * 令牌管理菜单缓存
     */
    public static final PageModelKey getSystemTokenPageKey = new PageModelKey("system_token_page:", EXPIRE_TIME);

    /**
     * 定时任务菜单缓存
     */
    public static final PageModelKey getSystemScheduledPageKey = new PageModelKey("system_scheduled_page:", EXPIRE_TIME);

}
