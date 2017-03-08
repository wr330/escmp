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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import com.buaa.comm.domain.Joballot;
import com.buaa.comm.domain.Jobconcern;

@Entity
@Table(name="Comm_JobStatistics")
public class  Jobstatistics implements Serializable {
	private static final long serialVersionUID = 1L;
    public Jobstatistics(){}
   public Jobstatistics(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private Date realcompletedate ;
	private String sourcepersonid ;
	private String writingperson ;
	private String writingpersonName ;
	private String sourceevent ;
	private Date arrangementdate ;
	private String sourcepersonname ;
	private String content ;
	private Date refreshtime ;
	private Boolean addp6 ;
	private String projectname ;
	private String departid ;
	private Boolean isfromp6 ;
	private String tag ;
	private String status ;
	private String departname ;
	private Date plancompletedate ;
	private String miji ;
	private int workStatus ;
	private String sectionChief ;
	private String sectionChiefName ;
	private String workReport;
	@Transient
	private Boolean isfocus;

	public Boolean getIsfocus() {
		return isfocus;
	}
	public void setIsfocus(Boolean isfocus) {
		this.isfocus = isfocus;
	}

	private Collection<Joballot> joballot;
	private Collection<Jobconcern> jobconcern;

	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="RealCompleteDate")
	public Date getRealcompletedate() {
		return realcompletedate;
	}
	public void setRealcompletedate(Date realcompletedate) {
		this.realcompletedate=realcompletedate;
	}
	@Column(name="SourcePersonID")
	public String getSourcepersonid() {
		return sourcepersonid;
	}
	public void setSourcepersonid(String sourcepersonid) {
		this.sourcepersonid=sourcepersonid;
	}
	@Column(name="WritingPerson")
	public String getWritingperson() {
		return writingperson;
	}
	public void setWritingperson(String writingperson) {
		this.writingperson=writingperson;
	}
	@Column(name="WritingPersonName")
	public String getWritingpersonName() {
		return writingpersonName;
	}
	public void setWritingpersonName(String writingpersonName) {
		this.writingpersonName = writingpersonName;
	}
	@Column(name="SourceEvent")
	public String getSourceevent() {
		return sourceevent;
	}
	public void setSourceevent(String sourceevent) {
		this.sourceevent=sourceevent;
	}
	@Column(name="ArrangementDate")
	public Date getArrangementdate() {
		return arrangementdate;
	}
	public void setArrangementdate(Date arrangementdate) {
		this.arrangementdate=arrangementdate;
	}
	@Column(name="SourcePersonName")
	public String getSourcepersonname() {
		return sourcepersonname;
	}
	public void setSourcepersonname(String sourcepersonname) {
		this.sourcepersonname=sourcepersonname;
	}
	@Column(name="Content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content=content;
	}
	@Column(name="RefreshTime")
	public Date getRefreshtime() {
		return refreshtime;
	}
	public void setRefreshtime(Date refreshtime) {
		this.refreshtime=refreshtime;
	}
	@Column(name="AddP6")
	public Boolean getAddp6() {
		return addp6;
	}
	public void setAddp6(Boolean addp6) {
		this.addp6=addp6;
	}
	@Column(name="ProjectName")
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname=projectname;
	}
	@Column(name="DepartID")
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid=departid;
	}
	@Column(name="IsFromP6")
	public Boolean getIsfromp6() {
		return isfromp6;
	}
	public void setIsfromp6(Boolean isfromp6) {
		this.isfromp6=isfromp6;
	}
	@Column(name="Tag")
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag=tag;
	}
	@Column(name="Status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status=status;
	}
	@Column(name="DepartName")
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname=departname;
	}
	@Column(name="PlanCompleteDate")
	public Date getPlancompletedate() {
		return plancompletedate;
	}
	public void setPlancompletedate(Date plancompletedate) {
		this.plancompletedate=plancompletedate;
	}

	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	@Column(name="WorkStatus")
	public int getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(int workStatus) {
		this.workStatus = workStatus;
	}
	@Column(name="SectionChief")
	public String getSectionChief() {
		return sectionChief;
	}
	public void setSectionChief(String sectionChief) {
		this.sectionChief = sectionChief;
	}
	@Column(name="SectionChiefName")
	public String getSectionChiefName() {
		return sectionChiefName;
	}
	public void setSectionChiefName(String sectionChiefName) {
		this.sectionChiefName = sectionChiefName;
	}
	@Column(name="WorkReport")
	public String getWorkReport() {
		return workReport;
	}
	public void setWorkReport(String workReport) {
		this.workReport = workReport;
	}
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "jobstatistics")
    public Collection<Joballot> getJoballot() {
		return joballot;
	}
	public void setJoballot(Collection<Joballot> joballot) {
		this.joballot=joballot;
	}
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "jobstatistics")
    public Collection<Jobconcern> getJobconcern() {
		return jobconcern;
	}
	public void setJobconcern(Collection<Jobconcern> jobconcern) {
		this.jobconcern=jobconcern;
	}
	
}