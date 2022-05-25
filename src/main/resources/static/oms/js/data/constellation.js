$(function () {
    initSelected();
});

// 初始化下拉框
function initSelected() {
    $.getJSON("./json/star.json", function (data) {
        $.each(data, function (index, item) {
            var option = "<option value='" + item + "'>" + item + "</option>"
            $("#star").append(option);
        })
    })
}
