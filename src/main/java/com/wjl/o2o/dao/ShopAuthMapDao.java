package com.wjl.o2o.dao;

import com.wjl.o2o.entity.ShopAuthMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopAuthMapDao {
    /**
     * 根据店铺信息返回分页查询结果
     * @param shopId
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<ShopAuthMap> queryShopAuthMapListByShopId(@Param("shopId")long shopId, @Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 返回数量
     * @param shopId
     * @return
     */
    int queryShopAuthCountByShopId(@Param("shopId")long shopId);

    /**
     * 新增一跳店铺信息
     * @param shopAuthMap
     * @return
     */
    int insertShopAuthMap(ShopAuthMap shopAuthMap);

    /**
     * 更新授权信息
     * @param shopAuthMap
     * @return
     */
    int updateShopAuthMap(ShopAuthMap shopAuthMap);

    /**
     * 对某员工除权
     * @param shopAuthId
     * @return
     */
    int deleteShopAuthMap(long shopAuthId);

    /**
     * 通过shopAuthId查询员工授权信息
     * @param shopAuthId
     * @return
     */
    ShopAuthMap queryShopAuthMapById(Long shopAuthId);
}
