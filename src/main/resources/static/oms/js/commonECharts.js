/**
 * echarts公共图表模板
 */

/**
 * 初始化简单图表
 * @param echartsId 图表ID
 * @param title 图表标题
 * @param lineStyle 折线图样式
 * @param xData x轴数据
 * @param yData y轴数据
 */
window.initSimpleLineEcharts = function (echartsId, title, lineStyle, xData, yData) {
    const line = echarts.init(document.getElementById(echartsId));

    const colorList = ["#9E87FF", '#73DDFF', '#fe9a8b', '#F56948', '#9E87FF']
    const option = {
        backgroundColor: '#fff',
        title: {
            text: title,
            textStyle: {
                fontSize: 12,
                fontWeight: 400
            },
            left: 'center',
            top: '5%'
        },
        legend: {
            icon: 'circle',
            top: '5%',
            right: '5%',
            itemWidth: 6,
            itemGap: 20,
            textStyle: {
                color: '#556677'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                label: {
                    show: true,
                    backgroundColor: '#fff',
                    color: '#556677',
                    borderColor: 'rgba(0,0,0,0)',
                    shadowColor: 'rgba(0,0,0,0)',
                    shadowOffsetY: 0
                },
                lineStyle: {
                    width: 0
                }
            },
            backgroundColor: '#fff',
            textStyle: {
                color: '#5c6c7c'
            },
            padding: [10, 10],
            extraCssText: 'box-shadow: 1px 0 2px 0 rgba(163,163,163,0.5)'
        },
        grid: {
            top: '15%'
        },
        xAxis: [{
            type: 'category',
            data: xData,
            axisLine: {
                lineStyle: {
                    color: '#DCE2E8'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                interval: 1, //自定间隔x轴坐标数据
                textStyle: {
                    color: '#556677'
                },
                // 默认x轴字体大小
                fontSize: 12,
                // margin:文字到x轴的距离
                margin: 15
            },
            axisPointer: {
                label: {
                    // padding: [11, 5, 7],
                    padding: [0, 0, 10, 0],

                    // 这里的margin和axisLabel的margin要一致!
                    margin: 15,
                    // 移入时的字体大小
                    fontSize: 12,
                    backgroundColor: {
                        type: 'linear',
                        x: 0,
                        y: 0,
                        x2: 0,
                        y2: 1,
                        colorStops: [{
                            offset: 0,
                            color: '#fff' // 0% 处的颜色
                        }, {
                            // offset: 0.9,
                            offset: 0.86,

                            color: '#fff' // 0% 处的颜色
                        }, {
                            offset: 0.86,
                            color: '#33c0cd' // 0% 处的颜色
                        }, {
                            offset: 1,
                            color: '#33c0cd' // 100% 处的颜色
                        }],
                        global: false // 缺省为 false
                    }
                }
            },
            boundaryGap: false,
            inverse: true //反转坐标轴
        }],
        yAxis: [{
            type: 'value', //y轴刻度自适应
            scale: true, //y轴刻度自适应最大最小值，可以不包含0值
            axisTick: {
                show: false
            },
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#DCE2E8'
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#556677'
                }
            },
            splitLine: {
                show: false
            }
        }, {
            type: 'value',
            position: 'right',
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#556677'
                },
                formatter: '{value}'
            },
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#DCE2E8'
                }
            },
            splitLine: {
                show: false
            }
        }],
        series: [{
            type: 'line',
            data: yData,
            symbolSize: 1,
            symbol: 'circle',
            smooth: true,
            yAxisIndex: 0,
            showSymbol: false,
            lineStyle: lineStyle,
            itemStyle: {
                normal: {
                    color: colorList[0],
                    borderColor: colorList[0]
                }
            }
        }
        ]
    };

    line.setOption(option);

    window.onresize = function () {
        line.resize();
    }
}

/**
 * 初始化网格图表
 * @param echartsId 图表ID
 * @param lineStyle 折线图样式
 * @param xData x轴数据
 * @param yData y轴数据
 * @param dataName 数据描述
 */
window.initGridLineEcharts = function (echartsId, lineStyle, xData, yData, dataName) {
    const echartsRecords = echarts.init(document.getElementById(echartsId), 'walden');
    const option = {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [{
            type: 'category',
            data: xData,
            //坐标轴轴线
            axisLine: {
                lineStyle: {
                    color: "#999"
                }
            },
            //分割线
            splitLine: {
                show: false
            },
            inverse: true //反转坐标轴
        }],
        yAxis: [{
            type: 'value',
            //分割数量
            splitNumber: 5,
            splitLine: {
                lineStyle: {
                    type: 'dashed',
                    color: '#DDD'
                }
            },
            axisLine: {
                show: false,
                lineStyle: {
                    color: "#333"
                },
            },
            nameTextStyle: {
                color: "#999"
            },
            splitArea: {
                show: false
            },
            //是否脱离0轴
            scale: true
        }],
        series: [{
            name: dataName,
            type: 'line',
            data: yData,
            lineStyle: lineStyle,
            //实心圆点
            symbol: 'circle',
            //标记大小
            symbolSize: 12,
            //阶梯图
            // step: true,
            itemStyle: {
                color: '#48D8BF',
                borderWidth: 4,
                // shadowColor: 'rgba(72,216,191, 0.3)',
                // shadowBlur: 100,
                borderColor: "#FFF"
            },
            //开启平滑
            smooth: true,
            //平滑单调性，仅针对x处理，默认是xy
            smoothMonotone: 'x'
        }]
    };
    echartsRecords.setOption(option);

    window.onresize = function () {
        echartsRecords.resize();
    };
}

/**
 * 描边柱状图
 * @param echartsId 图表ID
 * @param lineStyle 折线图样式
 * @param xData x轴数据
 * @param yData y轴数据
 */
window.initStrokeColumnCharts = function (echartsId, lineStyle, xData, yData) {
    const column4 = echarts.init(document.getElementById(echartsId));
    const option = {
        backgroundColor: '#fff',
        title: {
            top: 10,
            left: 15,
            textStyle: {
                color: "#35598d",
                fontSize: 16,
                fontWeight: 'normal'
            }
        },
        tooltip: {
            trigger: 'axis',
            formatter: '{b}：{c}',
        },
        grid: {
            left: '5%',
            right: '6%',
            bottom: '3%',
            top: '20%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: xData,
            axisLabel: {          //坐标轴字体颜色
                textStyle: {
                    color: '#9eaaba'
                }
            },
            axisLine: {
                lineStyle: {
                    color: "#e5e5e5"
                }
            },
            axisTick: {       //y轴刻度线
                show: false
            },
            splitLine: {    //网格
                show: false,

            },
            inverse: true //反转坐标轴
        },
        yAxis: {
            type: 'value',
            axisLabel: {        //坐标轴字体颜色
                textStyle: {
                    color: '#9eaaba'
                }
            },
            axisLine: {
                show: false,
            },
            axisTick: {       //y轴刻度线
                show: false
            },
            splitLine: {    //网格
                show: true,
                lineStyle: {
                    color: '#dadde4',
                    type: "dashed" //坐标网线类型
                }
            }
        },
        series: {
            name: '收益',
            type: 'bar',
            barWidth: '50%',  //柱子宽度
            barGap: 0,
            itemStyle: {  //柱子颜色
                normal: {
                    // borderWidth: 2,
                    // borderColor: 'rgb(79, 116, 223)',
                    // 柱形图圆角，初始化效果
                    barBorderRadius: 15,
                    color: function (param) {
                        if (param.value > 0) {
                            // return '#eb4e5c';
                            return 'rgb(253, 57, 122)';
                        } else {
                            // return '#00af92';
                            return 'rgb(10, 187, 135)';
                        }
                    },
                }
            },
            data: yData
        }
    };

    column4.setOption(option);

    window.onresize = function () {
        column4.resize();
    }
}

/**
 * 简单折线样式
 * @param startColor 起始颜色
 * @param endColor 结束颜色
 * @returns {{shadowOffsetY: number, color, shadowBlur: number, width: number, shadowColor: string}}
 */
window.simpleLineStyle = function (startColor, endColor) {
    return {
        width: 5,
        color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
            offset: 0,
            color: startColor
        },
            {
                offset: 1,
                color: endColor
            }
        ]),
        shadowColor: 'rgba(158,135,255, 0.3)',
        shadowBlur: 10,
        shadowOffsetY: 20
    }
}

/**
 * 带打点折线样式
 * @param startColor 起始颜色
 * @param endColor 结束颜色
 * @returns {{shadowOffsetY: number, color: {globalCoord: boolean, colorStops: [{offset: number, color}, {offset: number, color}], type: string}, shadowBlur: number, width: number, shadowColor: string}}
 */
window.pointLineStyle = function (startColor, endColor) {
    return {
        width: 5,
        color: {
            type: 'linear',
            colorStops: [{
                offset: 0,
                color: startColor // 0% 处的颜色
            }, {
                offset: 1,
                color: endColor // 100% 处的颜色
            }],
            globalCoord: false // 缺省为 false
        },
        shadowColor: 'rgba(72,216,191, 0.7)',
        shadowBlur: 10,
        shadowOffsetY: 20
    }
}