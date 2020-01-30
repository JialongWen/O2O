package com.wjl.o2o.service;

import java.util.List;

import com.wjl.o2o.entity.HeadLine;

public interface HeadLineService {

	public static final String HLLISTKEY = "headlinelist";
	/**
	 * 返回相应查询的HeadLineList
	 * @param headLineCondition
	 * @return
	 * @throws Exception
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition)throws Exception;
	
}
