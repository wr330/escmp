package com.buaa.out.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name="Out_PersonStatistic")
public class  Personstatistic implements Serializable {
	private static final long serialVersionUID = 1L;
    public Personstatistic(){}

	private String itemoid ;
	private String oid ;
	private Date endexecutiontime ;
	private String workaddress ;
	private String troop ;
	private Integer days ;
	private Date startexecutiontime ;
	private String registrationexecutor ;




	@Column(name="ItemOid")
	public String getItemoid() {
		return itemoid;
	}
	public void setItemoid(String itemoid) {
		this.itemoid=itemoid;
	}
	@Id
	@Column(name="Oid")
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}
	@Column(name="EndExecutionTime")
	public Date getEndexecutiontime() {
		return endexecutiontime;
	}
	public void setEndexecutiontime(Date endexecutiontime) {
		this.endexecutiontime=endexecutiontime;
	}
	@Column(name="WorkAddress")
	public String getWorkaddress() {
		return workaddress;
	}
	public void setWorkaddress(String workaddress) {
		this.workaddress=workaddress;
	}
	@Column(name="Troop")
	public String getTroop() {
		return troop;
	}
	public void setTroop(String troop) {
		this.troop=troop;
	}
	@Column(name="Days")
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days=days;
	}
	@Column(name="StartExecutionTime")
	public Date getStartexecutiontime() {
		return startexecutiontime;
	}
	public void setStartexecutiontime(Date startexecutiontime) {
		this.startexecutiontime=startexecutiontime;
	}
	@Column(name="RegistrationExecutor")
	public String getRegistrationexecutor() {
		return registrationexecutor;
	}
	public void setRegistrationexecutor(String registrationexecutor) {
		this.registrationexecutor=registrationexecutor;
	}


}