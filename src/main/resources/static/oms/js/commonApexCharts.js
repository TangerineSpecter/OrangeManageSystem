/**
 * apexcharts公共图表模板
 */

/**
 * 初始化简单图表
 * @param echartsId 图表ID
 * @param title 图表标题
 * @param dataName 数据点名称
 * @param xData x轴数据
 * @param yData y轴数据
 */
window.initSimpleLineApexcharts = function (echartsId, title, dataName, xData, yData) {
    const options = {
        series: [{
            name: dataName,
            data: yData
        }],
        chart: {
            height: 350,
            type: 'line',
            zoom: {
                enabled: false
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            width: 4,
            curve: 'smooth'
        },
        title: {
            text: title,
            align: 'left'
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            categories: xData,
        },
        markers: {
            //打点
            size: 5,
            hover: {
                size: 10
            }
        },
        colors: ['#5d78ff']
    };

    const chart = new ApexCharts(document.querySelector(echartsId), options);
    chart.render();
}
