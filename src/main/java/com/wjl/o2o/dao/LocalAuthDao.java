package com.wjl.o2o.dao;

import java.util.Date;

import com.wjl.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

public interface LocalAuthDao {
	/**
	 * 通过账号合密码查询对应的信息，登陆用
	 * @param username
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);
	/**
	 * 通过id查询LocalAuth
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalByUserId(@Param("userId") long userId);
	
	/**
	 * 添加平台账号
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	/**
	 * 通过userId,username,password更改密码
	 * @param userId
	 * @param username
	 * @param password
	 * @param newPassword
	 * @param lastEdiTime
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username, @Param("password") String password, @Param("newPassword") String newPassword, @Param("lastEditTime") Date lastEdiTime);
}
