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
    window.addData = function (result, iframe) {
        if (iframe) {
            if (result.success) {
                parent.layui.toast.success({message: '添加成功', position: 'topCenter'});
                parent.layer.close(parent.layer.getFrameIndex(window.name));
            } else {
                parent.layui.toast.error({message: result.msg, position: 'topCenter'});
            }
            parent.layui.table.reload('currentTableId');
        } else {
            if (result.success) {
                toast.success({message: '添加成功', position: 'topCenter'});
                layer.close(layer.index);
            } else {
                toast.error({message: result.msg, position: 'topCenter'});
            }
            table.reload('currentTableId');
        }
    }

    //编辑数据
    window.editData = function (result, iframe) {
        if (iframe) {
            if (result.success) {
                parent.layui.toast.success({message: '编辑成功', position: 'topCenter'});
                parent.layer.close(parent.layer.getFrameIndex(window.name));
            } else {
                parent.layui.toast.error({message: result.msg, position: 'topCenter'});
            }
            parent.layui.table.reload('currentTableId');
        } else {
            if (result.success) {
                toast.success({message: '编辑成功', position: 'topCenter'});
                layer.close(layer.index);
            } else {
                toast.error({message: result.msg, position: 'topCenter'});
            }
            table.reload('currentTableId');
        }
    }

    //删除数据
    window.delData = function (result, iframe) {
        if (iframe) {
            if (result.success) {
                parent.layui.toast.success({message: '删除成功', position: 'topCenter'});
                parent.layui.table.reload('currentTableId');
                parent.layer.close(parent.layer.getFrameIndex(window.name));
            } else {
                parent.layui.toast.error({message: result.msg, position: 'topCenter'});
            }
        } else {
            if (result.success) {
                toast.success({message: '删除成功', position: 'topCenter'});
                table.reload('currentTableId');
                layer.close(layer.index);
            } else {
                toast.error({message: result.msg, position: 'topCenter'});
            }
        }
    }

    window.failInfo = function (iframe) {
        if (iframe) {
            parent.layui.toast.error({message: "操作失败", position: 'topCenter'});
        } else {
            toast.error({message: "操作失败", position: 'topCenter'});
        }
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

var Ajax = new function () {
    /**
     * post请求
     * @param url 请求地址
     * @param data 请求数据
     * @param iframe 是否iframe请求
     */
    this.post = function (url, data, iframe) {
        $.ajax({
            url: url,
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            type: 'post',
            success: function (result) {
                window.addData(result, iframe);
            },
            error: function () {
                window.failInfo(iframe);
            }
        })
    };

    /**
     * put请求
     * @param url 请求地址
     * @param data 请求数据
     * @param iframe 是否iframe请求
     */
    this.put = function (url, data, iframe) {
        $.ajax({
            url: url,
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            type: 'put',
            success: function (result) {
                window.editData(result, iframe);
            },
            error: function () {
                window.failInfo(iframe);
            }
        })
    };

    /**
     * delete请求
     * @param url 请求地址
     * @param data 请求数据
     * @param iframe 是否iframe请求
     */
    this.delete = function (url, data, iframe) {
        $.ajax({
            url: url + "/" + data.id,
            dataType: 'json',
            contentType: 'application/json',
            type: 'delete',
            success: function (result) {
                window.delData(result, iframe);
            },
            error: function () {
                window.failInfo(iframe);
            }
        })
    };
};
