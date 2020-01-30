package com.wjl.o2o.dao;

import java.util.List;

import com.wjl.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

public interface ShopDao {
	
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	
	/**
	 * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域ID，owner
	 * @param shopCondition
	 * @param rowIndex 从第几行开始取数据
	 * @param pageSize 返回的条数
	 * @return
	 */
	
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize) throws Exception;
	
	/**
	 * 通过shopId查询店铺信息
	 * @param shopId
	 * @return shop
	 * */
	Shop queryByShopId(long shopId);
	/**
	 * 新增店铺
	 * @param shop
	 * @return 1.新增成功
	 * */
	int insertShop(Shop shop);
	/**
	 * 更新店铺
	 * @param shop
	 * @return 1.修改成功
	 * */
	int updateShop(Shop shop);
}
