$(function() {
	var upload = function(event) {
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
					$('#imgHref').val(global.url + data.responseText);
				}
			},
			error : function(data, status, e) {
				alert(e);
			}
		});
		return false;
	};

	$('#summary').count({
		total : 500
	});

	$('div.col-md-5 input').change(upload);

	$.validator.addMethod("regex", function(value, element, regexp) {
		var re = new RegExp(regexp);
		return this.optional(element) || !re.test(value);
	}, "Please check your input.");

	$('#form').validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				remote : {
					url : global.context + '/admin/cards/name/duplicate',
					data : {
						name : function() {
							return $('#name').val();
						}
					}
				},
				minlength : 4,
				maxlength : 20,
				regex : '[!@#$%&*()\'"-=\|\(\)\+,\.\?\]'
			},
			initScore : {
				digits : true,
				min : 0,
				required:true
			},
			summary : {
				maxlength : 500
			}
		},
		messages : {
			name : {
				required : '会员卡名称不能为空',
				remote : '会员卡名称重复',
				regex : '请输入汉字或英文字母（不包含特殊字符）',
				minlength : '必须大于4个字',
				maxlength : '必须小于20个字'
			},
			initScore : {
				digits : '请输入整数',
				min : '必须大于0'
			},
			summary : {
				maxlength : '必须小于500个字'
			}
		}
	});
})