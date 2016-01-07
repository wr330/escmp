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
   public Supportitem(String id) {
      this.id=id;
 	}	

	private String id ;

	private Date endexecutiontime ;
	private Date startexecutiontime ;
	private String registrationexecutor ;
	private int miji;

	private Supportprogram supportprogram;
	private Collection<Handover> handover;
	private Collection<Itemmember> itemmember;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
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
	@Column(name="miji")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "SupportProgram")
    public Supportprogram getSupportprogram() {
		return supportprogram;
	}
	public void setSupportprogram(Supportprogram supportprogram) {
		this.supportprogram=supportprogram;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supportitem")
    public Collection<Itemmember> getItemmember() {
		return itemmember;
	}
	public void setItemmember(Collection<Itemmember> itemmember) {
		this.itemmember=itemmember;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supportitem")
	public Collection<Handover> getHandover() {
		return handover;
	}
	public void setHandover(Collection<Handover> handover) {
		this.handover = handover;
	}
	
}