package com.wjl.o2o.dao;

import java.util.List;

import com.wjl.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

public interface ProductCategoryDao {
	
	/**
	 * 根据productCategoryId 和 shopId 删除指定的类别
	 * @param productCategoryId shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId)throws Exception;
	
	
	/**
	 * 通过shopId 查询到店铺商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	/**
	 * 批量新增商品类别
	 * @param productCategoryList
	 * @return 影响的行数
	 * @throws Exception
	 */
	int batchaInsertProductCategory(List<ProductCategory> productCategoryList);
}
