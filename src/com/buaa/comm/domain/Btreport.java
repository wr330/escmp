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
import com.buaa.comm.domain.Appendixdocument;

@Entity
@Table(name="Comm_BtReport")
public class  Btreport implements Serializable {
	private static final long serialVersionUID = 1L;
    public Btreport(){}
   public Btreport(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String btperson ;
	private String btpersonName ;
	private String remainedproblem ;
	private String conclusion ;
	private String btplace ;
	private String technicalquestion ;
	private String keyword ;
	private String gainandadvice ;
	private String jobcontent ;
	private Date bttime ;
	private String bttask ;
	private int miji ;
	private int status ;
	private String sectionChief ;
	private String sectionChiefName ;
	private Date sectionChiefDate ;
	private String sectionChiefComment ;	
	private String departmentHead ;
	private String departmentHeadName ;
	private Date departmentHeadDate ;	
	private String departmentHeadComment ;
	private String writingPerson ;
	private String writingPersonName ;
	private String wPerDepartment;

	private Collection<BtReportSharePerson> btReportSharePerson;
	private Collection<Appendixdocument> appendixdocument;

	@Id
		@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="BTPERSON_")
	public String getBtperson() {
		return btperson;
	}
	public void setBtperson(String btperson) {
		this.btperson=btperson;
	}
	
	@Column(name="BTPERSONNAME_")
	public String getBtpersonName() {
		return btpersonName;
	}
	public void setBtpersonName(String btpersonName) {
		this.btpersonName = btpersonName;
	}
	
	@Column(name="REMAINEDPROBLEM_")
	public String getRemainedproblem() {
		return remainedproblem;
	}
	public void setRemainedproblem(String remainedproblem) {
		this.remainedproblem=remainedproblem;
	}
	@Column(name="CONCLUSION_")
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion=conclusion;
	}
	@Column(name="BTPLACE_")
	public String getBtplace() {
		return btplace;
	}
	public void setBtplace(String btplace) {
		this.btplace=btplace;
	}
	@Column(name="TECHNICALQUESTION_")
	public String getTechnicalquestion() {
		return technicalquestion;
	}
	public void setTechnicalquestion(String technicalquestion) {
		this.technicalquestion=technicalquestion;
	}
	@Column(name="KEYWORD_")
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword=keyword;
	}
	@Column(name="GAINANDADVICE_")
	public String getGainandadvice() {
		return gainandadvice;
	}
	public void setGainandadvice(String gainandadvice) {
		this.gainandadvice=gainandadvice;
	}
	@Column(name="JOBCONTENT_")
	public String getJobcontent() {
		return jobcontent;
	}
	public void setJobcontent(String jobcontent) {
		this.jobcontent=jobcontent;
	}
	@Column(name="BTTIME_")
	public Date getBttime() {
		return bttime;
	}
	public void setBttime(Date bttime) {
		this.bttime=bttime;
	}
	@Column(name="BTTASK_")
	public String getBttask() {
		return bttask;
	}
	public void setBttask(String bttask) {
		this.bttask=bttask;
	}
	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}
	
	@Column(name="STATUS_")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name="SECTIONCHIEF_")
	public String getSectionChief() {
		return sectionChief;
	}
	public void setSectionChief(String sectionChief) {
		this.sectionChief = sectionChief;
	}
	
	@Column(name="SECTIONCHIEFNAME_")
	public String getSectionChiefName() {
		return sectionChiefName;
	}
	public void setSectionChiefName(String sectionChiefName) {
		this.sectionChiefName = sectionChiefName;
	}
	@Column(name="SECTIONCHIEFDATE_")
	public Date getSectionChiefDate() {
		return sectionChiefDate;
	}
	public void setSectionChiefDate(Date sectionChiefDate) {
		this.sectionChiefDate = sectionChiefDate;
	}
	
	@Column(name="SECTIONCHIEFCOMMENT_")
	public String getSectionChiefComment() {
		return sectionChiefComment;
	}
	public void setSectionChiefComment(String sectionChiefComment) {
		this.sectionChiefComment = sectionChiefComment;
	}
	
	@Column(name="DEPARTMENTHEAD_")
	public String getDepartmentHead() {
		return departmentHead;
	}
	public void setDepartmentHead(String departmentHead) {
		this.departmentHead = departmentHead;
	}
	
	@Column(name="DEPARTMENTHEADNAME_")
	public String getDepartmentHeadName() {
		return departmentHeadName;
	}
	public void setDepartmentHeadName(String departmentHeadName) {
		this.departmentHeadName = departmentHeadName;
	}
	
	@Column(name="DEPARTMENTHEADDATE_")
	public Date getDepartmentHeadDate() {
		return departmentHeadDate;
	}
	public void setDepartmentHeadDate(Date departmentHeadDate) {
		this.departmentHeadDate = departmentHeadDate;
	}
	
	@Column(name="DEPARTMENTHEADCOMMENT_")
	public String getDepartmentHeadComment() {
		return departmentHeadComment;
	}
	public void setDepartmentHeadComment(String departmentHeadComment) {
		this.departmentHeadComment = departmentHeadComment;
	}
	
	@Column(name="WRITINGPERSON_")
	public String getWritingPerson() {
		return writingPerson;
	}
	public void setWritingPerson(String writingPerson) {
		this.writingPerson = writingPerson;
	}
	
	@Column(name="WRITINGPERSONNAME_")
	public String getWritingPersonName() {
		return writingPersonName;
	}
	public void setWritingPersonName(String writingPersonName) {
		this.writingPersonName = writingPersonName;
	}
	
	@Column(name="WPERDEPARTMENT_")
	public String getwPerDepartment() {
		return wPerDepartment;
	}
	public void setwPerDepartment(String wPerDepartment) {
		this.wPerDepartment = wPerDepartment;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "btreport")
    public Collection<Appendixdocument> getAppendixdocument() {
		return appendixdocument;
	}
	public void setAppendixdocument(Collection<Appendixdocument> appendixdocument) {
		this.appendixdocument=appendixdocument;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "btreport")
	public Collection<BtReportSharePerson> getBtReportSharePerson() {
		return btReportSharePerson;
	}
	public void setBtReportSharePerson(Collection<BtReportSharePerson> btReportSharePerson) {
		this.btReportSharePerson = btReportSharePerson;
	}
	
}