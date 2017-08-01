$(function() {
	$('div.upload').change(function(event) {
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
			},
			error : function(data, status, e) {
				alert(e);
			}
		});
		return false;
	});

	$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);

	$('.datepicker').datepicker({
		minDate : 0,
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true
	});

	$('textarea').ckeditor();

	$('#summary').count({
		total : 500
	});
	
	$('#form').validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				remote : {
					url : global.context + '/admin/shopCoupons/name/duplicate',
					data : {
						name : function() {
							return $('#name').val();
						},
						id : $('#validForm').attr('data-id')
					}
				}
			},
			amount : {
				number : true,
				required : true
			},
			score : {
				digits : true,
				required : true
			},
			beginTime : {
				dateISO : true,
				required : function() {
					return $('#endTime').val() != '';
				},
				before : 'endTime'
			},
			endTime : {
				dateISO : true,
				required : function() {
					return $('#beginTime').val() != '';
				},
				after : 'beginTime'
			},
			total : {
				digits : true
			},
			shop : {
				required : true
			},
			couponEndTime : {
				dateISO : true,
				required : function() {
					return $('#beginTime').val() != '';
				},
				after : 'endTime'
			}
		},
		messages : {
			name : {
				required : '名称不能为空',
				remote : '名称不能重复'
			},
			amount : {
				number : '请输入数字',
				required : '请输入金额'
			},
			score : {
				digits : '请输入整数',
				required : '请输入积分'
			},
			total : {
				digits : '请输入整数'
			},
			shop : {
				required : '所属店铺不能为空'
			},
			beginTime : {
				dateISO : '请输入正确的日期格式yyyy-mm-dd',
				required : '请输入生效日期',
				before : '生效日期应早于失效日期'
			},
			endTime : {
				dateISO : '请输入正确的日期格式yyyy-mm-dd',
				required : '请输入失效日期',
				after : '失效日期应晚于生效日期'
			},
			couponEndTime : {
				dateISO : '请输入正确的日期格式yyyy-mm-dd',
				required : '请输入核销截止日期',
				after : '核销截止日期应晚于卡券失效日期'
			}

		}
	});

	$('#valid-button').confirm({
		formId : '#validForm'
	});

})