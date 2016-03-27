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
	private String remainedproblem ;
	private String conclusion ;
	private String btplace ;
	private String technicalquestion ;
	private String keyword ;
	private String gainandadvice ;
	private String jobcontent ;
	private Date bttime ;
	private String bttask ;
	private String miji ;
	private int status ;
	private String sectionChief ;
	private Date sectionChiefDate ;
	private String departmentHead ;
	private Date departmentHeadDate ;
	private String writingPerson ;
	private String sharePerson ;
	


	private Collection<Appendixdocument> appendixdocument;

	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="BtPerson")
	public String getBtperson() {
		return btperson;
	}
	public void setBtperson(String btperson) {
		this.btperson=btperson;
	}
	@Column(name="RemainedProblem")
	public String getRemainedproblem() {
		return remainedproblem;
	}
	public void setRemainedproblem(String remainedproblem) {
		this.remainedproblem=remainedproblem;
	}
	@Column(name="Conclusion")
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion=conclusion;
	}
	@Column(name="BtPlace")
	public String getBtplace() {
		return btplace;
	}
	public void setBtplace(String btplace) {
		this.btplace=btplace;
	}
	@Column(name="TechnicalQuestion")
	public String getTechnicalquestion() {
		return technicalquestion;
	}
	public void setTechnicalquestion(String technicalquestion) {
		this.technicalquestion=technicalquestion;
	}
	@Column(name="KeyWord")
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword=keyword;
	}
	@Column(name="GainAndAdvice")
	public String getGainandadvice() {
		return gainandadvice;
	}
	public void setGainandadvice(String gainandadvice) {
		this.gainandadvice=gainandadvice;
	}
	@Column(name="JobContent")
	public String getJobcontent() {
		return jobcontent;
	}
	public void setJobcontent(String jobcontent) {
		this.jobcontent=jobcontent;
	}
	@Column(name="BtTime")
	public Date getBttime() {
		return bttime;
	}
	public void setBttime(Date bttime) {
		this.bttime=bttime;
	}
	@Column(name="BtTask")
	public String getBttask() {
		return bttask;
	}
	public void setBttask(String bttask) {
		this.bttask=bttask;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	
	@Column(name="Status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name="SectionChief")
	public String getSectionChief() {
		return sectionChief;
	}
	public void setSectionChief(String sectionChief) {
		this.sectionChief = sectionChief;
	}
	
	@Column(name="SectionChiefDate")
	public Date getSectionChiefDate() {
		return sectionChiefDate;
	}
	public void setSectionChiefDate(Date sectionChiefDate) {
		this.sectionChiefDate = sectionChiefDate;
	}
	
	@Column(name="DepartmentHead")
	public String getDepartmentHead() {
		return departmentHead;
	}
	public void setDepartmentHead(String departmentHead) {
		this.departmentHead = departmentHead;
	}
	
	@Column(name="DepartmentHeadDate")
	public Date getDepartmentHeadDate() {
		return departmentHeadDate;
	}
	public void setDepartmentHeadDate(Date departmentHeadDate) {
		this.departmentHeadDate = departmentHeadDate;
	}
	
	@Column(name="WritingPerson")
	public String getWritingPerson() {
		return writingPerson;
	}
	public void setWritingPerson(String writingPerson) {
		this.writingPerson = writingPerson;
	}
	@Column(name="SharePerson")
	public String getSharePerson() {
		return sharePerson;
	}
	public void setSharePerson(String sharePerson) {
		this.sharePerson = sharePerson;
	}
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "btreport")
    public Collection<Appendixdocument> getAppendixdocument() {
		return appendixdocument;
	}
	public void setAppendixdocument(Collection<Appendixdocument> appendixdocument) {
		this.appendixdocument=appendixdocument;
	}
}