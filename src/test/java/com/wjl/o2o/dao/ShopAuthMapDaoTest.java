package com.wjl.o2o.dao;

import com.wjl.o2o.entity.PersonInfo;
import com.wjl.o2o.entity.Shop;
import com.wjl.o2o.entity.ShopAuthMap;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopAuthMapDaoTest {
    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    //测试添加
    @Test
    @Ignore
    public void testInsertShopAuthMap(){
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        PersonInfo employee = new PersonInfo();
        employee.setUserId(1L);
        Shop shop = new Shop();
        shop.setShopId(1L);
        shopAuthMap.setEmployee(employee);
        shopAuthMap.setShop(shop);
        shopAuthMap.setTitle("老板娘");
        shopAuthMap.setTitleFlag(1);
        shopAuthMap.setEnableStatus(1);
        shopAuthMap.setCreateTime(new Date());
        shopAuthMap.setLastEditTime(new Date());
        shopAuthMapDao.insertShopAuthMap(shopAuthMap);
    }

    //测试查询List
    @Test
    @Ignore
    public void testQueryShopAuthMapList(){
        shopAuthMapDao.queryShopAuthMapListByShopId(1L,0,4);
        int effrectedNum = shopAuthMapDao.queryShopAuthCountByShopId(1L);
        System.out.println(effrectedNum+",is effrectedNum");
    }
    //测试更新
    @Test
    @Ignore
    public void testUpdateShopAuthMap(){
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        PersonInfo employee = new PersonInfo();
        employee.setUserId(1L);
        Shop shop = new Shop();
        shop.setShopId(1L);
        shopAuthMap.setShopAuthId(2L);
        shopAuthMap.setEmployee(employee);
        shopAuthMap.setShop(shop);
        shopAuthMap.setTitle("股东一");
        shopAuthMap.setTitleFlag(1);
        shopAuthMap.setEnableStatus(1);
        shopAuthMap.setLastEditTime(new Date());
        int i = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
        System.out.println("影响行数："+i);
    }
    //测试删除
    @Test
    @Ignore
    public void testDelete(){
        int i = shopAuthMapDao.deleteShopAuthMap(2L);
        System.out.println("影响行数:"+i);
    }
}
