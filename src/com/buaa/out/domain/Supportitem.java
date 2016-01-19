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
@Table(name="Out_SupportItem")
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

	private Supportprogram supportprogram;
	

	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name="EndExecutionTime")
	public Date getEndexecutiontime() {
		return endexecutiontime;
	}
	public void setEndexecutiontime(Date endexecutiontime) {
		this.endexecutiontime=endexecutiontime;
	}
	@Column(name="StartExecutionTime")
	public Date getStartexecutiontime() {
		return startexecutiontime;
	}
	public void setStartexecutiontime(Date startexecutiontime) {
		this.startexecutiontime=startexecutiontime;
	}
	@Column(name="RegistrationExecutor")
	public String getRegistrationexecutor() {
		return registrationexecutor;
	}
	public void setRegistrationexecutor(String registrationexecutor) {
		this.registrationexecutor=registrationexecutor;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "SupportProgram")
    public Supportprogram getSupportprogram() {
		return supportprogram;
	}
	public void setSupportprogram(Supportprogram supportprogram) {
		this.supportprogram=supportprogram;
	}

}