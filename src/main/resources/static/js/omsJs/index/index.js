$(function() {
	initSystemInfo();
	initMenuBtn();
	initStarECharts();
})

/**
 * 系统信息初始化
 */
function initSystemInfo() {
	$("#today-time").html(getToday());
}

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

/**
 * 获取今天日期
 */
function getToday() {
	var myDate = new Date();
	return myDate.getFullYear() + "-" + (myDate.getMonth() + 1) + "-"
			+ myDate.getDate();
}

function initStarECharts() {
	var myChart = echarts.init(document.getElementById('star-echarts'));
	var option = {
		tooltip : {
			trigger : 'axis'
		},
		radar : [ {
			indicator : [ {
				text : '健康',
				max : 100
			}, {
				text : '爱情',
				max : 100
			}, {
				text : '财运',
				max : 100
			}, {
				text : '工作',
				max : 100
			} ],
			center : [ '50%', '50%' ],
			radius : 50
		} ],
		series : [ {
			type : 'radar',
			tooltip : {
				trigger : 'item'
			},
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : [ {
				value : [ 60, 73, 85, 40 ],
				name : '星运图'
			} ]
		} ]
	};
	myChart.setOption(option);
}
