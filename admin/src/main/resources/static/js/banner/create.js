$(function() {
	$('div.col-md-5 input').change(
			function(event) {
				var source = $(event.target);
				var id = source.attr('id');
				$.ajaxFileUpload({
					url : global.context + '/admin/upload',
					secureuri : false,
					fileElementId : source.attr('id'),
					dataType : 'json',
					success : function(data, status) {
						if (status == 'success') {
							$('div.col-md-5 img').attr('src',
									global.url + data.responseText);
							$('#coverImg').val(global.url + data.responseText);
						}
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
			title : {
				required : true,
				maxlength : 20
			},
			name : {
				required : true,
				maxlength : 100
			},
			coverImg : {
				required : true
			},
			jumpTo : {
				required : true
			}
		},
		messages : {
			title : {
				required : '标题不能为空',
				maxlength : '标题最长不能超过20'
			},
			name : {
				required : '名称不能为空',
				maxlength : '名称最长不能超过100'
			},
			coverImg : {
				required : '封面图不能为空'
			},
			jumpTo : {
				required : '跳转链接不能为空'
			}
		}
	});
})