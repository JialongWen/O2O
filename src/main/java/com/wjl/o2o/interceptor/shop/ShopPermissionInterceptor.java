package com.wjl.o2o.interceptor.shop;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjl.o2o.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 主要做事前拦截，即哟农户操作发生前，改写preHandler里的逻辑，进行用户操作权限的拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从session中获得当前选择的店铺
		System.out.println("进入拦截器");
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		@SuppressWarnings("unchecked")
		List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
		//非空判断
		if(currentShop != null && shopList != null) {
			//遍历获得的店铺列表
			for(Shop shop : shopList) {
				//如果在当前店铺的可操作列表里则返回true,进行接下来的用户操作
				if(shop.getShopId() == currentShop.getShopId()) {
					return true;
				}
			}
		}else {
			PrintWriter out = response.getWriter();
			out.print("<html>");
			out.print("<p>对不起您没有这家店铺的管理权限</p>");
			out.print("<br>");
			out.print("<p>您可以返回<a href='" + request.getContextPath() + "/myo2o/frontend/index' >首页</a></p>");
			out.print("<br>");
			out.print("<p>或者，您也可以返回自己的<a href='/myo2o/shoapadmin/shoplist' >店铺列表</a></p>");
			out.print("</html>");
		}
		//若是不满足条件则返回false终止用户操作的执行
		return false;
	}

	
	
}
