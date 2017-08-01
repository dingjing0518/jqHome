/**
 * Created by yagamai on 16-1-12.
 */
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
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true
	});

	$('#form').validate({
		onkeyup : false,
		rules : {
			title : {
				required : true
			},
				url : {
					required : true
			},
			beginTime : {
				dateISO : true,
				before : 'endTime'
			},
			endTime : {
				dateISO : true,
				after : 'beginTime'
			}
		},
		messages : {
			title : {
				required : '活动标题不能为空'
			},
			url : {
				required : '活动链接不能为空'
			},
			beginTime : {
				dateISO : '请输入正确的日期格式yyyy-mm-dd',
				before : '开始日起应小于结束日期'
			},
			endTime : {
				dateISO : '请输入正确的日期格式yyyy-mm-dd',
				after : '结束日期应大于开始日期'
			}
		}
	});
});
