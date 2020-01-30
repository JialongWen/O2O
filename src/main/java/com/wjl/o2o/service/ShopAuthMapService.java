package com.wjl.o2o.service;

import com.wjl.o2o.dto.ShopAuthMapExecution;
import com.wjl.o2o.entity.ShopAuthMap;
import com.wjl.o2o.exception.ShopAuthMapOperationException;

public interface ShopAuthMapService {

    /**
     * 查列表
     * @param shopId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize);

    /**
     * 查单个
     * @param shopAuthId
     * @return
     */
    ShopAuthMap getShopAuthMapById(Long shopAuthId);

    /**
     * 增
     * @param shopAuthMap
     * @return
     * @throws ShopAuthMapOperationException
     */
    ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap)throws ShopAuthMapOperationException;

    /**
     * 改 职位 状态等
     * @param shopAuthMap
     * @return
     * @throws ShopAuthMapOperationException
     */
    ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap)throws ShopAuthMapOperationException;

}
