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
import com.buaa.comm.domain.Jobstatistics;

@Entity
@Table(name="Comm_JobAllot")
public class  Joballot implements Serializable {
	private static final long serialVersionUID = 1L;
    public Joballot(){}
   public Joballot(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String personid ;
	private Boolean status ;
	private String content ;
	private String personname ;
	private Date finishtime ;
	private String miji;
	private int workStatus;
	private Jobstatistics jobstatistics;
	


	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="PersonID")
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid=personid;
	}
	@Column(name="Status")
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status=status;
	}
	@Column(name="Content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content=content;
	}
	@Column(name="PersonName")
	public String getPersonname() {
		return personname;
	}
	public void setPersonname(String personname) {
		this.personname=personname;
	}
	@Column(name="FinishTime")
	public Date getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(Date finishtime) {
		this.finishtime=finishtime;
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
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "JobStatistics")
    public Jobstatistics getJobstatistics() {
		return jobstatistics;
	}
	public void setJobstatistics(Jobstatistics jobstatistics) {
		this.jobstatistics=jobstatistics;
	}

}