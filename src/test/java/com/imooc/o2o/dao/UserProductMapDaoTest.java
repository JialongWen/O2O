package com.imooc.o2o.dao;

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserProductMap;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProductMapDaoTest {

    @Autowired
    private UserProductMapDao userProductMapDao;

    @Test
    @Ignore
    public void testInsertUserProductMap(){
        UserProductMap userProductMap = new UserProductMap();
        Shop shop = new Shop();
        shop.setShopId(1L);
        Product prodcut = new Product();
        prodcut.setProductId(1L);
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        PersonInfo operator = new PersonInfo();
        operator.setUserId(2L);
        userProductMap.setCreateTime(new Date());
        userProductMap.setShop(shop);
        userProductMap.setProduct(prodcut);
        userProductMap.setUser(user);
        userProductMap.setOperator(operator);
        userProductMap.setPoint(5);
        int effectedNum = userProductMapDao.insertUserProductMap(userProductMap);
        System.out.println(effectedNum+",is effectedNum");
    }

    @Test
    public void testQueryUserProductList(){
        UserProductMap userProductMap = new UserProductMap();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        userProductMap.setUser(user);
        userProductMapDao.queryUserProductMapCount(userProductMap);
        userProductMapDao.queryUserProductMapList(userProductMap,0,5);

    }

}
