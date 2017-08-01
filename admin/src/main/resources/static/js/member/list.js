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
		var card = $('#card').val();
		return {
			beginTime : beginTime,
			endTime : endTime,
			card : card
		}
	};

	$('ul.pagination').pageable({
		url : global.context + '/admin/members/json',
		parameter : parameterFn,
		fn : submitFn
	});

	$('.datepicker').datepicker({
		dateFormat : 'yy-mm-dd'
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