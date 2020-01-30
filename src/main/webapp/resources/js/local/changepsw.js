$(function() {
	var usertype = getQueryString("usertype");
	var url = '/myo2o/local/changelocalpwd';
	$('#submit').click(function() {
		var userName = $('#userName').val();
		var password = $('#password').val();
		var newPassword = $('#newPassword').val();
		var confirmPassword = $('#confirmPassword').val();
		var formData = new FormData();
		formData.append('userName', userName);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		if(newPassword != confirmPassword){
			$.toast('两次密码不一致!');
			return;
		}
		formData.append("VerifyCodeActual", verifyCodeActual);
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					if(usertype == 2){
					window.location.href = '/myo2o/shopadmin/shoplist';
					}else{
					window.location.href = '/myo2o/frontend/index';	
					}
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		if(usertype == 2){
			window.location.href = '/myo2o/shopadmin/shoplist';
		}else{
			window.location.href = '/myo2o/frontend/index';
		}
	});
});
