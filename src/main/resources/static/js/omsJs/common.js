//提示框
function promptModal(content) {
	$("#prompt-modal-content").html(content);
	$("#prompt-modal").modal();
}

// 去除最后一位字符串
function removeLastOne(str) {
	return str.substring(0, str.length - 1);
}
