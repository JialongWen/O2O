package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import com.imooc.o2o.entity.Award;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.imooc.o2o.entity.UserAwardMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAwardMapDaoTest{
	@Autowired
	private UserAwardMapDao userAwardMapDao;

	@Test
	@Ignore
	public void testAInsertUserAwardMap() throws Exception {
		PersonInfo user = new PersonInfo();
		Award award = new Award();
		Shop shop =new Shop();
		shop.setShopId(1L);
		award.setAwardId(1L);
		award.setAwardName("第一个奖品");
	user.setUserId(1L);
		UserAwardMap userAwardMap = new UserAwardMap();
		userAwardMap.setAward(award);
		userAwardMap.setShop(shop);
		userAwardMap.setUser(user);
		userAwardMap.setUserId(1L);
		userAwardMap.setAwardId(1L);
		userAwardMap.setShopId(1L);
		userAwardMap.setUserName("test");
		userAwardMap.setAwardName("第一个奖品");
		userAwardMap.setCreateTime(new Date());
		userAwardMap.setUsedStatus(1);
		int effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
		assertEquals(1, effectedNum);
		userAwardMap.setUserId(2L);
		userAwardMap.setAwardId(1L);
		userAwardMap.setShopId(1L);
		userAwardMap.setUserName("test2");
		userAwardMap.setAwardName("第二个奖品");
		userAwardMap.setCreateTime(new Date());
		userAwardMap.setUsedStatus(0);
		effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryUserAwardMapList(){
		UserAwardMap userAwardMap = new UserAwardMap();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		Shop shop =new Shop();
		shop.setShopId(1L);
		userAwardMap.setShop(shop);
		userAwardMap.setUser(personInfo);
		try {
			List<UserAwardMap> userAwardMaps = userAwardMapDao.queryUserAwardMapList(userAwardMap, 0, 2);
			int i = userAwardMapDao.queryUserAwardMapCount(userAwardMap);
			System.out.println("查询到的条数为：" + i + "==查询到的类别为：" + userAwardMaps);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void testCQueryById(){
		UserAwardMap userAwardMap = userAwardMapDao.queryUserAwardMapById(1L);
		System.out.println(userAwardMap);
	}
}
