$(function() {
        $("li.media").css({"height":"70px"});
	if ($(window).width() > 700) {
		$('body').addClass('fixed');
		$('.about').css("background","url("+$('.about').attr("src")+") no-repeat");
    	$('.about').css("background-size","cover");
    	$('.about').css("background-position","50% 50%");
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
		$('.modal').remove();
		$('.about').css("background","url("+$('.about').attr("data")+") no-repeat");
    	$('.about').css("background-size","cover");
    	$('.about').css("background-position","50% 50%");
	}
	if ($(window).height() < 800 && $(window).width() > 700) {
		$('.ab_box>div').css('margin-top', '-50px')
		$('.ab_box>div.ab_last').css('margin-top', '-80px')
		$('.ab_box>div.ab_last img').css('width', '80%')
	}
});