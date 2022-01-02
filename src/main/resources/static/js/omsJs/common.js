//请求url地址
var url = '';

// 去除最后一位字符串
function removeLastOne(str) {
    if (str == '' || str == undefined) {
        return '';
    }
    return str.substring(0, str.length - 1);
}

//表单搜索
layui.use(['form', 'table'], function () {
    var form = layui.form,
        table = layui.table;

    form.on('submit(data-search-btn)', function (data) {
        table.reload('currentTableId', {
            where: data.field
        });
        return false;
    });

    form.on('submit(data-reset-btn)', function () {
        table.reload('currentTableId', {
            where: ""
        });
        form.reset();
        return false;
    });
});
