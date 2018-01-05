package com.buaa.out.domain;

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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@XmlRootElement(name="OUT_TECHNICALDOCUMENT",namespace="http://www.bstek.com/ws")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="OUT_TECHNICALDOCUMENT")
public class  Technicaldocument implements Serializable {
	private static final long serialVersionUID = 1L;
    public Technicaldocument(){}
   public Technicaldocument(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String efile ;
	private String execution ;
	private Date issuedate ;
	private String documenttype ;
	private String number ;
	private String responsibleperson ;
	private String rpersonDepartment ;
	private String rpersonSection ;
	private String title ;
	private String major ;
	private String aircrafttype;
	private String contentabstract;
	private String contenttype;
	private String transceivertype;
	private String recipient;
	private byte[] datablock ;
	private Integer bytes ;
	private int miji ;
	private int sendStatus;
	private String creatPerson;
	private String creatPersonOid;
	

	
	@Id
		@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="EFILE_")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile=efile;
	}
	@Column(name="EXECUTION_")
	public String getExecution() {
		return execution;
	}
	public void setExecution(String execution) {
		this.execution=execution;
	}
	@Column(name="ISSUEDATE_")
	public Date getIssuedate() {
		return issuedate;
	}
	public void setIssuedate(Date issuedate) {
		this.issuedate=issuedate;
	}
	@Column(name="DOCUMENTTYPE_")
	public String getDocumenttype() {
		return documenttype;
	}
	public void setDocumenttype(String documenttype) {
		this.documenttype=documenttype;
	}
	@Column(name="TDNUMBER_")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number=number;
	}
	@Column(name="RESPONSIBLEPERSON_")
	public String getResponsibleperson() {
		return responsibleperson;
	}
	public void setResponsibleperson(String responsibleperson) {
		this.responsibleperson=responsibleperson;
	}
	@Column(name="RPERSONDEPARTMENT_")
	public String getRpersonDepartment() {
		return rpersonDepartment;
	}
	public void setRpersonDepartment(String rpersonDepartment) {
		this.rpersonDepartment = rpersonDepartment;
	}
	@Column(name="RPERSONSECTION_")
	public String getRpersonSection() {
		return rpersonSection;
	}
	public void setRpersonSection(String rpersonSection) {
		this.rpersonSection = rpersonSection;
	}
	@Column(name="TITLE_")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	@Column(name="MAJOR_")
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major=major;
	}
	@Column(name="AIRCRAFTTYPE_")
	public String getAircrafttype() {
		return aircrafttype;
	}
	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype=aircrafttype;
	}
	@Column(name="ABSTRACT_")
	public String getContentabstract() {
		return contentabstract;
	}
	public void setContentabstract(String contentabstract) {
		this.contentabstract=contentabstract;
	}
	@Column(name="CONTENTTYPE_")
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype=contenttype;
	}
	@Column(name="TRANSCEIVERTYPE_")
	public String getTransceivertype() {
		return transceivertype;
	}
	public void setTransceivertype(String transceivertype) {
		this.transceivertype=transceivertype;
	}
	@Column(name="RECIPIENT_")
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient=recipient;
	}
	@Column(name="DATABLOCK_")
	public byte[] getDatablock() {
		return datablock;
	}
	public void setDatablock(byte[] datablock) {
		this.datablock=datablock;
	}
	@Column(name="BYTES_")
	public Integer getBytes() {
		return bytes;
	}
	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}
	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}
	@Column(name="SENDSTATUS_")
	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	@Column(name="CREATPERSONOID_")
	public String getCreatPersonOid() {
		return creatPersonOid;
	}
	public void setCreatPersonOid(String creatPersonOid) {
		this.creatPersonOid = creatPersonOid;
	}
	@Column(name="CREATPERSON_")
	public String getCreatPerson() {
		return creatPerson;
	}
	public void setCreatPerson(String creatPerson) {
		this.creatPerson = creatPerson;
	}
}