package com.buaa.sys.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_USEROPERATIONLOG")
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
	private String operationPersonName;
	private String operationWorkType;
	private String operationContent;
	private String operationReason;
	private Date operationTime;

	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "LOGOPERATIONTYPE_")
	public int getLogOperationType() {
		return logOperationType;
	}

	public void setLogOperationType(int logOperationType) {
		this.logOperationType = logOperationType;
	}
	
	@Column(name = "OPERATIONPERSON_")
	public String getOperationPerson() {
		return operationPerson;
	}

	public void setOperationPerson(String operationPerson) {
		this.operationPerson = operationPerson;
	}
	
	@Column(name = "OPERATIONPERSONNAME_")
	public String getOperationPersonName() {
		return operationPersonName;
	}

	public void setOperationPersonName(String operationPersonName) {
		this.operationPersonName = operationPersonName;
	}

	@Column(name = "OPERATIONWORKTYPE_")
	public String getOperationWorkType() {
		return operationWorkType;
	}

	public void setOperationWorkType(String operationWorkType) {
		this.operationWorkType = operationWorkType;
	}
	
	@Column(name = "OPERATIONCONTENT_")
	public String getOperationContent() {
		return operationContent;
	}
	
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	
	@Column(name = "OPERATIONREASON_")
	public String getOperationReason() {
		return operationReason;
	}

	public void setOperationReason(String operationReason) {
		this.operationReason = operationReason;
	}

	@Column(name = "OPERATIONTIME_")
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
}
