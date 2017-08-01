$(function() {
	$("li.media").css({
		"height" : "70px"
	});
	$(".ac_list").click(function() {
		window.location.href = $(this).attr("data");
	});
});