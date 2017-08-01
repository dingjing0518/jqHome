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
			rightImg : {
				required : true
			},
			PmiddleImg : {
				required : true
			},
			MmiddleImg : {
				required : true	
			},
			activityContent : {
				required : true
			},
			activityTitle : {
				required : true
			}
		},
		messages : {
			rightImg : {
				required : '右上图不能为空'
			},
			PmiddleImg : {
				required : '中下图(PC端)不能为空'	
			},
			MmiddleImg : {
				required : '中下图(移动端)不能为空'	
			},
			activityTitle : {
				required : '活动名称不能为空'
			},
			activityContent : {
				required : '活动内容不能为空'
			}
		}
	});
	
//	$('textarea').ckeditor({
//    	height: 400
//    });
})