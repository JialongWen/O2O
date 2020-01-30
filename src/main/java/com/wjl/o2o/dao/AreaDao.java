package com.wjl.o2o.dao;

import java.util.List;

import com.wjl.o2o.entity.Area;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AreaDao {
	/**
	 * 列出区域列表
	 * @return areaList
	 */
	List<Area> queryArea();
}
