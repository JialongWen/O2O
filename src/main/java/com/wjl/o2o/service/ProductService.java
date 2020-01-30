package com.wjl.o2o.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wjl.o2o.dto.ProductExecution;
import com.wjl.o2o.entity.Product;
import com.wjl.o2o.exception.ProductOperationException;

public interface ProductService {
	
	Product getProductById(long productId);
	
	/**
	 * 查询商品列表并分页，可输入的条件有：商品名称（模糊）,商品状态，店铺Id，商品类别
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
	
	/**
	 * 添加商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs) throws ProductOperationException;
	
	/**
	 * 根据id 返回唯一的product对象
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	Product queryProduct(Long productId)throws Exception;
	
	/**
	 * 更新商品信息以及新的图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs) throws ProductOperationException;

}
