package com.buaa.comm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name="COMM_MEETINGMANAGER")
public class  Meetingmanager implements Serializable {
	private static final long serialVersionUID = 1L;
    public Meetingmanager(){}
    public Meetingmanager(String oid) {
      this.oid=oid;
 	}	

	private String oid ;
	private int miji ;
	private Date meetingstime ;
	private Date meetingetime ;
	private String applypersonname ;
	private String applyperson ;
	private String meetingaddress ;


	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="Miji")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji=miji;
	}
	@Column(name="MEETINGSTIME_")
	public Date getMeetingstime() {
		return meetingstime;
	}
	public void setMeetingstime(Date meetingstime) {
		this.meetingstime=meetingstime;
	}
	@Column(name="MEETINGETIME_")
	public Date getMeetingetime() {
		return meetingetime;
	}
	public void setMeetingetime(Date meetingetime) {
		this.meetingetime=meetingetime;
	}
	@Column(name="APPLYPERSONNAME_")
	public String getApplypersonname() {
		return applypersonname;
	}
	public void setApplypersonname(String applypersonname) {
		this.applypersonname=applypersonname;
	}
	@Column(name="APPLYPERSON_")
	public String getApplyperson() {
		return applyperson;
	}
	public void setApplyperson(String applyperson) {
		this.applyperson=applyperson;
	}
	@Column(name="MEETINGADDRESS_")
	public String getMeetingaddress() {
		return meetingaddress;
	}
	public void setMeetingaddress(String meetingaddress) {
		this.meetingaddress=meetingaddress;
	}


}