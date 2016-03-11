package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Fly_TaskList")
public class Tasklist implements Serializable {
	private static final long serialVersionUID = 1L;

	public Tasklist() {
	}

	public Tasklist(String oid) {
		this.oid = oid;
	}

	private String oid;
	private String taskexecution;
	private String tasknumber;
	private String aircrafttype;
	private String taskcontent;
	private String subject;
	private Integer miji;
	private Collection<Sfstatistic> sfstatistic;
	private byte[] datablock;
	private String filename;
	private Integer bytes;

	@Column(name = "Datablock")
	public byte[] getDatablock() {
		return datablock;
	}
	
	public void setDatablock(byte[] datablock) {
		this.datablock = datablock;
	}

	@Column(name = "Filename")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "Bytes")
	public Integer getBytes() {
		return bytes;
	}

	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}

	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "TaskExecution")
	public String getTaskexecution() {
		return taskexecution;
	}

	public void setTaskexecution(String taskexecution) {
		this.taskexecution = taskexecution;
	}

	@Column(name = "TaskNumber")
	public String getTasknumber() {
		return tasknumber;
	}

	public void setTasknumber(String tasknumber) {
		this.tasknumber = tasknumber;
	}

	@Column(name = "AircraftType")
	public String getAircrafttype() {
		return aircrafttype;
	}

	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype = aircrafttype;
	}

	@Column(name = "TaskContent")
	public String getTaskcontent() {
		return taskcontent;
	}

	public void setTaskcontent(String taskcontent) {
		this.taskcontent = taskcontent;
	}

	@Column(name = "Subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "miji")
	public Integer getMiji() {
		return miji;
	}

	public void setMiji(Integer miji) {
		this.miji = miji;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "taskNo")
	public Collection<Sfstatistic> getSfstatistic() {
		return sfstatistic;
	}

	public void setSfstatistic(Collection<Sfstatistic> sfstatistic) {
		this.sfstatistic = sfstatistic;
	}
}