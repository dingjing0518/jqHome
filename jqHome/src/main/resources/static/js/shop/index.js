$(function() {
	$('.map-default img').click(function() {
		$(this).css('z-index', '1').siblings().css('z-index', '4')
		var d_id = $(this).attr('data-id');
		var c_id = $('.map-color div#' + d_id);
		$(c_id).show().siblings().hide();
		$('.pos4_12,.pos4_14').css('z-index', '6');
		$('.pos4_13,.pos2_11').css('z-index', '5');
		var img_w=c_id.find('img').width()/1.5;
		var img_h=c_id.find('img').height()/3;
		var left=c_id.position().left;
		var top=c_id.position().top;						
		$('.modal-msg').show().css({top:top+img_h,left:left+img_w,'z-index':'66'});

	})
	/*var len=$('.shop-con .s-floor').eq(0).find('.shop-list').length;
	if(len < 1){
		$('.footer').addClass('footed');
	}else{
		$('.footer').removeClass('footed');
	}*/
	var tab_a = $('.s-nav a');
	tab_a.click(function() {
		$(this).addClass('active').siblings().removeClass(
				'active');
		var index = tab_a.index(this);
		$('.shop-con .s-floor').eq(index).show().siblings().hide();
		/*var len=$('.shop-con .s-floor').eq(index).find('.shop-list').length;
		if(len < 1){
			$('.footer').addClass('footed');
		}else{
			$('.footer').removeClass('footed');
		}*/
				
		$('#scrollbar1,#scrollbar2,#scrollbar3,#scrollbar4,#scrollbar5,#scrollbar6')
		.tinyscrollbar({
			size : 780,
			sizethumb : 140,
			wheel : 30,
		});
	})
})
		