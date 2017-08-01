$(function() {
	var tmpl = $.templates("#detail");

	var submitFn = function(data) {
		if (data.obj.content.length > 0) {
			var html = tmpl.render(data.obj);
			console.log(html);
			$('table tbody').children().remove();
			$('table tbody').html(html);
		}
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
		url : global.context + '/admin/giftCoupons/json',
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
				before : '开始日起应小于结束日期'
			},
			endTime : {
				after : '结束日期应大于开始日期'
			}
		}
	});
});