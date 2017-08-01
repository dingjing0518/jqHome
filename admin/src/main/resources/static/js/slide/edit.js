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
			coverImg : {
				required : true
			},mobileImg: {
				required : true
			},sort : {
				required : true
			}
		},
		messages : {
			coverImg: {
				required : 'PC端背景图不能为空'
			},
			mobileImg : {
				required : '移动端背景图不能为空'	
			},
			sort : {
				required : '排序不能为空'
			}
		}
	});
	
//	$('textarea').ckeditor({
//    	height: 400
//    });
})