(function($) {
	$.fn.count = function(options) {
		var settings = $.extend({
			total : 5
		}, options || {});

		var self = $(this);
		var p = $('<p>已经输入<span>0</span>个字</p>');
		self.after(p);
		var span = p.find('span');

		if (typeof CKEDITOR != 'undefined') {
			CKEDITOR.instances.summary.on("instanceReady", function(e) {
				span.text($.trim(CKEDITOR.instances.summary.editable().getText()).length);
				e.editor.on('change', function(event) {
					var length = CKEDITOR.instances.summary.editable().getText().length;
					span.text(length);
					if (length > settings.total)
						span.addClass('red');
					else
						span.removeClass('red');
				});
			});
		} else {
			var keyup = function() {
				var length = self.val().length;
				span.text(length);
				if (length > settings.total)
					span.addClass('red');
				else
					span.removeClass('red');
			};
			self.on('keyup', keyup)
		}
	};
}(jQuery));