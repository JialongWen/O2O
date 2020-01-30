package com.imooc.o2o.entity;

import java.util.Date;

public class HeadLine {
	//ID
	private Long lineID;
	//头条名称
	private String lineName;
	//头条连接
	private String lineLink;
	//头条图片
	private String lineImg;
	//头条权重
	private Integer priority;
	//状态
	private Integer enableStatus;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	public Long getLineID() {
		return lineID;
	}
	public void setLineID(Long lineID) {
		this.lineID = lineID;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineLink() {
		return lineLink;
	}
	public void setLineLink(String lineLink) {
		this.lineLink = lineLink;
	}
	public String getLineImg() {
		return lineImg;
	}
	public void setLineImg(String lineImg) {
		this.lineImg = lineImg;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
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
	@Override
	public String toString() {
		return "HeadLine [lineID=" + lineID + ", lineName=" + lineName + ", lineLink=" + lineLink + ", lineImg="
				+ lineImg + ", priority=" + priority + ", enableStatus=" + enableStatus + ", createTime=" + createTime
				+ ", lastEditTime=" + lastEditTime + "]";
	}
	
	
}
