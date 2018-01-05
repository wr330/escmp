package com.buaa.out.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="OUT_PERSONSTATISTIC")
public class  Personstatistic implements Serializable {
	private static final long serialVersionUID = 1L;
    public Personstatistic(){}

	private String workaddress ;
	private Integer days ;
	private String registrationexecutor ;
	@Id
    private PersonstatisticKey personSKey ;
	
	@Id
	@Column(name="WORKADDRESS_")
	public String getWorkaddress() {
		return workaddress;
	}
	public void setWorkaddress(String workaddress) {
		this.workaddress=workaddress;
	}
	@Column(name="DAYS_")
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days=days;
	}
	@Id
	@Column(name="REGISTRATIONEXECUTOR_")
	public String getRegistrationexecutor() {
		return registrationexecutor;
	}
	public void setRegistrationexecutor(String registrationexecutor) {
		this.registrationexecutor=registrationexecutor;
	}


}