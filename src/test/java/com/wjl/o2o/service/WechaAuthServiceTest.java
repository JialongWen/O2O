package com.wjl.o2o.service;

import com.wjl.o2o.dto.WechatAuthExecution;
import com.wjl.o2o.entity.PersonInfo;
import com.wjl.o2o.entity.WechatAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechaAuthServiceTest {
    @Autowired
    private WechatAuthService wechatAuthService;

    @Test
    public void testRegister(){
        WechatAuth wechatAuth =new WechatAuth();
        PersonInfo personInfo =new PersonInfo();
        String openId = "dafahsitsgww";
        //给微信设置上用户信息，但是不设置用户id
        //希望创建微信账号的时候自动创建用户信息
        personInfo.setCreateTime(new Date());
        personInfo.setName("测试一下");
        personInfo.setUserType(1);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setOpenId(openId);
        wechatAuth.setCreateTime(new Date());
        WechatAuthExecution wae = wechatAuthService.register(wechatAuth,null);
        WechatAuth wechatAuthByOpenId = wechatAuthService.getWechatAuthByOpenId(openId);
        System.out.println(wechatAuthByOpenId.getPersonInfo().getName());
    }

}
