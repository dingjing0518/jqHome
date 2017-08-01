$(function() {
	$('div.imgUp').change(function(event) {
		var source = $(event.target);
		var id = source.attr('id');
		var img = source.parent().parent().find('img');
		var input = source.siblings().last();
		$.ajaxFileUpload({
			url : global.context + '/admin/upload',
			secureuri : false,
			fileElementId : source.attr('id'),
			dataType : 'json',
			success : function(data, status) {
				if (status == 'success') {
					img.attr('src', global.url + data.responseText);
					input.val(global.url + data.responseText);
				}
				console.log(data);
			},
			error : function(data, status, e) {
				alert(e);
			}
		});
		return false;
	});

	$('#form').validate({
		onkeyup : false,
		rules : {
			topBackgroundImg : {
				required : true
			},email: {
				required : true,
				email:true
			},phone : {
				required : true
			},address : {
				required : true
			},iconLogoImg : {
				required : true
			},invested:{
				required : true
			}
			
		},
		messages : {
			topBackgroundImg : {
				required : '顶部背景图不能为空'
			},
			email : {
				required : '邮箱不能为空',
				email:'请输入正确的邮箱'	
			},
			phone : {
				required : '电话不能为空'
			},
			address : {
				required : '地址不能为空'
			},
			iconLogoImg : {
				required : '下方logo图不能为空'
			},
			invested:{
				required : '投资方不能为空'
			}
		}
	});
	
//	$('textarea').ckeditor({
//    	height: 400
//    });
})