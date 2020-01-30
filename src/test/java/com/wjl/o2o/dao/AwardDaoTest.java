package com.wjl.o2o.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.wjl.o2o.entity.Award;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardDaoTest {
    @Autowired
    private AwardDao awardDao;

    @Test
    @Ignore
    public void testAInsertAward(){
        long shopId =1;
        //创建奖品1
        Award award1 = new Award();
        award1.setAwardName("测试一");
        award1.setAwardImg("test1");
        award1.setPoint(5);
        award1.setPriority(1);
        award1.setEnableStatus(1);
        award1.setCreateTime(new Date());
        award1.setLastEditTime(new Date());
        award1.setShopId(shopId);
    try {
        int effectedNum = awardDao.insertAard(award1);
        System.out.println(effectedNum);
    }catch (Exception e){
        e.printStackTrace();
    }
        //创建奖品2
        Award award2 = new Award();
        award2.setAwardName("测试二");
        award2.setAwardImg("test2");
        award2.setPoint(5);
        award2.setPriority(1);
        award2.setEnableStatus(1);
        award2.setCreateTime(new Date());
        award2.setLastEditTime(new Date());
        award2.setShopId(shopId);
        try {
            int effectedNum2 = awardDao.insertAard(award2);
            System.out.println(effectedNum2);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    @Ignore
    public void testBQueryAwardList(){
        Award award = new Award();
        long shopId = 1L;
        award.setShopId(shopId);
        award.setEnableStatus(1);
        award.setAwardName("测试");

        List<Award> awardList = awardDao.queryAwardList(award, 0, 2);
        int count = awardDao.queryAwardCount(award);
        System.out.println("count:"+count+"awardList:"+awardList);

    }

    @Test
    @Ignore
    public void testCUpdateAward(){
        long shopId =1;
        //创建奖品1
        Award award1 = new Award();
        award1.setAwardId(1L);
        award1.setAwardName("测试一新");
        award1.setAwardImg("test1new");
        award1.setPoint(3);
        award1.setPriority(1);
        award1.setEnableStatus(1);
        award1.setCreateTime(new Date());
        award1.setLastEditTime(new Date());
        award1.setShopId(shopId);

        int effectedNum = awardDao.updateAward(award1);
        System.out.println(effectedNum+"effectedNum");
    }

    @Test
    @Ignore
    public void testDDelectAward(){
        long shopId =1L;
        long awardId =2L;
        awardDao.deleteAward(awardId,shopId);
    }
}
