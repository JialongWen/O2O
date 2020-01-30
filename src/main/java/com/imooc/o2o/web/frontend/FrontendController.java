package com.imooc.o2o.web.frontend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("frontend")
public class FrontendController {

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	private String index() {
		return "frontend/index";
	}
	
	@RequestMapping(value = "/shoplist",method = RequestMethod.GET)
	private String ShopList() {
		return "frontend/shoplist";
	}
	
	@RequestMapping(value = "/shopdetail",method = RequestMethod.GET)
	private String ShopDetail() {
		return "frontend/shopdetail";
	}
	
	@RequestMapping(value = "/productdetail",method = RequestMethod.GET)
	private String ProductDetail() {
		return "frontend/productdetail";
	}
	
	@RequestMapping(value = "/customerbind", method = RequestMethod.GET)
	private String customerBind() {
		
		return "frontend/customerbind";
	}
	
}
