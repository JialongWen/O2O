/**
 * 
 * 
 */
$(function(){
	var loginUrl = "/myo2o/local/loincheck";
	var usertype =getQueryString("usertype");
	var loginCount =0;
	
	$("#submit").click(function(){
		var userName = $("#username").val();
		var password = $("#psw").val();
		var verifyCodeActual = $("#j_captcha").val();
		var needVerify =false;
		if(loginCount >=3){
			if(!verifyCodeActual){
				$.toast("请输入验证码！");
				return;
			}else{
				needVerify = true;
			}
		}
		//访问后台验证
		$.ajax({
			url : loginUrl,
			async:false,
			cache:false,
			type:"post",
			dataType:'json',
			data:{
				userName:userName,
				password:password,
				VerifyCodeActual:verifyCodeActual,
				//是否需要验证码校验
				needVerify:needVerify
			},
			success:function(data){
				if(data.success){
				$.toast("登陆成功！");
				if(usertype == 1){
					//若是用户在前端展示系统页面自动连接到前端展示系统
					window.location.href ='/myo2o/frontend/index';
				}else{
					//若是用户是在店家管理系统登陆的那么连接到店家管理系统
					window.location.href ='/myo2o/shopadmin/shoplist';
				}
				}else{
					$.toast("登陆失败!"+data.errMsg);
					loginCount++;
					if(loginCount >= 3){
						$("#verifyPart").show();
					}
				}
			}
		});
	});
});