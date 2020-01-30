package com.wjl.o2o.dao;

import com.wjl.o2o.entity.PersonInfo;

public interface PersonInfoDao {
	/**
	 * 根据openid查询用户
	 * @param openId
	 * @return
	 */
	PersonInfo queryPersonInfoByOpenId(String openId);
	
	/**
	 * 添加一个用户
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);

	/**
	 * 根据id查询用户
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);
}
