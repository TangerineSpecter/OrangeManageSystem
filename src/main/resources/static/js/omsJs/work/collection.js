$(function() {
	// 新增
	$("#createBtn").click(function() {
		create();
	});
	// 提交
	$("#submit").click(function() {
		submit();
	});
})

// 创建
function create() {
	$("#add-modal").modal();
	url = "/collection/add"
}

// 提交
function submit() {
	$.ajax({
		type : "post",
		url : url,
		data : $("#modal-data").serialize(),
		dataType : "json",
		success : function(data) {
			if (data.success) {
				promptModal("新增成功");
				$('#add-modal').modal('hide');
			} else {
				promptModal(data.errorDesc);
			}
		}
	})
}