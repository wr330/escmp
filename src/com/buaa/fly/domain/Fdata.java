package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Fly_Data")
public class Fdata implements Serializable {
	private static final long serialVersionUID = 1L;
	public Fdata(){
		
	}
	private String outFactoryNo;
	private Integer landingTimes;
	private String dataType;
	private String special;
	private String dataPath;
	private String dataLabel;
	private Tasklist taskList;
	private String oid;
	private Date flightdate ;
	
	
	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	@Column(name="outFactoryNo")
	public String getOutFactoryNo() {
		return outFactoryNo;
	}
	public void setOutFactoryNo(String outFactoryNo) {
		this.outFactoryNo = outFactoryNo;
	}
	
	@Column(name="LandingTimes")
	public int getLandingTimes() {
		return landingTimes;
	}
	public void setLandingTimes(int landingTimes) {
		this.landingTimes = landingTimes;
	}
	
	@Column(name="DataType")
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column(name="Special")
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	
	@Column(name="DataPath")
	public String getDataPath() {
		return dataPath;
	}
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	
	@Column(name="DataLabel")
	public String getDataLabel() {
		return dataLabel;
	}
	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "Tasklist")
	public Tasklist getTaskList() {
		return taskList;
	}
	public void setTaskList(Tasklist taskList) {
		this.taskList = taskList;
	}
	
	@Column(name="Flightdate")
	public Date getFlightdate() {
		return flightdate;
	}
	public void setFlightdate(Date flightdate) {
		this.flightdate = flightdate;
	}

	
	
}
