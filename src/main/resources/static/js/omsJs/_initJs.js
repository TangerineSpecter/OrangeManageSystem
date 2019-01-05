$(function() {
	initSystemInfo();
})

// 系统信息初始化
function initSystemInfo() {
	$.post("/systemInfo", {}, function(result) {
		if (result.success) {
			var infoSuffix = "% / 100%";
			$("#cpuInfo").html(result.data.cpuRatio + infoSuffix);
			$("#memoryInfo").html(result.data.memoryRatio + infoSuffix);
			$("#diskInfo").html(result.data.diskRatio + infoSuffix);

			$("#cpuRatioValue").css("width", result.data.cpuRatio + "%");
			$("#memoryRatioValue").css("width", result.data.memoryRatio + "%");
			$("#diskRatioValue").css("width", result.data.diskRatio + "%");
		}
	})
}

/**
 * 加载内容
 */
function loadUrlContent(url) {
	$("#contentUrl").attr("src", url);
}