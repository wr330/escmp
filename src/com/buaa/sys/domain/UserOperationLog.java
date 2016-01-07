package com.buaa.sys.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserOperationLog")
public class UserOperationLog implements Serializable{
	private static final long serialVersionUID = 1L;

	public UserOperationLog() {
	}

	public UserOperationLog(String oid) {
		this.oid = oid;
	}

	private String oid;
	private int logOperationType;
	private String operationPerson;
	private String operationWorkType;
	private String operationContent;
	private String operationReason;
	private Date operationTime;

	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "LogOperationType")
	public int getLogOperationType() {
		return logOperationType;
	}

	public void setLogOperationType(int logOperationType) {
		this.logOperationType = logOperationType;
	}
	
	@Column(name = "OperationPerson")
	public String getOperationPerson() {
		return operationPerson;
	}

	public void setOperationPerson(String operationPerson) {
		this.operationPerson = operationPerson;
	}
	
	@Column(name = "OperationWorkType")
	public String getOperationWorkType() {
		return operationWorkType;
	}

	public void setOperationWorkType(String operationWorkType) {
		this.operationWorkType = operationWorkType;
	}
	
	@Column(name = "OperationContent")
	public String getOperationContent() {
		return operationContent;
	}
	
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	
	@Column(name = "OperationReason")
	public String getOperationReason() {
		return operationReason;
	}

	public void setOperationReason(String operationReason) {
		this.operationReason = operationReason;
	}

	@Column(name = "OperationTime")
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
}
