$(function() {
	$('#ruleCategory').change(function(e) {
		var self = $(e.target);
		if (self.val() == 9) {
			$('#rate-div').removeClass('hide');
			$('#score').val('0');
		} else {
			$('#rate-div').addClass('hide');
			$('#rate').val('1.0');		
			$('#score').val('0');
		}
	});

	$.validator.addMethod("regex", function(value, element, regexp) {
		var re = new RegExp(regexp);
		return this.optional(element) || re.test(value);
	}, "Please check your input.");

	$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);

	$('.datepicker').datepicker({
		minDate : 0,
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true
	});

	$('#form').validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				remote : {
					url : global.context + '/admin/cardRules/name/duplicate',
					data : {
						name : function() {
							return $('#name').val();
						}
					}
				},
				minlength : 4,
				maxlength : 20
			},
			card : {
				required : true
			},
			ruleCategory : {
				required : true
			},
			beginTime : {
				dateISO : true,
				required : function() {
					return $('#endTime').val() !== '';
				},
				before : 'endTime'
			},
			endTime : {
				dateISO : true,
				required : function() {
					return $('#beginTime').val() !== '';
				},
				after : 'beginTime'
			},
			score : {
				digits : true,
				required : true
			},
			rate : {
				number : true,
				regex : '^[0-9]+\\.[0-9]$',
				required : function() {
					return $('#ruleCategory').val() == 9;
				},
			}
		},
		messages : {
			name : {
				required : '规则名称不能为空',
				remote : '名称不能重复',
				minlength : '最小长度为4个字',
				maxlength : '最大长度为20个字'
			},
			card : {
				required : '所属会员卡不能为空'
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
			ruleCategory : {
				required : '积分类型不能为空'
			},
			score : {
				digits : '请输入整数',
				required : '额外积分不能为空'
			},
			rate : {
				number : '请输入数字',
				regex : '小数点后一位',
				required:'交易倍率不能为空',
			}
		}
	});
});