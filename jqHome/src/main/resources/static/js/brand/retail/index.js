$(function(){
	$("li.media").css({"height":"70px"});
	var loadedImages = 0, // Counter for loaded images
	  con='.retail_bottom ul',
			$container = $(con),
	  tileCount = 30,
	  wookmark;

      // Initialize Wookmark
	   wookmark = new Wookmark(con, {
       	align: 'left',
       	offset:10
      });
		if($(window).width()<700){	
			wookmark = new Wookmark(con, {
				itemWidth:'48%',
				flexibleWidth: true, 
			});
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
		}
      $container.imagesLoaded()
        .always(function () {
          
        })
        .progress(function (instance, image) {
          // Update progress bar after each image has loaded and remove loading state
          $(image.img).closest('li').removeClass('tile-loading');
          wookmark.updateOptions();
        });
})