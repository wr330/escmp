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
@Table(name="COMM_BTREPORTSHAREPERSON")
public class BtReportSharePerson implements Serializable {
	private static final long serialVersionUID = 1L;
    public BtReportSharePerson(){}
    public BtReportSharePerson(String oid) {
    	this.oid=oid;
 	}
    
    private String oid ;
	private String personName ;
	private String personDepartment;
	private String userName ;
	private int readStatus ;
	private Btreport btreport ;
	private Date firstReadTime ;
	private Date latelyReadTime;
	
	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	@Column(name="PERSONNAME_")
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	@Column(name="PERSONDEPARTMENT_")
	public String getPersonDepartment() {
		return personDepartment;
	}
	public void setPersonDepartment(String personDepartment) {
		this.personDepartment = personDepartment;
	}
	
	@Column(name="USERNAME_")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
		
	@Column(name="READSTATUS_")
	public int getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}
	
	@Column(name="FIRSTREADTIME_")
	public Date getFirstReadTime() {
		return firstReadTime;
	}
	public void setFirstReadTime(Date firstReadTime) {
		this.firstReadTime = firstReadTime;
	}
	
	@Column(name="LATELYREADTIME_")
	public Date getLatelyReadTime() {
		return latelyReadTime;
	}
	public void setLatelyReadTime(Date latelyReadTime) {
		this.latelyReadTime = latelyReadTime;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "BTREPORT_")
	public Btreport getBtreport() {
		return btreport;
	}
	public void setBtreport(Btreport btreport) {
		this.btreport = btreport;
	}
	
	
}
