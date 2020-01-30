package com.wjl.o2o.dao;

import java.util.List;

import com.wjl.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductDao {
	/**
	 * 根据id查询一个商品
	 * @param productId
	 * @return
	 */
	Product queryProductByProductId(long productId);
	
	/**
	 *查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，商品Id,商品类别
	 *
	 * @param productdition
	 * @param rowIndex
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);
	/**
	 * 查询对应的商品总数
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);
	
	/**
	 * 商品添加
	 * @param product
	 * @return
	 * @throws Exception
	 */
	int insertProduct(Product product)throws Exception;
	
	/**
	 * 根据productId 查询该商品的信息
	 * @param productId
	 * @return Product商品实体
	 * @throws Exception
	 */
	Product queryProductById(@Param("productId") long productId)throws Exception;
	
	/**
	 * 更新商品信息
	 */
	int updateProduct(Product product)throws Exception;
	
	/**
	 * 删除该id下的所有图片
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
