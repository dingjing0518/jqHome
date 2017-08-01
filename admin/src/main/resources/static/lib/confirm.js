$(function($) {
	$.fn.confirm = function(options) {
		var settings = $.extend({
			height : 140,
			dialogId : '#dialog-confirm',
			formId : '',
			beforeSubmit : function() {
			}
		}, options || {});

		var dialog = $(settings.dialogId).dialog({
			autoOpen : false,
			resizable : false,
			height : 140,
			modal : true,
			buttons : {
				确认 : function() {
					settings.beforeSubmit();
					$(settings.formId).submit();
				},
				取消 : function() {
					$(this).dialog("close");
				}
			}
		});

		this.on('click', function(event) {
			dialog.dialog("open");
			return false;
		});
	};
}(jQuery));