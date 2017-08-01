$(function() {
	$('#form').validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				maxlength : 20,
			}
		},
		messages : {
			name : {
				required : '名称不能为空',
				maxlength : '名称最长不能超过20',
			}
		}
	});
})