//请求url地址
var url = '';

// 去除最后一位字符串
function removeLastOne(str) {
    if (str == '' || str == undefined) {
        return '';
    }
    return str.substring(0, str.length - 1);
}


layui.use(['form', 'table', 'popup', 'toast'], function () {
    const form = layui.form,
        popup = layui.popup,
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
            popup.failure(result.msg);
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
});
