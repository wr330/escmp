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
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "btreport")
    public Collection<Appendixdocument> getAppendixdocument() {
		return appendixdocument;
	}
	public void setAppendixdocument(Collection<Appendixdocument> appendixdocument) {
		this.appendixdocument=appendixdocument;
	}
}