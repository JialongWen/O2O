package com.wjl.o2o.service.impl;

import java.util.List;

import com.wjl.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wjl.o2o.dao.ProductCategoryDao;
import com.wjl.o2o.dto.ProductCategoryExecution;
import com.wjl.o2o.entity.ProductCategory;
import com.wjl.o2o.enums.ProductCategoryStateEnum;
import com.wjl.o2o.exception.ProductCategoryOperationException;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if(productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao.batchaInsertProductCategory(productCategoryList);
				if(effectedNum<=0) {
					throw new ProductCategoryOperationException("商品类别创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			}catch (Exception e) {
			    	throw new ProductCategoryOperationException("bathAddProductCategory error:"+e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductcategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//将该商品类别下的商品商品ID置为空 TODO
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectedNum <= 0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductCategoryOperationException("deleteProductCategory error"+e.getMessage());
		}
	}


}
