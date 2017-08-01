var mySwiper1 = new Swiper('.swiper-container1', {
	speed : 800,
	slidesPerView : 1,
	autoplay : false, //可选选项，自动滑动
	simulateTouch : false, //禁止鼠标点击和拖动	

})
var mySwiper2 = new Swiper('.swiper-container2', {
	speed : 800,
	slidesPerView : 1,
	autoplay : false, //可选选项，自动滑动
	simulateTouch : false
//禁止鼠标点击和拖动

})
function next() {
	mySwiper.slideNext(function() {
	}, 2000);
}

function next() {
	mySwiper.slideNext();
}
if (mySwiper1.slides.length <= 1) {
	$('.swiper-container1 .plation').hide();
};
function next() {
	mySwiper.slideNext(function() {
	}, 2000);
}

function next() {
	mySwiper.slideNext();
}
if ($(window).width() <= 768) {
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
	$('#scrollbar1').hide();
	 $('#scrollbar1').hide();
     $(".act_contentlist").find("p").each(function () {
     if($(this).text().length>50){
         var text=$(this).text().substr(0,50);
         $(this).text(text+"...");
     }
 });
 $(".act_contentlist").find("h3").each(function () {
     if($(this).text().length>20){
         var text=$(this).text().substr(0,20);
         $(this).text(text+"...");
     }
 });
} else {
	$('#scrollbar1').tinyscrollbar();
	$('.act_contentlist').hide();
}
$(".viewport").find("p").each(function () {
    if($(this).text().length>50){
        var text=$(this).text().substr(0,50);
        $(this).text(text+"...");
    }
});
$(".viewport").find("h3").each(function () {
    if($(this).text().length>25){
        var text=$(this).text().substr(0,25);
        $(this).text(text+"...");
    }
});
