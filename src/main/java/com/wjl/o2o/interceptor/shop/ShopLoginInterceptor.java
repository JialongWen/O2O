package com.wjl.o2o.interceptor.shop;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjl.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 主要做事前拦截，即用户操作发生之前，改写preHandle里的逻辑进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从session中取出用户信息来
		System.out.println("进入登陆拦截器");
		Object userObj = request.getSession().getAttribute("user");
		if(userObj != null) {
			//若是用户信息不为空则将session里的用户信息转换成PersonInfo实体类对象
			PersonInfo user = (PersonInfo)userObj;
			//做出空值判断，确保userId不为空且该账号的可用状态为1，并且用户类型为店家
			if(user != null && user.getUserId() != null && user.getUserId() >0 && user.getEnableStatus() ==1) {
				//若是通过验证则返回true,拦截器返回true之后，用户接下来的操作得以正常执行
				return true;
			}
			}
			//若是不满足验证条件，则跳转到账户登录页面
			PrintWriter out = response.getWriter();
			out.print("<html>");
			out.print("<script>");
			out.print("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self')");
			out.print("</script>");
			out.print("</html>");
		return false;
	}

}
