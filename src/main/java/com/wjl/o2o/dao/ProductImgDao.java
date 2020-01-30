package com.wjl.o2o.dao;

import java.util.List;

import com.wjl.o2o.entity.ProductImg;

public interface ProductImgDao {
	
	/**
	 * 批量添加商品详情图
	 * @param prouctImgList
	 * @return
	 * @throws Exception
	 */
	int batchInsertProductImg(List<ProductImg> productImgList)throws Exception;
	
	List<ProductImg> queryProductImgList(long productId);
	
}
