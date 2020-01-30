package com.imooc.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.o2o.dao.PersonInfoDao;
import com.imooc.o2o.dao.WechatAuthDao;
import com.imooc.o2o.dto.PersonInfoExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.PersonInfoStateEnum;
import com.imooc.o2o.service.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

	@Autowired
	private WechatAuthDao wechatAuthDao;
	
	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Override
	public PersonInfo queryPersonInfoByOpenId(String openId) {
		return personInfoDao.queryPersonInfoByOpenId(openId);
	}

	@Override
	public PersonInfoExecution addPersonInfo(PersonInfo user) {
		//非空判断 状态，用户类型，openId不可为空
		if(user.getEnableStatus() != null && user.getUserType() != null && user.getOpenId() != null) {
			//如果不为空那么就添加
			int effectedNum = personInfoDao.insertPersonInfo(user);
			//添加成功之后创建一个关系类实例添加关系表
			if(effectedNum > 0) {
				return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS);
			}else {
				return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
			}
		}else {
			return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
		}
	}

	@Override
	public PersonInfo getPersonInfoById(long userId) {
		return personInfoDao.queryPersonInfoById(userId);
	}

}
