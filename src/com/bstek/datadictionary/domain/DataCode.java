package com.bstek.datadictionary.domain;

import java.io.Serializable;
import java.util.Date;

public class DataCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dcId;
	private String dataName;
	private String dataCode;
	private String dataSimpleName;
	private Integer dataOrderNo;
	private String dataParentId;
	private String bakOne;
	private String bakTwo;
	private Date createTime;
	private String createUsername;
	private Date updateTime;
	private String updateUsername;
	
	public String getDcId() {
		return dcId;
	}
	public void setDcId(String dcId) {
		this.dcId = dcId;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getDataSimpleName() {
		return dataSimpleName;
	}
	public void setDataSimpleName(String dataSimpleName) {
		this.dataSimpleName = dataSimpleName;
	}
	public Integer getDataOrderNo() {
		return dataOrderNo;
	}
	public void setDataOrderNo(Integer dataOrderNo) {
		this.dataOrderNo = dataOrderNo;
	}
	public String getDataParentId() {
		return dataParentId;
	}
	public void setDataParentId(String dataParentId) {
		this.dataParentId = dataParentId;
	}
	public String getBakOne() {
		return bakOne;
	}
	public void setBakOne(String bakOne) {
		this.bakOne = bakOne;
	}
	public String getBakTwo() {
		return bakTwo;
	}
	public void setBakTwo(String bakTwo) {
		this.bakTwo = bakTwo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUsername() {
		return updateUsername;
	}
	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}
}
