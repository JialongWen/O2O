package com.wjl.o2o.dao;

import com.wjl.o2o.entity.PersonInfo;
import com.wjl.o2o.entity.Shop;
import com.wjl.o2o.entity.UserShopMap;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserShopMaoDaoTest {

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Test
    @Ignore
    public void testAInsertUsersShopMap(){
        PersonInfo user = new PersonInfo();
        user.setUserId(2L);
        Shop shop = new Shop();
        shop.setShopId(1L);
        UserShopMap userShopMap = new UserShopMap();
        userShopMap.setShop(shop);
        userShopMap.setUser(user);
        userShopMap.setCreateTime(new Date());
        userShopMap.setPoint(5);
        int effectedNum = userShopMapDao.insertUserShopMap(userShopMap);
        System.out.println(effectedNum);
    }

    @Test
    @Ignore
    public void testQueryUserShopMao(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        UserShopMap userShopMap = new UserShopMap();
        userShopMap.setShop(shop);
        userShopMapDao.queryUserShopMapList(userShopMap,0,3);
        userShopMapDao.queryUserShopMapCount(userShopMap);
    }

    @Test
    public void testUpdateUserShopMap(){
        UserShopMap userShopMap = new UserShopMap();
        Shop shop = new Shop();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        shop.setShopId(1L);
        userShopMap.setPoint(10);
        userShopMap.setUser(user);
        userShopMap.setShop(shop);
        userShopMapDao.updateUserShopMapPoint(userShopMap);
    }
}
