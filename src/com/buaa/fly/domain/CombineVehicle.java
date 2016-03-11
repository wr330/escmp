package com.buaa.fly.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerators;

@Entity
@Table(name = "Fly_CombineVehicle")
public class CombineVehicle {
	private static final long serialVersionUID = 1L;

	public CombineVehicle() {

	}

	private String oid;
	private Outlineexecution outlineExecution;
	private String combineSubject;
	private int combineNumber;

	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "CombineSubject")
	public String getCombineSubject() {
		return combineSubject;
	}

	public void setCombineSubject(String combineSubject) {
		this.combineSubject = combineSubject;
	}

	@Column(name = "CombineNumber")
	public int getCombineNumber() {
		return combineNumber;
	}

	public void setCombineNumber(int combineNumber) {
		this.combineNumber = combineNumber;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "OutlineExecution")
	public Outlineexecution getOutlineExecution() {
		return outlineExecution;
	}

	public void setOutlineExecution(Outlineexecution outlineExecution) {
		this.outlineExecution = outlineExecution;
	}

}
