package com.wjl.o2o.dao;

import java.util.List;

import com.wjl.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

public interface HeadLineDao {
	/**
	 * 根据传入的查询条件（头条名查询头条）
	 * @param headLineCondition
	 * @return
	 */
	List<HeadLine> queryHeadLinde(@Param("headLineCondition") HeadLine headLineCondition);
	
	/**
	 * 根据头条Id返回唯一的头条信息
	 */
	HeadLine queryHeadLineById(long lineId);
}
