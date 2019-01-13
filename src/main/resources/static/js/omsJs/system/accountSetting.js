$(function() {
	$("#saveBtn").click(function() {
		$.ajax({
			type : 'POST',
			datatype : 'json',
			url : '/systemUser/update',
			data : $("#systemUser-form").serialize(),
			success : function(result) {
				promptModal("保存成功");
			},
			error : function(result) {
				promptModal(result.errorDesc);
			}
		});
	})
})