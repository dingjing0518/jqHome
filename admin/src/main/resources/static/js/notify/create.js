$(function() {
	$('#form').validate({
		onkeyup : false,
		rules : {
			title : {
				required : true
			},
			content : {
				required : true
			},
			sort:{
				required : true
			}
		},
		messages : {
			title : {
				required : '消息名称不能为空'
			},
			content : {
				required : '消息内容不能为空'
			},
			sort:{
				required:'序号不能为空'
			}
		}
	});
	
})