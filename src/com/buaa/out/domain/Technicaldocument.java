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
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name="Out_TechnicalDocument")
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
	private String title ;
	private String major ;
	private String aircrafttype;
	private String contentabstract;
	private String contenttype;
	private String transceivertype;
	private String recipient;
	private byte[] datablock ;
	private Integer bytes ;
	private String miji ;

	
	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="EFile")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile=efile;
	}
	@Column(name="Execution")
	public String getExecution() {
		return execution;
	}
	public void setExecution(String execution) {
		this.execution=execution;
	}
	@Column(name="IssueDate")
	public Date getIssuedate() {
		return issuedate;
	}
	public void setIssuedate(Date issuedate) {
		this.issuedate=issuedate;
	}
	@Column(name="DocumentType")
	public String getDocumenttype() {
		return documenttype;
	}
	public void setDocumenttype(String documenttype) {
		this.documenttype=documenttype;
	}
	@Column(name="Number")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number=number;
	}
	@Column(name="ResponsiblePerson")
	public String getResponsibleperson() {
		return responsibleperson;
	}
	public void setResponsibleperson(String responsibleperson) {
		this.responsibleperson=responsibleperson;
	}
	@Column(name="Title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	@Column(name="Major")
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major=major;
	}
	@Column(name="Aircrafttype")
	public String getAircrafttype() {
		return aircrafttype;
	}
	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype=aircrafttype;
	}
	@Column(name="Abstract")
	public String getContentabstract() {
		return contentabstract;
	}
	public void setContentabstract(String contentabstract) {
		this.contentabstract=contentabstract;
	}
	@Column(name="Contenttype")
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype=contenttype;
	}
	@Column(name="Transceivertype")
	public String getTransceivertype() {
		return transceivertype;
	}
	public void setTransceivertype(String transceivertype) {
		this.transceivertype=transceivertype;
	}
	@Column(name="recipient")
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient=recipient;
	}
	@Column(name="datablock")
	public byte[] getDatablock() {
		return datablock;
	}
	public void setDatablock(byte[] datablock) {
		this.datablock=datablock;
	}
	@Column(name="bytes")
	public Integer getBytes() {
		return bytes;
	}
	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
}