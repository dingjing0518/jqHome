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
			},
			mallPositionContent : {
				required : true
			},
			projectTeamContent : {
				required : true
			},
			businessInvestmentContent : {
				required : true
			}
		},
		messages : {
			coverImg : {
				required : '封面图不能为空'
			},
			mallPositionContent : {
				required : '商场定位描述不能为空'
			},
			projectTeamContent : {
				required : '项目团队描述不能为空'
			},
			businessInvestmentContent : {
				required : '商业投资描述不能为空'
			}
		}
	});
	
	$('textarea').ckeditor({
    	height: 400
    });
})