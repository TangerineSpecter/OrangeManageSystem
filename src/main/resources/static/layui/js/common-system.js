var href;

//触发按钮点击
function submitFun() {
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
        , area: ['600px', '600px']
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
}

//通用基本CRUD处理方法
const active = {
    add: function (data, url) {

    },
    delete: function (data, url) {
        $.post(url, data, function (result) {
            if (result.success) {
                layer.open({
                    title: "提示",
                    content: "删除成功",
                    yes: function () {
                        reloadTab();
                    }
                });
            } else {
                layer.open({
                    title: "警告",
                    content: result.msg
                })
            }
        }, "JSON");
    },
};

/**
 * 刷新当前页tab
 */
function reloadTab() {
    $(".layui-tab-item.layui-show", top.window.document).find("iframe")[0].contentWindow.location.reload();
}

//通用搜索按钮监听
layui.use(['form', 'table'], function () {
    var form = layui.form,
        table = layui.table;

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            }
            , where: data.field
        }, 'data');
        return false;
    });

    // 监听清空操作
    form.on('submit(data-clear-btn)', function () {
        $("#search-from")[0].reset();
        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            }, where: {queryParam: null}
        }, 'data');
        return false;
    });
});
