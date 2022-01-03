//请求url地址
var requestUrl = '';

// 去除最后一位字符串
function removeLastOne(str) {
    if (str === '' || str === undefined) {
        return '';
    }
    return str.substring(0, str.length - 1);
}


layui.use(['form', 'table', 'toast'], function () {
    const form = layui.form,
        toast = layui.toast,
        table = layui.table;

    //表单搜索
    form.on('submit(data-search-btn)', function (data) {
        table.reload('currentTableId', {
            where: data.field
        });
        return false;
    });

    //表单重置
    form.on('submit(data-reset-btn)', function () {
        table.reload('currentTableId', {
            where: ""
        });
        form.reset();
        return false;
    });

    //添加数据
    window.addData = function (result) {
        if (result.success) {
            toast.success({message: '添加成功', position: 'topCenter'});
            table.reload('currentTableId');
        } else {
            toast.error({message: result.msg, position: 'topCenter'});
        }
    }

    //编辑数据
    window.editData = function (result) {
        if (result.success) {
            toast.success({message: '编辑成功', position: 'topCenter'});
            table.reload('currentTableId');
        } else {
            toast.error({message: result.msg, position: 'topCenter'});
        }
    }

    //删除数据
    window.delData = function (result) {
        if (result.success) {
            toast.success({message: '删除成功', position: 'topCenter'});
            table.reload('currentTableId');
        } else {
            toast.error({message: result.msg, position: 'topCenter'});
        }
    }

    window.failInfo = function () {
        toast.error({message: "操作失败", position: 'topCenter'});
    }

    /**
     * 打开提交窗口
     * @param title 窗口标题
     * @param data 传入iframe数据
     * @param contentUrl iframe地址
     * @param height 窗口高度
     */
    window.loadFormHeightModel = function (title, data, contentUrl, height) {
        let iframe;
        layer.open({
            type: 2,
            title: title,
            area: ['600px', height],
            shade: 0.3,
            maxmin: true,
            offset: 'auto',
            content: contentUrl,
            success: function (layero, index) {
                iframe = window["layui-layer-iframe" + index];
                iframe.initData(data);
            },
            zIndex: layer.zIndex
        });
    }
});

var Ajax = new function() {

    this.delete = function(url,data) {
        $.ajax({
            url: url + data.id,
            dataType: 'json',
            contentType: 'application/json',
            type: 'delete',
            success: function (result) {
                window.delData(result);
            }
        })
    }
}
