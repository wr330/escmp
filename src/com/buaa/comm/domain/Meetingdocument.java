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
@Table(name="COMM_MEETINGDOCUMENT")
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
	private String filingpersonOid ;
	private String efile ;
	private String address ;
	private Date filingdate ;
	private Date meetingtime ;
	private String category ;
	private String filename ;
	private String filenumber ;
	private int miji ;


	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="HOLDDEPARTMENT_")
	public String getHolddepartment() {
		return holddepartment;
	}
	public void setHolddepartment(String holddepartment) {
		this.holddepartment=holddepartment;
	}
	@Column(name="AIRCRAFTTYPE_")
	public String getAircrafttype() {
		return aircrafttype;
	}
	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype=aircrafttype;
	}
	@Column(name="NAME_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="FILINGPERSON_")
	public String getFilingperson() {
		return filingperson;
	}
	public void setFilingperson(String filingperson) {
		this.filingperson=filingperson;
	}
	@Column(name="FILINGPERSONOID_")
	public String getFilingpersonOid() {
		return filingpersonOid;
	}
	public void setFilingpersonOid(String filingpersonOid) {
		this.filingpersonOid = filingpersonOid;
	}
	@Column(name="EFILE_")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile=efile;
	}
	@Column(name="ADDRESS_")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	@Column(name="FILINGDATE_")
	public Date getFilingdate() {
		return filingdate;
	}
	public void setFilingdate(Date filingdate) {
		this.filingdate=filingdate;
	}
	@Column(name="MEETINGTIME_")
	public Date getMeetingtime() {
		return meetingtime;
	}
	public void setMeetingtime(Date meetingtime) {
		this.meetingtime=meetingtime;
	}
	@Column(name="CATEGORY_")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category=category;
	}
	@Column(name="FILENAME_")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename=filename;
	}
	@Column(name="FILENUMBER_")
	public String getFilenumber() {
		return filenumber;
	}
	public void setFilenumber(String filenumber) {
		this.filenumber=filenumber;
	}
	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}


}