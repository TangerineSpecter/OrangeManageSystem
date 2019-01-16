$(function() {
	initMenuBtn();
})

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
	$("#menu a").click(function() {
		var url = $(this).attr("id");
		loadUrlContent(url);
	})
	// 帐号设置
	$("#account-setting").click(function() {
		loadUrlContent("accountSetting?id=1");
	})
}
