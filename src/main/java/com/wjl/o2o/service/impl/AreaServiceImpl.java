package com.wjl.o2o.service.impl;

import java.util.List;

import com.wjl.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wjl.o2o.cache.JedisUtil;
import com.wjl.o2o.dao.AreaDao;
import com.wjl.o2o.entity.Area;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;
	
	@Autowired
	private JedisUtil.Strings jedisString;
	
	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	
	@Override
	@Transactional
	public List<Area> getArea(){

		return areaDao.queryArea();
	}

}
