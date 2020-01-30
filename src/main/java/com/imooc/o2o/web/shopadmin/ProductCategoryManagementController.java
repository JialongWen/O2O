package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping({"shopadmin"})
public class ProductCategoryManagementController{
  @Autowired
  private ProductCategoryService productCategoryService;
  
  //获取某一个商铺下的所有商品类别
  @RequestMapping(value = {"/getproductcategorylistbyshopId"}, method = {RequestMethod.GET})
  @ResponseBody
  private Map<String, Object> getProductCategoryListByShopId(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    long shopId = HttpServletRequestUtil.getLong(request, "shopId");
    try {
      List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(shopId);
      modelMap.put("success", true);
      modelMap.put("productCategoryList", productCategoryList);
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    } 
    return modelMap;
  }

  //删除某一个商品类别
  @RequestMapping(value = {"/removeproductcategory"}, method = {RequestMethod.POST})
  @ResponseBody
  private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    //非空判断
    if (productCategoryId != null && productCategoryId > -1L) {
      try {
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");

        //进行删除操作
        ProductCategoryExecution pe = productCategoryService.deleteProductcategory(productCategoryId, currentShop.getShopId());
        if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", pe.getStateInfo());
        } 
      } catch (RuntimeException e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
        return modelMap;
      } 
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "empty productCategoryId");
    } 
    return modelMap;
  }


  //批量增加商品类别
  @RequestMapping(value = {"/addproductcategorys"}, method = {RequestMethod.POST})
  @ResponseBody
  private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    //此处的shop应该从Session中获取
    Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
    for (ProductCategory pc : productCategoryList) {
    	//赋予默认值
      pc.setShopId(currentShop.getShopId());
      pc.setCreateTime(new Date());
    } 
    //非空判断
    if (productCategoryList != null && productCategoryList.size() > 0) {
      try {
        ProductCategoryExecution pc = productCategoryService.batchAddProductCategory(productCategoryList);
        if (pc.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", pc.getStateInfo());
        } 
      } catch (Exception e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.getMessage());
      } 
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请添加需要上传的商品类别");
    } 
    return modelMap;
  }

  //获取商品列表
  @RequestMapping(value = {"/getproductcategorylist"}, method = {RequestMethod.GET})
  @ResponseBody
  private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
    Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
    List<ProductCategory> list = null;
    try {
      if (currentShop != null && currentShop.getShopId().longValue() > 0L) {
        list = this.productCategoryService.getProductCategoryList(currentShop.getShopId().longValue());
        return new Result<List<ProductCategory>>(true, list);
      } 
      
      ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
      return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
    
    }
    catch (Exception e) {
    	throw new ShopOperationException(e.getMessage());
    } 
  }
}
