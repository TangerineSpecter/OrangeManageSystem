//请求url地址
var requestUrl = '';

// 去除最后一位字符串
function removeLastOne(str) {
    if (str === '' || str === undefined) {
        return '';
    }
    return str.substring(0, str.length - 1);
}


layui.use(['form', 'table', 'toast', 'treetable', 'layer'], function () {
    const form = layui.form,
        toast = layui.toast,
        table = layui.table,
        treetable = layui.treetable,
        layer = layui.layer;

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
            parent.layui.treetable.reload("#menu-table");
        } else {
            if (result.success) {
                toast.success({message: '添加成功', position: 'topCenter'});
                layer.close(layer.index);
            } else {
                toast.error({message: result.msg, position: 'topCenter'});
            }
            table.reload('currentTableId');
            treetable.reload("#menu-table");
        }
    };

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
            parent.layui.treetable.reload("#menu-table");
        } else {
            if (result.success) {
                toast.success({message: '编辑成功', position: 'topCenter'});
                layer.close(layer.index);
            } else {
                toast.error({message: result.msg, position: 'topCenter'});
            }
            table.reload('currentTableId');
            treetable.reload("#menu-table");
        }
    };

    //删除数据
    window.delData = function (result, iframe) {
        if (iframe) {
            if (result.success) {
                parent.layui.toast.success({message: '删除成功', position: 'topCenter'});
                parent.layui.table.reload('currentTableId');
                parent.layui.treetable.reload("#menu-table");
                parent.layer.close(parent.layer.getFrameIndex(window.name));
            } else {
                parent.layui.toast.error({message: result.msg, position: 'topCenter'});
            }
        } else {
            if (result.success) {
                toast.success({message: '删除成功', position: 'topCenter'});
                table.reload('currentTableId');
                treetable.reload("#menu-table");
                layer.close(layer.index);
            } else {
                toast.error({message: result.msg, position: 'topCenter'});
            }
        }
    };

    window.failInfo = function (iframe) {
        if (iframe) {
            parent.layui.toast.error({message: "操作失败", position: 'topCenter'});
        } else {
            toast.error({message: "操作失败", position: 'topCenter'});
        }
    };

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

    /**
     * 系统公告弹窗
     * @param title   公告标题
     * @param content 公告内容
     */
    window.notice = function (title, content) {
        layer.open({
            type: 1,
            title: title, //不显示标题栏
            closeBtn: false,
            area: '300px;',
            shade: 0.8,
            id: 'LAY_layuipro', //设定一个id，防止重复弹出
            btn: ['关闭'],
            btnAlign: 'c',
            moveType: 1, //拖拽模式，0或者1
            content: content,
            success: function (layero) {
            }
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
