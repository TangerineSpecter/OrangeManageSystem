var href;

//触发按钮点击
function submitFun() {
    console.log("点击了提交");
    $("#submitBtn").click();
}

/**
 * 打开提交窗口
 * @param title 窗口标题
 * @param data 传入iframe数据
 * @param contentUrl iframe地址
 */
function loadFormModel(title, data, contentUrl) {
    let iframe;
    layer.open({
        type: 2
        , title: title
        , area: ['600px', '550px']
        , shade: 0
        , maxmin: true
        , offset: 'auto'
        , content: contentUrl
        , btn: ['提交', '关闭']
        , success: function (layero, index) {
            iframe = window["layui-layer-iframe" + index];
            iframe.initData(data);
        }
        , yes: function (layero, index) {
            iframe.submitFun();
        }
        , btn2: function () {
            layer.close();
        }
        , zIndex: layer.zIndex
    });
};

/**
 * 刷新当前页tab
 */
function reloadTab() {
    $(".layui-tab-item.layui-show", top.window.document).find("iframe")[0].contentWindow.location.reload();
}
