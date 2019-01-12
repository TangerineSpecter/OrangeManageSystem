$(function() {
	$("#saveBtn").click(function() {
		$.ajax({
			type : 'POST',
			datatype : 'json',
			url : '/systemUser/update',
			data : $("#systemUser-form").serialize(),
			success : function(result) {
				$('#tip-model-btn').modal();
			},
			error : function(result) {
				console.log("失败");
			}
		});
	})
})