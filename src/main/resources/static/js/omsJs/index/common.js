$(function() {
	initSystemInfo();
})

// 系统信息初始化
function initSystemInfo() {
	$.post("/systemInfo", {}, function(result) {
		if (result.success) {
			var infoSuffix = "% / 100%";
			// 系统负载信息
			var systemInfo = result.data.systemInfo;

			$("#cpuInfo").html(systemInfo.cpuRatio + infoSuffix);
			$("#memoryInfo").html(systemInfo.memoryRatio + infoSuffix);
			$("#diskInfo").html(systemInfo.diskRatio + infoSuffix);

			$("#cpuRatioValue").css("width", systemInfo.cpuRatio + "%");
			$("#memoryRatioValue").css("width", systemInfo.memoryRatio + "%");
			$("#diskRatioValue").css("width", systemInfo.diskRatio + "%");
		}
	})
}

/**
 * 加载内容
 */
function loadUrlContent(url) {
	$("#contentUrl").attr("src", url);
}