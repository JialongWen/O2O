package com.wjl.o2o.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.wjl.o2o.entity.Area;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest {
	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;
	
	@Test
	public void testGetAreaList() {
		List<Area> areaList = areaService.getArea();
		System.out.println(areaList);
		cacheService.removeFromCache(areaService.AREALISTKEY);
		areaList = areaService.getArea();
	}
	
}
