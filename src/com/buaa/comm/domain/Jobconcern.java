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
@Table(name="Comm_JobConcern")
public class  Jobconcern implements Serializable {
	private static final long serialVersionUID = 1L;
    public Jobconcern(){}
   public Jobconcern(String oid) {
      this.oid=oid;
 	}	

	private String oid ;
	private Date concerntime ;
	private String personid ;

	private Jobstatistics jobstatistics;


	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}
	@Column(name="ConcernTime")
	public Date getConcerntime() {
		return concerntime;
	}
	public void setConcerntime(Date concerntime) {
		this.concerntime=concerntime;
	}
	@Column(name="PersonID")
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid=personid;
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