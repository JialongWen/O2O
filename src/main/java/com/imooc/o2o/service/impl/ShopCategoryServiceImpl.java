package com.imooc.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exception.AreaOperationException;
import com.imooc.o2o.exception.ShopCategoryOperationException;
import com.imooc.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{

	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);
	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition){
		/*String key = SCLISTKEY;
		//定义接受对象
		List<ShopCategory> shopCategoryList = null;
		//定义jackson数据转换操作
		ObjectMapper mapper = new ObjectMapper();
		//拼接出redis的key
		if(shopCategoryCondition == null) {
			//如果查询条件为空则列出所有首页大类,即parentId为空的店铺类别
			key = key + "_allfirstlevel";
		}else if(shopCategoryCondition != null && shopCategoryCondition.getParent()!= null && shopCategoryCondition.getParent().getShopCategoryId() != null){
			//若是parentId 不为空，就列出该parentId下的所有子类别
			key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
		}else if(shopCategoryCondition != null) {
			//列出所有的子类别，不管属于哪个类别，都列出来
			key = key + "_allsecondilevel";
		}
		
		//判断key是否存在
		if(!jedisKeys.exists(key)) {
			//若不存在，则从数据库里取出相应的数据
			shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			//将相关的实体类集合转换成String，存入redis里面对应的key中
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(shopCategoryList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw  new ShopCategoryOperationException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		}else {
			//若是存在，则直接从redis里面取出相应的数据
			String jsonString = jedisStrings.get(key);
			//指定要将string转换成的集合类型
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			//将相关的key对应的value里的string转换成对象的实体类集合
			try {
				shopCategoryList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw  new ShopCategoryOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw  new ShopCategoryOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw  new ShopCategoryOperationException(e.getMessage());
			}
		}*/
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

}
