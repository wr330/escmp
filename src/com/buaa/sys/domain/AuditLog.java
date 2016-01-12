package com.buaa.sys.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Sys_AuditLog")
public class AuditLog implements Serializable{

	private static final long serialVersionUID = 1L;

	public AuditLog() {
	}

	public AuditLog(String oid) {
		this.oid = oid;
	}

	private String oid;
	private String operationPerson;
	private Date operationTime;
	private String operationContent;
	private int operationType;

	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "OperationPerson")
	public String getOperationPerson() {
		return operationPerson;
	}

	public void setOperationPerson(String operationPerson) {
		this.operationPerson = operationPerson;
	}

	@Column(name = "OperationTime")
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	@Column(name = "OperationContent")
	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	@Column(name = "OperationType")
	public int getoperationType() {
		return operationType;
	}

	public void setoperationType(int operationType) {
		this.operationType = operationType;
	}

	
	
}
