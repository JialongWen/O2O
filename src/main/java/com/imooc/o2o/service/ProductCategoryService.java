package com.imooc.o2o.service;

import java.util.List;
import java.util.Map;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.exception.ProductCategoryOperationException;

public interface ProductCategoryService {
	
	/**
	 * 根据两个id 删除指定的商品类别
	 * 将所有该类别下的商品类别Id值置为空再删除该类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws Exception
	 */
	ProductCategoryExecution deleteProductcategory(long productCategoryId, long shopId)throws ProductCategoryOperationException;
	
	/**
	 * 查询某个店铺下的所有商品类别信息
	 * @param Long shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> getProductCategoryList(long shopId);
	
	/**
	 * 
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
		throws ProductCategoryOperationException;
}
