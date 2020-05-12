$(function() {
	initSelected();
	
	$("#queryBtn").click(function(){
		$("#queryForm").submit();
	})
});

// 初始化下拉框
function initSelected() {
	console.log("初始化json");
	$.getJSON("./json/star.json", function(data) {
		$.each(data, function(index, item) {
			var option = "<option value='" + item　+ "'>" + item + "</option>"
			$("#star").append(option);
		})
	})
}
