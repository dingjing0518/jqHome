$(function() {
	var type = $('#type').val();
	if(type == 1) {
		$('#fromType').hide();
	} else if(type == 2) {
		$('#fromType').show();
	}
	
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
	
	$('#type').change(
			function(event) {
				var source = $(event.target);
				if(source.val() == 1) {
					$('#imgTip').html('请上传小于1MB文件大小的图片，图片仅限PNG、JPG格式<br/>建议尺寸：1920像素 * 1000像素');
				} else if (source.val() == 2) {
					$('#imgTip').html('请上传小于1MB文件大小的图片，图片仅限PNG、JPG格式<br/>建议尺寸：1420像素 * 488像素');
					
				}
			});



	$('#form').validate({
		onkeyup : false,
		rules : {
			type : {
				range:[1,2]
			},
			coverImg : {
				url:true
			}
		},
		messages : {
			type : {
				range : '幻灯片类型不能为空'
			},
			coverImg : {
				url : '背景图不能为空'
			}
		}
	});
})