package com.wjl.o2o.service.impl;

import java.io.IOException;
import java.util.List;

import com.wjl.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wjl.o2o.cache.JedisUtil;
import com.wjl.o2o.dao.HeadLineDao;
import com.wjl.o2o.entity.HeadLine;

@Service
public class HeadLineServiceImpl implements HeadLineService {
	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private HeadLineDao headLineDao;

	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition)
			throws IOException {
		return headLineDao.queryHeadLinde(headLineCondition);
	}


}
