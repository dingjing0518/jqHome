/**
 * Created by yagamai on 15-12-23.
 */
(function ($) {
    $.fn.pageable = function (options) {
        var settings = $.extend({
            url: '',
            pageId: null,
            parameter: function () {
            	return {};
            },
            fn: function () {
            }
        }, options || {});

        function getPages(currentPage, maxSize, totalPages) {
            var pages = [];

            // Default page limits
            var startPage = 1, endPage = totalPages == 0 ? 1 : totalPages;
            var isMaxSized = maxSize && maxSize < totalPages;

            // recompute if maxSize
            if (isMaxSized) {
                // Current page is displayed in the middle of the visible ones
                startPage = Math.max(currentPage - Math.floor(maxSize / 2), 1);
                endPage = startPage + maxSize - 1;

                // Adjust if limit is exceeded
                if (endPage > totalPages) {
                    endPage = totalPages;
                    startPage = endPage - maxSize + 1;
                }
            }

            for (var number = startPage; number <= endPage; number++) {
                var page = makePage(number, number, number == currentPage);
                pages.push(page);
            }
            return pages;
        }

        function makePage(number, text, isActive) {
            return {
                number: number,
                text: text,
                active: isActive
            };
        }

        var self = this;

        var current = 1;

        if (settings.totalPages) {
            this.children().remove();
            var pages = getPages(1, 7, settings.totalPages);
            var temp = {};
            temp.pages = pages;
            var previous = $('<li> <a  aria-label="Previous"> <span aria-hidden="true">«</span> </a> </li>');
            var next = $('<li> <a  aria-label="Next"> <span aria-hidden="true">»</span> </a> </li>');
            pages.forEach(function (e) {
                var li = $('<li><a></a></li>');
                var a = li.find('a').html(e.text);
                if (e.active) {
                    li.addClass('active');
                }
                previous.after(li);
            });
            previous.after(next);
            this.html(previous);
        }

        this.on('click', 'li a', function (e) {
            var source = $(e.target);
            var page = Number(source.html())||current;
            var pages;
            var parameter = settings.parameter();
            var submit=function(){
				$.ajax({
					url: settings.url,
					data: parameter
				}).done(function (data) {
					settings.fn(data);
					pages = getPages(current, 7, data.obj.totalPages);
					self.children().remove();
					pages.forEach(function (e) {
						var li = $('<li><a></a></li>');
						var a = li.find('a').html(e.text);
						if (e.active) {
							li.addClass('active');
						}
						self.append(li);
					});
					self.append('<li><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>');
					self.prepend('<li><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>')
				});
            };

            if (source.attr('aria-label') == 'Previous' ) {
                page = current - 1;
                if (page <= 0)
                    page = 1;
            } else if (source.attr('aria-label') == 'Next' ) {
                page = current + 1;
                if(page>settings.totalPages){
                	page=settings.totalPages;
                }
            }

            parameter.page = page - 1;
            current = page;
            parameter.size = 10;
         	submit();
        });
    };
}(jQuery));
