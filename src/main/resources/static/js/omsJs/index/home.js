$(function() {
	initSystemInfo();
	initStarECharts();
})

/**
 * 系统信息初始化
 */
function initSystemInfo() {
	$("#today-time").html(getToday());
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
	var myChart = echarts.init(document.getElementById('star-echarts'),
			"macarons");
	var option = {
		tooltip : {
			trigger : 'axis'
		},
		radar : [ {
			indicator : [ {
				text : '综合',
				max : 100
			}, {
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
			radius : 50,
			splitLine : {
				show : true,
			}
		} ],
		series : [ {
			type : 'radar',
			tooltip : {
				trigger : 'item'
			},
			itemStyle : {
				normal : {
					lineStyle : {
						color : "#00b2ff" // 图表中各个图区域的边框线颜色
					},
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : [ {
				value : [ 20, 70, 20, 20, 40 ],
				name : '星运图'
			} ]
		} ]
	};
	myChart.setOption(option);
}
