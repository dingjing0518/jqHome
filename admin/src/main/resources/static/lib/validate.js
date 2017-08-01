(function() {
	$.validator.addMethod('before', function(v, e, r) {
		var eEndTime = $('#' + r).val();
		if (eEndTime == '')
			return true;
		var endTime = new Date(eEndTime).getTime();
		var value = new Date(v).getTime();
		if (value > endTime)
			return false;
		return true;
	});

	$.validator.addMethod('after', function(v, e, r) {
		var eBeginTime = $('#' + r).val();
		if (eBeginTime == '')
			return true;
		var endTime = new Date(eBeginTime).getTime();
		var value = new Date(v).getTime();
		if (value < endTime)
			return false;
		return true;
	});
}())