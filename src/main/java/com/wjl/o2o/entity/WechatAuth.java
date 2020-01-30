package com.wjl.o2o.entity;

import java.util.Date;

public class WechatAuth {
	//微信id
	private Long wechatAuthId;
	//开放id
	private String openId;
	//创建时间
	private Date createTime;
	//实体类
	private PersonInfo personInfo;

	public WechatAuth(){

	}

	public WechatAuth(String openId, PersonInfo user, Date time) {
		this.openId = openId;
		this.personInfo = user;
		this.createTime = time;
	}

	public Long getWechatAuthId() {
		return wechatAuthId;
	}
	public void setWechatAuthId(Long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	
}
