$(function() {
	console.log($(window).height());
})

var mySwiper = new Swiper('.swiper-container', {
	loop : true,
	speed : 2000,
	slidesPerView : 1,
	autoplay : 3000,//可选选项，自动滑动
	simulateTouch : false, //禁止鼠标点击和拖动
	pagination : '.pagination',
})

if ($(window).width() < 700) {
	mySwiper.params.speed = 500;
}

function next() {
	mySwiper.slideNext(function() {
	}, 2000);
}
function next() {
	mySwiper.slideNext();
}