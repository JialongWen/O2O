package com.imooc.o2o.web.local;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.service.LocalAuthService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value = "local",method = {RequestMethod.GET,RequestMethod.POST})
public class LocalAuthController {
	@Autowired
	private LocalAuthService localAuthService;
	
	/**
	 * 将用户信息与平台绑定
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindlocalauth",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> bindLocalAuth(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//获取输入的账号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		//获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		//从session中获取当前用户信息（用户一旦通过微信登陆之后便可以获取到用户的信息）
		PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
		//非空判断，要求当前账号密码和当前的用户session非空
		if(userName != null && password != null && user != null && user.getUserId() != null) {
			//创建LocalAuth对象并赋值
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUsername(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			//绑定账号
			LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
			if(lae.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("error", lae.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("error", "用户名和密码不可为空");
		}
		return modelMap;
	}
	/**
	 * 修改密码的流程逻辑
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changelocalpwd",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> changeLocalPwd(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//获取账户
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		//获取新密码
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		//从session中获取当前用户信息,用户已从微信登陆之后，便可获取到用户信息
		PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
		//非空判断
		if(userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
				&& !password.equals(newPassword)) {
			try {
				//查看原先账号，看看输入的密码是否一致，不一致则认为是非法操作
				LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
				if(localAuth == null || !localAuth.getUsername().equals(userName)) {
					//不一致则直接退出
					modelMap.put("success", false);
					modelMap.put("error", "输入的账号非本次登陆账号");
					return modelMap;
				}
				//修改平台账号的用户密码
				LocalAuthExecution lae = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
				if(lae.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("error", lae.getStateInfo());
				}
			}catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("error", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("error", "请输入密码");
		}
		return modelMap;
	}
	
	/**
	 * 用户登录的校验逻辑
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loincheck",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> logincheck(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		//获取是否需要进行验证码校验的标识符
		boolean needVerfy = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if(needVerfy && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//获取账户密码
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		//非空校验
		if(userName != null && password != null) {
			//传入账号和密码去获取平台账号信息
			LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName, password);
			if(localAuth != null) {
				//若能获取到账号信息则登陆成功
				modelMap.put("success", true);
				//同时往session里设置用户信息
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名与密码不可为空");
		}
		return modelMap;
	}

	/**
	 * 当用户点击退出登陆时注销session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> logout(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		//将用户session置空
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
	
}
