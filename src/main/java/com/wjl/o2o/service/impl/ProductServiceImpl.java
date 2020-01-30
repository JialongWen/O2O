package com.wjl.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wjl.o2o.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wjl.o2o.dao.ProductDao;
import com.wjl.o2o.dao.ProductImgDao;
import com.wjl.o2o.dto.ProductExecution;
import com.wjl.o2o.entity.Product;
import com.wjl.o2o.entity.ProductImg;
import com.wjl.o2o.enums.ProductStateEnum;
import com.wjl.o2o.exception.ProductOperationException;
import com.wjl.o2o.util.ImageUtil;
import com.wjl.o2o.util.PageCalculator;
import com.wjl.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImgDao productImgDao;
	
	
	/**
	 * 1.处理缩略图，获取缩略图相对路径并赋值给product
	 * 2.往tb_product里写入商品信息
	 * 3.结合priductId批量处理商品详情图
	 * 4.将商品详情图列表批量插入tb_product_img中
	 */
	@Override
	public ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws ProductOperationException {
		//空值判断
		if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//给商品设置默认值
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认为上架状态
			product.setEnableStatus(1);
			//商品缩略图不为空就添加
			if(thumbnail != null) {
				addThunbnail(product,thumbnail);
			}
			try {
				//创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if(effectedNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			}catch (Exception e) {
				throw new ProductOperationException("创建商品失败"+e.toString());
			}
			
			//若商品详情图不为空则添加
			if(productImgs != null && productImgs.size() > 0) {
				addProoductImgList(product,productImgs);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else {
			throw new ProductOperationException("请正确填写商品 信息");
		}
	}


	private void addProoductImgList(Product product, List<CommonsMultipartFile> productImgs) {
		//获取存储路径，这里直接存放到相应的店铺文件夹下
		String desc = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		//遍历图片封装到productImg实体类中
		for(CommonsMultipartFile img : productImgs) {
			String imgAddr = ImageUtil.generateThumbnail(img, desc);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		
		//如果确实是有图片需要添加，就执行批量添加操作
		if(productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if(effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图失败");
				}
			}catch (Exception e) {
				throw new ProductOperationException("创建商品详情图失败"+e.toString());
			}
		}
	}


	private void addThunbnail(Product product, CommonsMultipartFile thumbnail) {
		//获取图片目录的相对值路径
				String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
				String productImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
				product.setImgAddr(productImgAddr);
	}

	/**
	 * 查询返回product
	 */

	@Override
	public Product queryProduct(Long productId) throws Exception {
		return productDao.queryProductById(productId);
	}

	/**
	 * 交托给事务管理优先处理之前的图片
	 * 1.若存在原先的缩略图则删除之前的再添加新图，之后获取缩略图相对路径并赋值给product
	 * 2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
	 * 3.将tb_product_img下面的该商品原先的商品详情图记录全部清空
	 * 4.更新tb_product的信息
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgs) throws ProductOperationException {
		//空值判断
		if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//给商品设置默认属性
			product.setLastEditTime(new Date());
			//若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if(thumbnail != null) {
				//先获取一遍原有信息，因为原来的信息里有图片地址
				Product temProduct = null;
				try {
					temProduct = productDao.queryProductById(product.getProductId());
				} catch (Exception e) {
					throw new ProductOperationException("查询原有商品信息失败");
				}
				
				if(temProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(temProduct.getImgAddr());
				}
				addThunbnail(product, thumbnail);
			}
			//如果有新存入的商品详情图，则将原先的删除，并添加新的图片
			if(productImgs != null && productImgs.size() > 0) {
				deletProductImgList(product.getProductId());
				addProoductImgList(product, productImgs);
			}
			try {
				//更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if(effectedNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			}catch (Exception e) {
				throw new ProductOperationException("更新商品信息失败"+e.toString());
			}
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}


	private void deletProductImgList(Long productId) {
		//获取原来的图片
		List<ProductImg> queryProductImgList = productImgDao.queryProductImgList(productId);
		//删除图片
		for(ProductImg productImg : queryProductImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		//删除数据库中的信息
		productDao.deleteProductImgByProductId(productId);
	}


	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize){
		//页码转换为数据库页码
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		//同样条件下查询商品总数
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}


	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductByProductId(productId);
	}

}
