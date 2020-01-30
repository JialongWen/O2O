package com.wjl.o2o.service;

import java.util.List;

import com.wjl.o2o.entity.Area;

public interface AreaService {

	public static final String AREALISTKEY = "arealist";
	
	List<Area> getArea();
 }
