package com.imooc.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("frontend")
public class ShopListController {
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Autowired
	private ShopService shopservice;
	
	@Autowired
	private ShopCategoryService shopCategoryCateService;
	
	@Autowired
	private AreaService AreaService;
	
	/**
	 * 返回商铺列表业里的ShopCategory列表（二级或一级），以及区域信息列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/listshopspageinfo" , method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listShopsPageInfo(HttpServletRequest request) throws Exception{
		Map<String,Object> modelMap = new HashMap<String, Object>();
		//试着从前端请求中获取parentId
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if(parentId != -1) {
			//如果parentId存在，则取出该一级shopCategiry下的二级ShopCategory列表
			try {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(parentId);
				shopCategoryCondition.setParent(parent);
				shopCategoryList = shopCategoryCateService.getShopCategoryList(shopCategoryCondition);
			}catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
			//如果parentId不存在的话，则取出所有一级ShopCategory（用户在首页选择的是全部商店列表）
			try {
			shopCategoryList = shopCategoryCateService.getShopCategoryList(null);
			}catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		
		List<Area> areaList = null;
		try {
			//获取区域列表信息
			areaList = AreaService.getArea();
			modelMap.put("success", true);
			modelMap.put("areaList", areaList);
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}

		return modelMap;
	}
	
	/**
	 * 获取指定查询条件下的店铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshops" , method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listShops(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		//页码获取
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取每页的条数
		int PageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//非空判断
		if((pageIndex > -1)&&(PageSize > -1)) {
			//尝试获取一级类别Id
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			//试着获取特定二级类别Id
			long shopCategoryId= HttpServletRequestUtil.getLong(request, "shopCategoryId");
			//尝试获取区域Id
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			//试着获取模糊查询的名字
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			//获取组合之后的查询条件
			Shop shopCondition = compactShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
			//格局查询条件和分页信息获取店铺列表，并返回总数
			ShopExecution se = null;
			try {
				se = shopservice.getShopList(shopCondition, pageIndex, PageSize);
			} catch (Exception e) {
				modelMap.put("success", true);
				modelMap.put("errMsg", e.toString());
			}
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", true);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}
		return modelMap;
	}
	
	//组合所有的查询条件返回一个Shop对象
	private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
		if (parentId != -1L) {
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			shopCondition.setShopCategory(parentCategory);
		}
		if (shopCategoryId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if (areaId != -1L) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}

		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
