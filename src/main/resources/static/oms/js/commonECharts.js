/**
 * echarts公共图表模板
 */

/**
 * 初始化简单图表
 * @param echartsId 图表ID
 * @param title 图表标题
 * @param color 折线图样式
 * @param xData x轴数据
 * @param yData y轴数据
 */
window.initSimpleLineEcharts = function (echartsId, title, color, xData, yData) {
    const line = echarts.init(document.getElementById(echartsId));

    // const colorList = ["#9E87FF", '#73DDFF', '#fe9a8b', '#F56948', '#9E87FF']
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
            smooth: true,
            yAxisIndex: 0,
            showSymbol: true,
            lineStyle: simpleLineStyle(color),
            //实心圆点
            symbol: 'circle',
            //标记大小
            symbolSize: 12,
            //阶梯图
            // step: true,
            itemStyle: {
                color: color,
                borderWidth: 4,
                // shadowColor: 'rgba(72,216,191, 0.3)',
                // shadowBlur: 100,
                borderColor: "#FFF"
            },
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
 * @param pointColor 转折点颜色
 * @param xData x轴数据
 * @param yData y轴数据
 * @param dataName 数据描述
 */
window.initGridLineEcharts = function (echartsId, lineStyle, pointColor, xData, yData, dataName) {
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
                show: true
            },
            inverse: true //反转坐标轴
        }],
        yAxis: [{
            type: 'value',
            //分割数量
            splitNumber: 5,
            splitLine: {
                show: false,
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
                color: pointColor,
                borderWidth: 4,
                // shadowColor: 'rgba(72,216,191, 0.3)',
                // shadowBlur: 100,
                borderColor: "#FFF"
            },
            //开启平滑
            // smooth: true,
            //平滑单调性，仅针对x处理，默认是xy
            // smoothMonotone: 'x'
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
            barWidth: '80%',  //柱子宽度
            barGap: 0,
            itemStyle: {  //柱子颜色
                normal: {
                    // borderWidth: 2,
                    // borderColor: 'rgb(79, 116, 223)',
                    // 柱形图圆角，初始化效果
                    // barBorderRadius: 15,
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
 * 描边圆角柱状图
 * @param echartsId 图表ID
 * @param xData x轴数据
 * @param yData y轴数据
 */
window.initBorderColumnCharts = function (echartsId, xData, yData) {
    const column = echarts.init(document.getElementById(echartsId));
    const emphasisStyle = {
        itemStyle: {
            shadowBlur: 100,
            shadowColor: 'rgba(0,0,0,0.3)'
        }
    };
    let upData = [];
    let downData = [];
    //拆分数据为正和负
    $.each(yData, function (index, item) {
        if (item > 0) {
            upData.push(item);
            downData.push(0);
        } else if (item < 0) {
            upData.push(0);
            downData.push(item);
        } else {
            upData.push(0);
            downData.push(0);
        }
    })
    const option = {
        legend: {
            data: ['bar', 'bar2'],
            left: '10%'
        },
        // brush: {
        //     toolbox: ['rect', 'polygon', 'lineX', 'lineY', 'keep', 'clear'],
        //     xAxisIndex: 0
        // },
        // toolbox: {
        //     feature: {
        //         magicType: {
        //             type: ['stack']
        //         },
        //         dataView: {}
        //     }
        // },
        tooltip: {},
        xAxis: {
            data: xData,
            // name: '时间',
            axisLine: {onZero: true},
            splitLine: {show: false},
            splitArea: {show: true},
            //反转
            inverse: true
        },
        yAxis: {
            // interval: [0, 500, 2000, 5000, 10000, 20000, 50000, 200000]
            splitNumber: 10,
        },
        grid: {
            bottom: 100
        },
        series: [
            {
                name: '收益',
                type: 'bar',
                stack: 'one',
                emphasis: emphasisStyle,
                data: upData,
                color: 'rgb(232,110,106)',
                itemStyle: {
                    borderRadius: [5, 5, 0, 0]
                },
            },
            {
                name: '亏损',
                type: 'bar',
                stack: 'one',
                emphasis: emphasisStyle,
                data: downData,
                color: 'rgb(129,196,99)',
                itemStyle: {
                    borderRadius: [0, 0, 5, 5]
                },
            }
        ]
    };
    column.setOption(option);
    window.onresize = function () {
        column.resize();
    }
}

/**
 * 简单渐变折线样式
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
 * 简单折线样式
 * @returns {{shadowOffsetY: number, color, shadowBlur: number, width: number, shadowColor: string}}
 * @param color 颜色
 */
window.simpleLineStyle = function (color) {
    return {
        width: 5,
        color: color,
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

/**
 * 时间轴折线图
 * @param echartsId 图表id
 * @param title 图表标题
 * @param seriesName 标记名称
 * @param data 数据 - 数组[时间，y值]
 */
window.timeLineCharts = function (echartsId, title, seriesName, data) {
    const line = echarts.init(document.getElementById(echartsId));
    const option = {
        tooltip: {
            trigger: 'axis',
            position: function (pt) {
                return [pt[0], '10%'];
            }
        },
        title: {
            left: 'center',
            text: title
        },
        toolbox: {
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'time',
            boundaryGap: false
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%']
        },
        dataZoom: [
            {
                type: 'inside',
                start: 0,
                end: 10
            },
            {
                start: 0,
                end: 10
            }
        ],
        series: [
            {
                name: seriesName,
                type: 'line',
                color: '#73c0de',
                smooth: true,
                symbol: 'none',
                areaStyle: {},
                data: data,
            }
        ]
    };
    line.setOption(option);

    window.onresize = function () {
        line.resize();
    }
}