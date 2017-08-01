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
			},clauseImg : {
				required : true
			},clauseContent : {
				required : true
			},partnerImg : {
				required : true
			},partnerContent : {
				required : true
			},contactImg : {
				required : true
			}
			
		},
		messages : {
			coverImg : {
				required : '顶部图不能为空'
			},
			clauseImg : {
				required : '免责条款图不能为空'
			},
			clauseContent : {
				required : '免责条款描述不能为空'
			},
			partnerImg : {
				required : '合作伙伴图不能为空'
			},
			partnerContent : {
				required : '合作伙伴描述不能为空'
			},
			contactImg : {
				required : '联系我们图不能为空'
			}
		}
	});
	
	$('textarea').ckeditor({
    	height: 400
    });
})