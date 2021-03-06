$(function () {
    initMenuBtn();
});

/**
 * 加载内容
 */
function loadUrlContent(url) {
    $("#contentUrl").attr("src", url);
}

/**
 * 首页按钮初始化
 */
function initMenuBtn() {
    // 菜单跳转初始化
    $("#menu a").click(function () {
        var url = $(this).attr("id");
        loadUrlContent(url);
    });
    // 系统菜单跳转初始化
    $("#system-menu a").click(function () {
        var url = $(this).attr("id");
        loadUrlContent(url);
    });
    // 帐号设置
    $("#account-setting").click(function () {
        loadUrlContent("/systemUser/accountSetting?id=1164031608038096897");
    });
}
