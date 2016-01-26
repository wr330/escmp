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
@Table(name = "Fly_OutlineExecution")
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
	private Integer orderno;
	private String subject;
	private int outlineFlights;
	private int combineFlights;
	private String combineSubject;
	private String aircraftStruct;
	private String testStatus;
	private String engineState;
	private String testMethod;
	private String miji;
	private Integer jiaci;
	private Integer shijijiaci;
	
	private Subject project;
	private List<Outlineexecution> children;
	private Collection<CombineVehicle> combineVehicle;

	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "AircraftType")
	public String getAircrafttype() {
		return aircrafttype;
	}

	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype = aircrafttype;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "project")
	public Subject getProject() {
		return project;
	}

	public void setProject(Subject project) {
		this.project = project;
	}

	@Column(name = "RemainState")
	public String getRemainstate() {
		return remainstate;
	}

	public void setRemainstate(String remainstate) {
		this.remainstate = remainstate;
	}

	@Column(name = "ExecDate")
	public Date getExecdate() {
		return execdate;
	}

	public void setExecdate(Date execdate) {
		this.execdate = execdate;
	}

	@Column(name = "CompleteState")
	public String getCompletestate() {
		return completestate;
	}

	public void setCompletestate(String completestate) {
		this.completestate = completestate;
	}

	@Column(name = "Subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "outlineFlights")
	public int getOutlineFlights() {
		return outlineFlights;
	}

	public void setOutlineFlights(int outlineFlights) {
		this.outlineFlights = outlineFlights;
	}

	@Column(name = "combineFlights")
	public int getCombineFlights() {
		return combineFlights;
	}

	public void setCombineFlights(int combineFlights) {
		this.combineFlights = combineFlights;
	}

	@Column(name = "combineSubject")
	public String getCombineSubject() {
		return combineSubject;
	}

	public void setCombineSubject(String combineSubject) {
		this.combineSubject = combineSubject;
	}

	@Column(name = "aircraftStruct")
	public String getAircraftStruct() {
		return aircraftStruct;
	}

	public void setAircraftStruct(String aircraftStruct) {
		this.aircraftStruct = aircraftStruct;
	}

	@Column(name = "testStatus")
	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	@Column(name = "engineState")
	public String getEngineState() {
		return engineState;
	}

	public void setEngineState(String engineState) {
		this.engineState = engineState;
	}

	@Column(name = "testMethod")
	public String getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}
	@Column(name = "miji")
	public String getMiji() {
		return miji;
	}

	public void setMiji(String miji) {
		this.miji = miji;
	}
	@OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "outlineExecution")
	public Collection<CombineVehicle> getCombineVehicle() {
		return combineVehicle;
	}

	public void setCombineVehicle(Collection<CombineVehicle> combineVehicle) {
		this.combineVehicle = combineVehicle;
	}
	@Column(name = "parentnode")
	public String getParentnode() {
		return parentnode;
	}

	public void setParentnode(String parentnode) {
		this.parentnode = parentnode;
	}
	@Column(name = "orderno")
	public int getOrderno() {
		return orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	@Transient
	public List<Outlineexecution> getChildren() {
		return children;
	}

	public void setChildren(List<Outlineexecution> children) {
		this.children = children;
	}
	@Column(name = "jiaci")
	public Integer getJiaci() {
		return jiaci;
	}
	@Column(name = "shijijiaci")
	public void setJiaci(Integer jiaci) {
		this.jiaci = jiaci;
	}

	public Integer getShijijiaci() {
		return shijijiaci;
	}

	public void setShijijiaci(Integer shijijiaci) {
		this.shijijiaci = shijijiaci;
	}
	

}