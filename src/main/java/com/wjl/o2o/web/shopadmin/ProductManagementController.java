package com.wjl.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjl.o2o.dto.ProductExecution;
import com.wjl.o2o.entity.Product;
import com.wjl.o2o.entity.ProductCategory;
import com.wjl.o2o.entity.Shop;
import com.wjl.o2o.enums.ProductStateEnum;
import com.wjl.o2o.exception.ProductOperationException;
import com.wjl.o2o.service.ProductCategoryService;
import com.wjl.o2o.service.ProductService;
import com.wjl.o2o.util.CodeUtil;
import com.wjl.o2o.util.HttpServletRequestUtil;

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
public class ProductManagementController{
	
  @Autowired
  private ProductService productService;
  
  @Autowired
  private ProductCategoryService productCategpryService;
  
  //设置图片上传的最大值
  private static final int IMAGEMAXCOUNT = 6;
  
  @RequestMapping(value = {"/getproductlistbyshop"}, method = {RequestMethod.GET})
  @ResponseBody
  private Map<String, Object> getProductListShop(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    
    int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
    int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
    Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
    try {
      if (pageIndex > -1 && pageSize > -1 && currentShop.getShopId() != null) {

        long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
        String prductName = HttpServletRequestUtil.getString(request, "prductName");
        
        Product prodcutCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, prductName);
        try {
          ProductExecution pe = productService.getProductList(prodcutCondition, pageIndex, pageSize);
          modelMap.put("productList", pe.getProductList());
          modelMap.put("count", pe.getCount());
          modelMap.put("success", true);
        } catch (Exception e) {
          modelMap.put("success", false);
          modelMap.put("errMsg","系统内部错误"+ e.toString());
        } 
      } else {
        
        modelMap.put("success", false);
        modelMap.put("errMsg", "empt pageSize or pageIndex or shopId");
      } 
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "empt shopId");
      return modelMap;
    } 
    return modelMap;
  }
  
  private Product compactProductCondition(Long shopId, long productCategoryId, String prductName) {
    Product productCondition = new Product();
    Shop shop = new Shop();
    shop.setShopId(shopId);
    productCondition.setShop(shop);
    if (productCategoryId != -1L) {
      ProductCategory productCategory = new ProductCategory();
      productCategory.setProductCategoryId(productCategoryId);
      productCondition.setProductCategory(productCategory);
    } 
    
    if (prductName != null) {
      productCondition.setProductName(prductName);
    }
    return productCondition;
  }
  
  @RequestMapping(value = {"/modifyproduct"}, method = {RequestMethod.POST})
  @ResponseBody
  private Map<String, Object> modifyProduct(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    //是否需要进行验证码判断
    boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
    
    if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "验证码错误");
      return modelMap;
    } 
    //初始化各个对象
    ObjectMapper mapper = new ObjectMapper();
    Product product = null;
    CommonsMultipartFile thumbnail = null;
    List<CommonsMultipartFile> productImgList = new ArrayList<>();
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    
    try {
      if (multipartResolver.isMultipart(request)) {
        thumbnail = handleImage(request, productImgList);
      }
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    } 
    try {
      String productStr = HttpServletRequestUtil.getString(request, "productStr");
      product = (Product)mapper.readValue(productStr, Product.class);
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    } 
    
    if (product != null) { 
      try {
    	 Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        product.setShop(currentShop);
        
        ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
        if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", pe.getStateInfo());
        } 
      } catch (RuntimeException e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.toString());
        return modelMap;
      } 
    } else {
      modelMap.put("success",false);
      modelMap.put("errMsg", "操作失败");
    } 
    return modelMap;
  }
  
  @RequestMapping(value = {"/getproductbyid"}, method = {RequestMethod.GET})
  @ResponseBody
  private Map<String, Object> getProductById(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    Long productId = HttpServletRequestUtil.getLong(request, "productId");
    if (productId > -1L) {
      try {
        Product product = productService.queryProduct(productId);
        
        List<ProductCategory> productCategoryList = productCategpryService.getProductCategoryList(product.getShop().getShopId());
        modelMap.put("success", true);
        modelMap.put("productCategoryList", productCategoryList);
        modelMap.put("product", product);
      } catch (Exception e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.toString());
      } 
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "emty productId");
    } 
    return modelMap;
  }
  
  //添加商品
  @RequestMapping(value = {"/addproduct"}, method = {RequestMethod.POST})
  @ResponseBody
  private Map<String, Object> addProduct(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();
    
    if (!CodeUtil.checkVerifyCode(request)) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "验证码错误");
      return modelMap;
    } 
    //初始化各个对象
    ObjectMapper mapper = new ObjectMapper();
    Product product = null;
    String productStr = HttpServletRequestUtil.getString(request, "productStr");
    CommonsMultipartFile thumbnail = null;
    List<CommonsMultipartFile> productImgList = new ArrayList<>();
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    
    try {
      if (multipartResolver.isMultipart(request)) {
        thumbnail = handleImage(request, productImgList);
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", "请上传商品详情图");
        return modelMap;
      } 
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    } 
    
    try {
      product = (Product)mapper.readValue(productStr, Product.class);
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    } 

    
    if (product != null && thumbnail != null && productImgList.size() > 0) {
      try {
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        product.setShop(currentShop);
        
        ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
        if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
        } else {
          
          modelMap.put("success", false);
          modelMap.put("errMsg", pe.getStateInfo());
        }
      
      } catch (ProductOperationException e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.toString());
        return modelMap;
      } 
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请填写完整的信息");
      return modelMap;
    } 
    return modelMap;
  }


  //处理文件集合的部分
  private CommonsMultipartFile handleImage(HttpServletRequest request, List<CommonsMultipartFile> productImgList) {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    
    CommonsMultipartFile thumbnail = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
    
    for (int i = 0; i < IMAGEMAXCOUNT;i++) {
      CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" + i);
      if (productImgFile != null) {
        productImgList.add(productImgFile);
      } 
    } 
    return thumbnail;
  }
}
