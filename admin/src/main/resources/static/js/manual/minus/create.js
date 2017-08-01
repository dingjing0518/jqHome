$(function() {
	$.ajax({
		url : global.context + '/admin/shops/all',
		success : function(data) {
			$("#shopName").autocomplete({
				source : data.obj.map(function(o) {
					return o.name;
				})
			});
		}
	});

	$('#form').validate({
		onkeyup : false,
		rules : {
			mobile : {
				required : true,
				remote : {
					url : global.context + '/admin/members/exist',
					data : {
						name : function() {
							return $('#mobile').val();
						}
					}
				},
				minlength : 11,
				maxlength : 11
			},
			shopName : {
				required : true
			},
			flowCode : {
				required : true,
			},
			amount : {
				required : true
			}
		},
		messages : {
			mobile : {
				remote : '用户不存在',
				required : '请输入手机',
				minlength : '请输入11位手机号',
				maxlength : '请输入11位手机号'
			},
			shopName : {
				required : '请输入店铺名称'
			},
			flowCode : {
				required : '请输入流水号'
			},
			amount : {
				required : '请输入金额'
			}
		}
	});
});