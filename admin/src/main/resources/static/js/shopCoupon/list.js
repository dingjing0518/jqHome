$(function() {
	var submitFn = function(data) {
		var tmpl = $.templates("#detail");
		var html = tmpl.render(data.obj);
		$('table tbody').children().remove();
		$('table tbody').html(html);
	};

	var parameterFn = function() {
		var beginTime = $('#beginTime').val();
		var endTime = $('#endTime').val();
		var state = $('#state').val();
		return {
			beginTime : beginTime,
			endTime : endTime,
			state : state
		}
	};

	$('ul.pagination').pageable({
		url : global.context + '/admin/shopCoupons/json',
		parameter : parameterFn,
		fn : submitFn
	});

	$('.datepicker').datepicker({
		dateFormat : 'yy-mm-dd',
	});

	$('#queryForm').validate({
		onkeyup : false,
		rules : {
			beginTime : {
				before : 'endTime'
			},
			endTime : {
				after : 'beginTime'
			}
		},
		messages : {
			beginTime : {
				before : '生效日期应早于失效日期'
			},
			endTime : {
				after : '失效日期应晚于生效日期'
			}
		}
	});
});