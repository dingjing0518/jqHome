$(function() {
	$("li.media").css({
		"height" : "70px"
	});
	if ($(window).width() > 700) {
		$('body').addClass('fixed')
	} else {
		$(".jiathis_button_weixin").hide();
		$("#share").click(function () {
			$("#share_pc").css("height",document.documentElement.clientHeight);
			$("#share_pc").css("width",document.documentElement.clientWidth);
			$("#share_pc").css("background-size","100% 100%");
			$("#share_pc").show();
		});
		$("#share_pc").click(function () {
			$("#share_pc").hide();
		});
		$('.vip').css("background-position", "center center");
	}
	mySwiper = new Swiper('.swiper-container', {
		speed : 1000,
		slidesPerView : "auto",
		autoplay : 3000,// 可选选项，自动滑动
		simulateTouch : false
	// 禁止鼠标点击和拖动
	})

	function next() {
		mySwiper.slideNext(function() {
		}, 2000);
	}
	function next() {
		mySwiper.slideNext();
	}
});
