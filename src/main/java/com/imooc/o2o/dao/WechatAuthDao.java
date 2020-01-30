package com.imooc.o2o.dao;

import com.imooc.o2o.entity.WechatAuth;

public interface WechatAuthDao {
	int insertWechatAuth(WechatAuth wechatAuth);
	WechatAuth queryWechatInfoByOpenId(String openId);
}
