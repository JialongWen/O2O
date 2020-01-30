package com.wjl.o2o.service;

import com.wjl.o2o.dto.PersonInfoExecution;
import com.wjl.o2o.entity.PersonInfo;

public interface PersonInfoService {
	PersonInfo queryPersonInfoByOpenId(String openId);
	PersonInfoExecution addPersonInfo(PersonInfo personInfo);
	PersonInfo getPersonInfoById(long userId);
}
