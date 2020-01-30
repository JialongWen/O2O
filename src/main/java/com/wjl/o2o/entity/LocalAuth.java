package com.wjl.o2o.entity;

import java.util.Date;

public class LocalAuth {
	//本地ID
	private Long localAuthId;
	//用户名
	private String username;
	//密码
	private String password;
	//用户id
	private Long userId;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	//实体类
	private PersonInfo personInfo;
	
	public Long getLocalAuthId() {
		return localAuthId;
	}
	public void setLocalAuthId(Long localAuthId) {
		this.localAuthId = localAuthId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "LocalAuth [localAuthId=" + localAuthId + ", username=" + username + ", password=" + password
				+ ", userId=" + userId + ", createTime=" + createTime + ", lastEditTime=" + lastEditTime
				+ ", personInfo=" + personInfo + "]";
	}
	
	
}
