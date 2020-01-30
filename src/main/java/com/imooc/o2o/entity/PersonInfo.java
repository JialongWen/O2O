package com.imooc.o2o.entity;

import java.util.Date;

public class PersonInfo {
	//用户id
	private Long userId;
	//用户姓名
	private String name;
	//用户头像地址
	private String profileImg;
	//客户标识
	private Integer customerFlag;
	//邮箱
	private String email;
	//性别
	private String gender;
	//微信的唯一标识
	private String openId;
	//状态
	private Integer enableStatus;
	//身份标识 1.顾客 2.店家 3.超级管理员
	private Integer userType;
	//管理标识
	private Integer adminFlag;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	public Long getUserId() {
		return userId;
	}
	
	
	

	public String getOpenId() {
		return openId;
	}




	public void setOpenId(String openId) {
		this.openId = openId;
	}




	public void setUserId(Long userID) {
		this.userId = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
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
	public Integer getCustomerFlag() {
		return customerFlag;
	}
	public void setCustomerFlag(Integer customerFlag) {
		this.customerFlag = customerFlag;
	}
	public Integer getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(Integer adminFlag) {
		this.adminFlag = adminFlag;
	}




	@Override
	public String toString() {
		return "PersonInfo [userId=" + userId + ", name=" + name + ", profileImg=" + profileImg + ", customerFlag="
				+ customerFlag + ", email=" + email + ", gender=" + gender + ", openId=" + openId + ", enableStatus="
				+ enableStatus + ", userType=" + userType + ", adminFlag=" + adminFlag + ", createTime=" + createTime
				+ ", lastEditTime=" + lastEditTime + "]";
	}

	
	
}
