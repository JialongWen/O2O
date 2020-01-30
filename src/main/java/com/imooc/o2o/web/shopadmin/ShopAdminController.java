package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = {"shopadmin"}, method = {RequestMethod.GET})
public class ShopAdminController
{
  @RequestMapping({"/shopoperation"})
  public String shopOperation() { return "shop/shopoperation"; }


  
  @RequestMapping({"/shoplist"})
  public String shopList() { return "shop/shoplist"; }


  
  @RequestMapping({"/shopmanage"})
  public String shopManage() { return "shop/shopmanage"; }


  
  @RequestMapping({"/productcategorymanage"})
  public String productCategoryManage() { return "shop/productcategorymanage"; }


  
  @RequestMapping({"/productedit"})
  public String productEdit() { return "shop/productedit"; }


  
  @RequestMapping({"/productmanage"})
  public String productManage() { return "shop/productmanage"; }

  @RequestMapping(value = "/shopauthmanage")
  public String shopAuthManagement(){
    return "shop/shopauthmanage";
  }

  @RequestMapping(value = "/shopauthedit")
  public String shopAuthEdit(){
    return "shop/shopauthedit";
  }

  @RequestMapping(value = "/operationsuccess")
  public String operationSuccess(){
    return "shop/operationsuccess";
  }

  @RequestMapping(value = "/operationfail")
  public String operationFail(){
    return "shop/operationfail";
  }

}


