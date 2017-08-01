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
			name : {
				required : true,
				maxlength : 20,
				remote : {
					url : global.context + '/admin/shops/name/duplicate',
					data : {
						name : function() {
							return $('#name').val();
						}
					}
				}
			},
			shopKey : {
				required : true,
				remote : {
					url : global.context + '/admin/shops/key/duplicate',
					data : {
						shopKey : function() {
							return $('#shopKey').val();
						}
					}
				}
			},
			floor : {
				required : true
			}
		},
		messages : {
			name : {
				required : '名称不能为空',
				maxlength : '名称最长不能超过20',
				remote : '名称重复'
			},
			shopKey : {
				required : 'Pos-Key不能为空',
				maxlength : 'Pos-Key最长不能超过255',
				remote : 'Pos-Key已存在'
			},
			floor : {
				required : '楼层不能为空'
			}
		}
	});
})