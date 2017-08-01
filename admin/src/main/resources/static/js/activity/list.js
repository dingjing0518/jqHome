$(function() {
	var submitFn = function(data) {
		var tmpl = $.templates("#detail");
		var html = tmpl.render(data.obj);
		$('table tbody').children().remove();
		$('table tbody').html(html);
	};

	var parameterFn = function() {
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		var isShowOnIndex = $('#isShowOnIndex').val();
		return {
			startTime : startTime,
			endTime : endTime,
			isShowOnIndex : isShowOnIndex
		}
	};

	$('ul.pagination').pageable({
		url : global.context + '/admin/activity/json',
		parameter : parameterFn,
		fn : submitFn
	});

	$('.datepicker').datepicker({
		dateFormat : 'yy-mm-dd'
	});

})