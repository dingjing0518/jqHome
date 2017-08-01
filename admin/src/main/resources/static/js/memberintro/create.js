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
			},introduction : {
				required : true
			},memberRightsContent : {
				required : true
			},regFlowContent : {
				required : true
			},howToUseContent : {
				required : true
			}
			
		},
		messages : {
			coverImg : {
				required : '顶部图不能为空'
			},
			introduction : {
				required : '会员介绍描述不能为空'
			},
			coverImg : {
				required : '会员权益描述不能为空'
			},
			coverImg : {
				required : '注册流程描述不能为空'
			},
			coverImg : {
				required : '使用方法描述不能为空'
			}
		}
	});
	
	$('textarea').ckeditor({
    	height: 400
    });
})