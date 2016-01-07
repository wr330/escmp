package com.buaa.comm.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name="Comm_MeetingDocument")
public class  Meetingdocument implements Serializable {
	private static final long serialVersionUID = 1L;
    public Meetingdocument(){}
   public Meetingdocument(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String holddepartment ;
	private String aircrafttype ;
	private String name ;
	private String filingperson ;
	private String efile ;
	private String address ;
	private Date filingdate ;
	private Date meetingtime ;
	private String category ;
	private String filename ;
	private String filenumber ;
	private String miji ;


	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="HoldDepartment")
	public String getHolddepartment() {
		return holddepartment;
	}
	public void setHolddepartment(String holddepartment) {
		this.holddepartment=holddepartment;
	}
	@Column(name="AircraftType")
	public String getAircrafttype() {
		return aircrafttype;
	}
	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype=aircrafttype;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="FilingPerson")
	public String getFilingperson() {
		return filingperson;
	}
	public void setFilingperson(String filingperson) {
		this.filingperson=filingperson;
	}
	@Column(name="EFile")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile=efile;
	}
	@Column(name="Address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	@Column(name="FilingDate")
	public Date getFilingdate() {
		return filingdate;
	}
	public void setFilingdate(Date filingdate) {
		this.filingdate=filingdate;
	}
	@Column(name="MeetingTime")
	public Date getMeetingtime() {
		return meetingtime;
	}
	public void setMeetingtime(Date meetingtime) {
		this.meetingtime=meetingtime;
	}
	@Column(name="Category")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category=category;
	}
	@Column(name="FileName")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename=filename;
	}
	@Column(name="FileNumber")
	public String getFilenumber() {
		return filenumber;
	}
	public void setFilenumber(String filenumber) {
		this.filenumber=filenumber;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}


}