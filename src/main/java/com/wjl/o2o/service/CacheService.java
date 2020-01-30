package com.wjl.o2o.service;

public interface CacheService {
	/**
	 * 依据key前缀删除匹配该模式下的所有key-value 如传入：shopcategory则shopcategory_allfirstlevel等
	 * 以shopcategory开头的key-value都会被清空
	 */
	void removeFromCache(String keyPrefix);
}
