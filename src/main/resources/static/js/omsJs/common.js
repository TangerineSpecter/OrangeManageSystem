//请求url地址
var url = '';

// 提示框
function promptModal(content) {
	$("#prompt-modal-content").html(content);
	$("#prompt-modal").modal();
}

// 去除最后一位字符串
function removeLastOne(str) {
	if(str == '' || str == undefined) {
		return '';
	}
	return str.substring(0, str.length - 1);
}
