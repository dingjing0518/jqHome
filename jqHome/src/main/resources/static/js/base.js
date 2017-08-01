// JavaScript Document
$(function(){
	$('.scale-box img').mouseover(function(){
		$(this).addClass('hover').removeClass('nohover');	
	}).mouseout(function(){
		$(this).removeClass('hover').addClass('nohover');	
	})
	$('.meun-btn').click(function(){
		if($('.meun').is(':hidden')){
			$('.meun').slideDown(100)
			$(this).addClass('rotate');
		}else{
			$('.meun').slideUp(100)
			$(this).removeClass('rotate');
		}
	})
	$(window).scroll(function(){
		if($(window).scrollTop()>200){
			$('.getTop').fadeIn();
		}else{
			$('.getTop').fadeOut();
		}
	});
	$('.getTop').click(function(){
		$('body,html').animate({ scrollTop:0 },500);
		return false;
	});
})
