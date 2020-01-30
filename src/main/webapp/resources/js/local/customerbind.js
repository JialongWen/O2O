$(function() {
	var bindUrl = '/myo2o/local/bindlocalauth';
	var usetype = getQueryString('usertype');
	$('#submit').click(function() {
		var userName = $('#username').val();
		var password = $('#psw').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		$.ajax({
			url : bindUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				VerifyCodeActual : verifyCodeActual
			},
			success : function(data) {
				if (data.success) {
					$.toast('绑定成功！');
					if(usetype == 1){
						//若是用户在前台展示系统页面则自动退回到前台展示页面
						window.location.href = '/myo2o/frontend/index';
					}else{
						window.location.href = '/myo2o/shopadmin/shoplist';
					}
				} else {
					$.toast('绑定失败！');
					$('#captcha_img').click();
				}
			}
		});
	});
});