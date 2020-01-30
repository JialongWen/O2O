package com.wjl.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("local")
public class LocalController {
	/**
	 * 绑定账号页路由
	 */
	@RequestMapping(value = "/accountbind",method = RequestMethod.GET)
	private String accountbind() {
		return "local/customerbind";
	}
	
	/**
	 * 登录页
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	private String login() {
		return "local/login";
	}
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping(value = "/changepsw",method = RequestMethod.GET)
	private String changepsw() {
		return "local/changepsw";
	}
}
