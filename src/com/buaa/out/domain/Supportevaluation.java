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
import java.lang.String;

@Entity
@Table(name="OUT_SUPPORTEVALUATION")
public class  Supportevaluation implements Serializable {
	private static final long serialVersionUID = 1L;
    public Supportevaluation(){}
   public Supportevaluation(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String supportGrade ;
	private Date supportEndTime ;
	private String supportAddress ;
	private String feedbackQuestion ;
	private Date supportStartTime ;
	private String supportSuggest ;
	private String efile ;
	private int miji ;
	private byte[] datablock ;
	private Integer bytes ;



	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name="SUPPORTGRADE_")
	public String getSupportGrade() {
		return supportGrade;
	}
	public void setSupportGrade(String supportGrade) {
		this.supportGrade = supportGrade;
	}
	
	@Column(name="SUPPORTENDTIME_")
	public Date getSupportEndTime() {
		return supportEndTime;
	}
	public void setSupportEndTime(Date supportEndTime) {
		this.supportEndTime = supportEndTime;
	}
	
	@Column(name="SUPPORTADDRESS_")
	public String getSupportAddress() {
		return supportAddress;
	}
	public void setSupportAddress(String supportAddress) {
		this.supportAddress = supportAddress;
	}
	
	@Column(name="FEEDBACKQUESTION_")
	public String getFeedbackQuestion() {
		return feedbackQuestion;
	}
	public void setFeedbackQuestion(String feedbackQuestion) {
		this.feedbackQuestion = feedbackQuestion;
	}
	
	@Column(name="SUPPORTSTARTTIME_")
	public Date getSupportStartTime() {
		return supportStartTime;
	}
	public void setSupportStartTime(Date supportStartTime) {
		this.supportStartTime = supportStartTime;
	}
	
	@Column(name="SUPPORTSUGGEST_")
	public String getSupportSuggest() {
		return supportSuggest;
	}
	public void setSupportSuggest(String supportSuggest) {
		this.supportSuggest = supportSuggest;
	}
	
	@Column(name="CUSSATSUR_")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile = efile;
	}
	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}
	@Column(name="DATABLOCK_")
	public byte[] getDatablock() {
		return datablock;
	}
	public void setDatablock(byte[] datablock) {
		this.datablock = datablock;
	}
	@Column(name="BYTES_")
	public Integer getBytes() {
		return bytes;
	}
	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}

}