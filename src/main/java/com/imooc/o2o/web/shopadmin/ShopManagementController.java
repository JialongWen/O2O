package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.web.shopadmin.ShopManagementController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Controller
@RequestMapping({"shopadmin"})
public class ShopManagementController
{
  @Autowired
  private ShopService shopService;
  @Autowired
  private ShopCategoryService shopCategoryService;
  @Autowired
  private AreaService areaService;
  
  //对未带shopId参数的请求进行拦截
  @RequestMapping(value = {"/getshopmanagementinfo"}, method = {RequestMethod.GET})
  @ResponseBody
  private Map<String, Object> getShopmanagementInfo(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    long shopId = HttpServletRequestUtil.getLong(request, "shopId");
    if (shopId <= 0L) {
      Shop currentShopObj = (Shop)request.getSession().getAttribute("currentShop");
      if (currentShopObj == null) {
        modelMap.put("redirect", true);
        modelMap.put("url", "/myo2o/shopadmin/shoplist");
      } else {
        Shop currentShop = new Shop();
        modelMap.put("redirect", false);
        modelMap.put("shopId", currentShop.getShopId());
      } 
    } else {
      Shop currentShop = new Shop();
      currentShop.setShopId(shopId);
      request.getSession().setAttribute("currentShop", currentShop);
      modelMap.put("redirect", false);
    } 
    return modelMap;
  }
  
  //根据用户ID获取其Id下的店铺列表
  @RequestMapping(value = {"/getshoplist"}, method = {RequestMethod.GET})
  @ResponseBody
  private Map<String, Object> getShopList(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
    try {
      Shop shopCondition = new Shop();
      shopCondition.setOwner(owner);
      System.out.println("店铺:"+shopCondition);
      ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
      if(se.getShopList().size() > 0) {
      modelMap.put("shopList", se.getShopList());
      //获取到列表之后将列表放入session中
      request.getSession().setAttribute("shopList", se.getShopList());
      modelMap.put("user", owner);
      modelMap.put("success", true);
      }else {
    	  modelMap.put("success", false);
          modelMap.put("errMsg", "暂无任何商铺");
      }
    }
    catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
    } 
    return modelMap;
  }
  
  //此方法作用于进入某一店铺的管理时的方法
  @RequestMapping(value = {"/getshopbyid"}, method = {RequestMethod.GET})
  @ResponseBody
  private Map<String, Object> getShopById(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    //从请求中获取shopId
    Long shopId = Long.valueOf(HttpServletRequestUtil.getLong(request, "shopId"));
    //非空判断
    if (shopId.longValue() > -1L) {
      try {
    	  //获取该店铺的信息
        Shop shop = shopService.getByShopId(shopId);
        //获取所有的地区信息
        List<Area> areaList = areaService.getArea();
        modelMap.put("shop", shop);
        modelMap.put("areaList", areaList);
        modelMap.put("success", true);
      } catch (Exception e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
      } 
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "empry shopId");
    } 
    return modelMap;
  }

  //注册店铺是申请的空白页面主要用来获取地区信息和商铺类别
  @RequestMapping(value = {"/getshopinitinfo"}, method = {RequestMethod.GET})
  @ResponseBody
  private Map<String, Object> getShopInitInfo(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    List<ShopCategory> shopCategoryList = new ArrayList<>();
    List<Area> areaList = new ArrayList<>();
    try {
      shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
      areaList = areaService.getArea();
      modelMap.put("shopCategoryList", shopCategoryList);
      modelMap.put("areaList", areaList);
      modelMap.put("success", true);
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
    } 
    return modelMap;
  }
  
  //注册店铺
  @RequestMapping(value = {"/registershop"}, method = {RequestMethod.POST})
  @ResponseBody
  private Map<String, Object> registerShop(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    //验证码判断
    if (!CodeUtil.checkVerifyCode(request)) {
      modelMap.put("success",false);
      modelMap.put("errMsg", "验证码输入错误");
      return modelMap;
    } 
    //获取前端上传过来的JSON流并初始化JSON工具类和店铺实体类
    String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
    ObjectMapper mapper = new ObjectMapper();
    Shop shop = null;
    
    try {
    	//将获得的JSON通过mapper封装成一个店铺实体
      shop = (Shop)mapper.readValue(shopStr, Shop.class);
    } catch (Exception e) {
      e.printStackTrace();
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
      return modelMap;
    } 
    //初始化获取文件类
    CommonsMultipartFile shopImg = null;
    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    //非空判断
    if (commonsMultipartResolver.isMultipart(request)) {
    	//获取文件并且将文件赋值给commonsMultipartFile类
      MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
      shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "上传文件不可为空");
      return modelMap;
    } 
    //开始注册店铺
    if (shop != null && shopImg != null) {
      PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
      shop.setOwner(owner);
      ShopExecution se = shopService.addShop(shop, shopImg);
      if (se.getState() == ShopStateEnum.CHECK.getState()) {
        modelMap.put("success",true);
        @SuppressWarnings("unchecked")
		List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
        if (shopList == null || shopList.size() == 0) {
          shopList = new ArrayList<>();
        }
        shopList.add(se.getShop());
        request.getSession().setAttribute("shopList", shopList);
      } else {
        modelMap.put("success", Boolean.valueOf(false));
        modelMap.put("errMsg", se.getStateInfo());
      } 
      return modelMap;
    }else { 
    modelMap.put("success", false);
    modelMap.put("errMsg", "empty shop or shopImg!");
    return modelMap;
    }
  }



  //更新店铺
  @RequestMapping(value = {"/modifyshop"}, method = {RequestMethod.POST})
  @ResponseBody
  private Map<String, Object> modifyShop(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    //验证码
    if (!CodeUtil.checkVerifyCode(request)) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "验证码输入错误");
      return modelMap;
    } 
    //获取JSON数据
    String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
    ObjectMapper mapper = new ObjectMapper();
    Shop shop = null;
    
    try {
      shop = (Shop)mapper.readValue(shopStr, Shop.class);
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.getMessage());
      return modelMap;
    } 
    CommonsMultipartFile shopImg = null;
    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    if (commonsMultipartResolver.isMultipart(request)) {
      MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
      shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
    } 
    
    if (shop != null && shop.getShopId() != null) {
      ShopExecution se = null;
      //判断是否需要更新图片
      if (shopImg == null) {
        se = shopService.modifyShop(shop, null);
      } else {
        se = shopService.modifyShop(shop, shopImg);
      } 
      if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
      } else {
        modelMap.put("success",false);
        modelMap.put("errMsg", se.getStateInfo());
      } 
      return modelMap;
    }else { 
	    modelMap.put("success", false);
	    modelMap.put("errMsg", "empty shopId");
	    return modelMap;
    }
  }
}
