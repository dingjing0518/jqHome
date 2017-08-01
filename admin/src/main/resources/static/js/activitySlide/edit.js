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
			vedioName: {
				required: true
			},
			vedioUrl:{
				required: true
			},
			coverImg : {
				url : true
			},
			sort:{
				required: true
			}
		},
		messages:{
			vedioName: {
				required: '视频名称不能为空'
			},
			vedioUrl:{
				required: '视频url不能为空'
			},
			coverImg : {
				url : '默认视频图片不能为空'
			},
			sort:{
				required:'视频序号不能为空'
			}
		}
	});
})