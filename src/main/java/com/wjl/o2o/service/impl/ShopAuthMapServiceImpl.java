package com.wjl.o2o.service.impl;

import com.wjl.o2o.dao.ShopAuthMapDao;
import com.wjl.o2o.dto.ShopAuthMapExecution;
import com.wjl.o2o.entity.ShopAuthMap;
import com.wjl.o2o.enums.ShopAuthMapStateEnum;
import com.wjl.o2o.exception.ShopAuthMapOperationException;
import com.wjl.o2o.service.ShopAuthMapService;
import com.wjl.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Override
    public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
    //空值判断
        if(shopId != null && pageIndex != null && pageSize != null){
            //页转行
            int beginIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
            //查询并返回该店铺的授权列表
            List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId, beginIndex, pageSize);
            //返回总数
            int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
            ShopAuthMapExecution se = new ShopAuthMapExecution();
            se.setShopAuthMapList(shopAuthMapList);
            se.setCount(count);
            return  se;
        }else {
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
        }
    }

    @Override
    public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
        return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
    }

    @Override
    @Transactional
    public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        //空值判断
        if(shopAuthMap != null && shopAuthMap.getShop() != null && shopAuthMap.getShop().getShopId() != null
                && shopAuthMap.getEmployee() !=null && shopAuthMap.getEmployee().getUserId() != null){
        //添加初始信息
            shopAuthMap.setCreateTime(new Date());
            shopAuthMap.setLastEditTime(new Date());
            shopAuthMap.setEnableStatus(1);
            shopAuthMap.setTitleFlag(0);
            try{
                //添加授权信息
                int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                //判断是否添加成功
                if(effectedNum <= 0){
                    throw new ShopAuthMapOperationException("添加授权失败");
                }
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
            }catch (Exception e){
                throw new ShopAuthMapOperationException("添加授权失败"+e.toString());
            }
        }else {
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
        }
    }

    @Override
    @Transactional
    public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        //空值判断
        if(shopAuthMap == null || shopAuthMap.getShopAuthId() == null){
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
        }else {
            try{
                int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
                if(effectedNum <= 0){
                    return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
                }else {
                    return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ShopAuthMapOperationException("modifyShopAuthMap error:"+e.getMessage());
            }
        }
    }
}
