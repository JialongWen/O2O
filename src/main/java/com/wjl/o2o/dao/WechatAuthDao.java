package com.wjl.o2o.dao;

import com.wjl.o2o.entity.WechatAuth;

public interface WechatAuthDao {
	int insertWechatAuth(WechatAuth wechatAuth);
	WechatAuth queryWechatInfoByOpenId(String openId);
}
