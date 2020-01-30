package com.imooc.o2o.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopDao shopDao;
	
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg) {
		//空值判断
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//初始值赋值 审核状态 创建时间 修改时间
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//店铺店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if(effectedNum<=0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(shopImg!=null) {
					//存储图片
					try {
					addShopImg(shop,shopImg);
					}catch(Exception e) {
						e.printStackTrace();
						throw new ShopOperationException("addShopImg error:"+e.getMessage());
					}
					//更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum<=0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new ShopOperationException("addShop error"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}


	private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
		//获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
		shop.setShopImg(shopImgAddr);
	}


	@Override
	public Shop getByShopId(long shopId){
		return shopDao.queryByShopId(shopId);
	}


	@Override
	public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) throws ShopOperationException {
		if(shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			//判断图片是否要处理
			try {
			if(shopImg != null && shopImg.getOriginalFilename()!=null && !"".equals(shopImg.getOriginalFilename())) {
				Shop tempShop  = null;
				
					tempShop = shopDao.queryByShopId(shop.getShopId());
				
				if(tempShop.getShopImg() != null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop,shopImg);
			}
			//更新店铺信息
			shop.setLastEditTime(new Date());
			int effectedNu = shopDao.updateShop(shop);
			if(effectedNu<=0) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}else {
				shop = shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS);
			}} catch (Exception e) {
				e.printStackTrace();
				throw new ShopOperationException("modifyShop error",e);
			}
		}
	}


	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageindex, int pageSzie) throws Exception {
		int rowIndex = PageCalculator.calculateRowIndex(pageindex, pageSzie);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSzie);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if(shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
