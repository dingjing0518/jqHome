$(function() {
	jQuery.validator.addMethod("isIdCardNo", function (value, element) {
        return this.optional(element) || isIdCardNo(value);
    }, "请正确输入您的身份证号码");
	
	$.datepicker.setDefaults($.datepicker.regional["zh-CN"]);

	$('.datepicker').datepicker({
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true
	});

	$('#form').validate({
		onkeyup : false,
		rules : {
			'memberDetail.realName' : {
				minlength : 2,
				maxlength : 10,
			},
			name : {
				digits:true,
				required : true,
				minlength : 11,
				maxlength : 11,
				remote : {
					url : global.context + '/admin/members/duplicate',
					data : {
						name : $('#mobile').val()
					}
				}
			},
			ic: {
				isIdCardNo: true
			},
			'memberDetail.birthday' : {
				dateISO : true
			},
			'memberDetail.address' : {
				maxlength : 200
			}
		},
		messages : {
			'memberDetail.realName' : {
				minlength : '最短长度为2',
				maxlength : '最大长度为10'
			},
			name : {
				digits:'请输入数字',
				required : '请输入手机号',
				remote : '手机号不能重复',
				minlength : '手机号为11位',
				maxlength : '手机号为11位'
			},
			'memberDetail.birthday' : {
				dateISO : '请输入正确的日期格式yyyy-mm-dd'
			},
			'memberDetail.address' : {
				maxlength : '长度小于200'
			}
		}
	});
	
	$("#ic").blur(function(){
		if($.trim($("#ic").val()) != "") {
			var date = "";
			if($("#ic").val().length == 15) {
				date =  '19'+$("#ic").val().substr(6,2)+'-'+$("#ic").val().substr(8,2)+'-'+$("#ic").val().substr(10,2);
			} else if ($("#ic").val().length == 18) {
				date =  $("#ic").val().substr(6,4)+'-'+$("#ic").val().substr(10,2)+'-'+$("#ic").val().substr(12,2);
			}
			$("[id='memberDetail.birthday']").val(date);
		}	    
	});
})

//增加身份证验证
	function isIdCardNo(num) {
	    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
	    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
	    var varArray = new Array();
	    var intValue;
	    var lngProduct = 0;
	    var intCheckDigit;
	    var intStrLen = num.length;
	    var idNumber = num;
	    // initialize
	    if ((intStrLen != 15) && (intStrLen != 18)) {
	        return false;
	    }
	    // check and set value
	    for (i = 0; i < intStrLen; i++) {
	        varArray[i] = idNumber.charAt(i);
	        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
	            return false;
	        } else if (i < 17) {
	            varArray[i] = varArray[i] * factorArr[i];
	        }
	    }
	    if (intStrLen == 18) {
	        //check date
	        var date8 = idNumber.substring(6, 14);
	        if (isDate8(date8) == false) {
	            return false;
	        }
	        // calculate the sum of the products
	        for (i = 0; i < 17; i++) {
	            lngProduct = lngProduct + varArray[i];
	        }
	        // calculate the check digit
	        intCheckDigit = parityBit[lngProduct % 11];
	        // check last digit
	        if (varArray[17] != intCheckDigit) {
	            return false;
	        }
	    }
	    else {        //length is 15
	        //check date
	        var date6 = idNumber.substring(6, 12);
	        if (isDate6(date6) == false) {
	            return false;
	        }
	    }
	    return true;
	}
	function isDate6(sDate) {
	    if (!/^[0-9]{6}$/.test(sDate)) {
	        return false;
	    }
	    var year, month, day;
	    year = sDate.substring(0, 4);
	    month = sDate.substring(4, 6);
	    if (year < 1700 || year > 2500) return false;
	    if (month < 1 || month > 12) return false;
	    return true;
	}

	function isDate8(sDate) {
	    if (!/^[0-9]{8}$/.test(sDate)) {
	        return false;
	    }
	    var year, month, day;
	    year = sDate.substring(0, 4);
	    month = sDate.substring(4, 6);
	    day = sDate.substring(6, 8);
	    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
	    if (year < 1700 || year > 2500) return false;
	    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
	    if (month < 1 || month > 12) return false;
	    if (day < 1 || day > iaMonthDays[month - 1]) return false;
	    return true;
	}