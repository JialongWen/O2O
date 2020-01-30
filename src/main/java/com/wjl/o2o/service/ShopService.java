package com.wjl.o2o.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wjl.o2o.dto.ShopExecution;
import com.wjl.o2o.entity.Shop;
import com.wjl.o2o.exception.ShopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition分页返回相应列表数据
	 * @param shopCondition
	 * @param pageindex
	 * @param pageSzie
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageindex, int pageSzie)  throws Exception ;
	//添加店铺
	ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);
	//查询店铺操作
	Shop getByShopId(long shopId);
	//更新店铺操作
	ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) throws ShopOperationException;
	
}
