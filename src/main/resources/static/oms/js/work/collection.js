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
	$("#add-modal").on("show.bs.modal", function(e) {
		$("#modal-data")[0].reset();
	});
	url = "/collection/add"
}

// 删除
function del(id) {
	url = "/collection/delete"
	$.ajax({
		type : "post",
		url : url,
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				promptModal("删除成功");
			} else {
				promptModal(data.errorDesc);
			}
		}
	})
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