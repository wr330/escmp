package com.buaa.fly.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FLY_OUTLINEEXECUTION")
public class Outlineexecution implements Serializable {
	private static final long serialVersionUID = 1L;

	public Outlineexecution() {
	}

	public Outlineexecution(String oid) {
		this.oid = oid;
	}

	private String oid;
	private String aircrafttype;
	private String remainstate;
	private Date execdate;
	private String completestate;
	private String parentnode;
	private String subject;
	private int outlineFlights;
	private Integer combineFlights;
	private String combineSubject;
	private String aircraftStruct;
	private String testStatus;
	private String engineState;
	private String testMethod;
	private int miji;
	private Integer jiaci;
	private Integer shijijiaci;
	private Subject project;
	private Integer testFligMaxNum;
	private List<Outlineexecution> children;
	private Collection<CombineVehicle> combineVehicle;

	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "AIRCRAFTTYPE_")
	public String getAircrafttype() {
		return aircrafttype;
	}

	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype = aircrafttype;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PROJECT_")
	public Subject getProject() {
		return project;
	}

	public void setProject(Subject project) {
		this.project = project;
	}

	@Column(name = "REMAINSTATE_")
	public String getRemainstate() {
		return remainstate;
	}

	public void setRemainstate(String remainstate) {
		this.remainstate = remainstate;
	}

	@Column(name = "EXECDATE_")
	public Date getExecdate() {
		return execdate;
	}

	public void setExecdate(Date execdate) {
		this.execdate = execdate;
	}

	@Column(name = "COMPLETESTATE_")
	public String getCompletestate() {
		return completestate;
	}

	public void setCompletestate(String completestate) {
		this.completestate = completestate;
	}

	@Column(name = "SUBJECT_")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "OUTLINEFLIGHTS_")
	public int getOutlineFlights() {
		return outlineFlights;
	}

	public void setOutlineFlights(int outlineFlights) {
		this.outlineFlights = outlineFlights;
	}

	@Column(name = "COMBINEFLIGHTS_")
	public Integer getCombineFlights() {
		return combineFlights;
	}

	public void setCombineFlights(Integer combineFlights) {
		this.combineFlights = combineFlights;
	}

	@Column(name = "COMBINESUBJECT_")
	public String getCombineSubject() {
		return combineSubject;
	}

	public void setCombineSubject(String combineSubject) {
		this.combineSubject = combineSubject;
	}

	@Column(name = "AIRCRAFTSTRUCT_")
	public String getAircraftStruct() {
		return aircraftStruct;
	}

	public void setAircraftStruct(String aircraftStruct) {
		this.aircraftStruct = aircraftStruct;
	}

	@Column(name = "TESTSTATUS_")
	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	@Column(name = "ENGINESTATE_")
	public String getEngineState() {
		return engineState;
	}

	public void setEngineState(String engineState) {
		this.engineState = engineState;
	}

	@Column(name = "TESTMETHOD_")
	public String getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	@Column(name = "MIJI_")
	public int getMiji() {
		return miji;
	}

	public void setMiji(int miji) {
		this.miji = miji;
	}

	@OneToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "outlineExecution")
	public Collection<CombineVehicle> getCombineVehicle() {
		return combineVehicle;
	}

	public void setCombineVehicle(Collection<CombineVehicle> combineVehicle) {
		this.combineVehicle = combineVehicle;
	}

	@Column(name = "PARENTNODE_")
	public String getParentnode() {
		return parentnode;
	}

	public void setParentnode(String parentnode) {
		this.parentnode = parentnode;
	}

	@Transient
	public List<Outlineexecution> getChildren() {
		return children;
	}

	public void setChildren(List<Outlineexecution> children) {
		this.children = children;
	}

	@Column(name = "JIACI_")
	public Integer getJiaci() {
		return jiaci;
	}

	public void setJiaci(Integer jiaci) {
		this.jiaci = jiaci;
	}
	
	@Column(name = "SHIJIJIACI_")
	public Integer getShijijiaci() {
		return shijijiaci;
	}

	public void setShijijiaci(Integer shijijiaci) {
		this.shijijiaci = shijijiaci;
	}

	@Column(name = "TESTFLIGMAXNUM_")
	public Integer getTestFligMaxNum() {
		return testFligMaxNum;
	}

	public void setTestFligMaxNum(Integer testFligMaxNum) {
		this.testFligMaxNum = testFligMaxNum;
	}

}