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
@Table(name="OUT_SUPPORTITEM")
public class  Supportitem implements Serializable {
	private static final long serialVersionUID = 1L;
    public Supportitem(){}
   public Supportitem(String oid) {
      this.oid = oid;
 	}	

	private String oid ;

	private Date endexecutiontime ;
	private Date startexecutiontime ;
	private String registrationexecutor ;
	private String registExecOid ;

	private Supportprogram supportprogram;
	private Integer days ;
	

	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name="ENDEXECUTIONTIME_")
	public Date getEndexecutiontime() {
		return endexecutiontime;
	}
	public void setEndexecutiontime(Date endexecutiontime) {
		this.endexecutiontime=endexecutiontime;
	}
	@Column(name="STARTEXECUTIONTIME_")
	public Date getStartexecutiontime() {
		return startexecutiontime;
	}
	public void setStartexecutiontime(Date startexecutiontime) {
		this.startexecutiontime=startexecutiontime;
	}
	@Column(name="REGISTRATIONEXECUTOR_")
	public String getRegistrationexecutor() {
		return registrationexecutor;
	}
	public void setRegistrationexecutor(String registrationexecutor) {
		this.registrationexecutor=registrationexecutor;
	}
	@Column(name="REGISTEXECOID_")
	public String getRegistExecOid() {
		return registExecOid;
	}
	public void setRegistExecOid(String registExecOid) {
		this.registExecOid = registExecOid;
	}
	@Column(name="DAYS_",insertable=false,updatable=false)
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "SUPPORTPROGRAM_")
    public Supportprogram getSupportprogram() {
		return supportprogram;
	}
	public void setSupportprogram(Supportprogram supportprogram) {
		this.supportprogram=supportprogram;
	}

}