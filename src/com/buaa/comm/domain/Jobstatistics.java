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
@Table(name="COMM_JOBSTATISTICS")
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
	private int miji ;
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
		@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="REALCOMPLETEDATE_")
	public Date getRealcompletedate() {
		return realcompletedate;
	}
	public void setRealcompletedate(Date realcompletedate) {
		this.realcompletedate=realcompletedate;
	}
	@Column(name="SOURCEPERSONID_")
	public String getSourcepersonid() {
		return sourcepersonid;
	}
	public void setSourcepersonid(String sourcepersonid) {
		this.sourcepersonid=sourcepersonid;
	}
	@Column(name="WRITINGPERSON_")
	public String getWritingperson() {
		return writingperson;
	}
	public void setWritingperson(String writingperson) {
		this.writingperson=writingperson;
	}
	@Column(name="WRITINGPERSONNAME_")
	public String getWritingpersonName() {
		return writingpersonName;
	}
	public void setWritingpersonName(String writingpersonName) {
		this.writingpersonName = writingpersonName;
	}
	@Column(name="SOURCEEVENT_")
	public String getSourceevent() {
		return sourceevent;
	}
	public void setSourceevent(String sourceevent) {
		this.sourceevent=sourceevent;
	}
	@Column(name="ARRANGEMENTDATE_")
	public Date getArrangementdate() {
		return arrangementdate;
	}
	public void setArrangementdate(Date arrangementdate) {
		this.arrangementdate=arrangementdate;
	}
	@Column(name="SOURCEPERSONNAME_")
	public String getSourcepersonname() {
		return sourcepersonname;
	}
	public void setSourcepersonname(String sourcepersonname) {
		this.sourcepersonname=sourcepersonname;
	}
	@Column(name="CONTENT_")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content=content;
	}
	@Column(name="REFRESHTIME_")
	public Date getRefreshtime() {
		return refreshtime;
	}
	public void setRefreshtime(Date refreshtime) {
		this.refreshtime=refreshtime;
	}
	@Column(name="ADDP6_")
	public Boolean getAddp6() {
		return addp6;
	}
	public void setAddp6(Boolean addp6) {
		this.addp6=addp6;
	}
	@Column(name="PROJECTNAME_")
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname=projectname;
	}
	@Column(name="DEPARTID_")
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid=departid;
	}
	@Column(name="ISFROMP6_")
	public Boolean getIsfromp6() {
		return isfromp6;
	}
	public void setIsfromp6(Boolean isfromp6) {
		this.isfromp6=isfromp6;
	}
	@Column(name="TAG_")
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag=tag;
	}
	@Column(name="STATUS_")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status=status;
	}
	@Column(name="DEPARTNAME_")
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname=departname;
	}
	@Column(name="PLANCOMPLETEDATE_")
	public Date getPlancompletedate() {
		return plancompletedate;
	}
	public void setPlancompletedate(Date plancompletedate) {
		this.plancompletedate=plancompletedate;
	}

	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}
	@Column(name="WORKSTATUS_")
	public int getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(int workStatus) {
		this.workStatus = workStatus;
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
	@Column(name="WORKREPORT_")
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