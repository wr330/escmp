package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FLY_TASKLIST")
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
	private int miji;	
	private byte[] datablock;
	private String filename;
	private Integer bytes;
	private Sfstatistic sfstatistic;

	@Column(name = "DATABLOCK_")
	public byte[] getDatablock() {
		return datablock;
	}
	
	public void setDatablock(byte[] datablock) {
		this.datablock = datablock;
	}

	@Column(name = "FILENAME_")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "BYTES_")
	public Integer getBytes() {
		return bytes;
	}

	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}

	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name = "TASKEXECUTION_")
	public String getTaskexecution() {
		return taskexecution;
	}

	public void setTaskexecution(String taskexecution) {
		this.taskexecution = taskexecution;
	}

	@Column(name = "TASKNUMBER_")
	public String getTasknumber() {
		return tasknumber;
	}

	public void setTasknumber(String tasknumber) {
		this.tasknumber = tasknumber;
	}

	@Column(name = "AIRCRAFTTYPE_")
	public String getAircrafttype() {
		return aircrafttype;
	}

	public void setAircrafttype(String aircrafttype) {
		this.aircrafttype = aircrafttype;
	}

	@Column(name = "TASKCONTENT_")
	public String getTaskcontent() {
		return taskcontent;
	}

	public void setTaskcontent(String taskcontent) {
		this.taskcontent = taskcontent;
	}

	@Column(name = "SUBJECT_")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "MIJI_")
	public int getMiji() {
		return miji;
	}

	public void setMiji(int miji) {
		this.miji = miji;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "FLYSTATISTIC_")
	public Sfstatistic getSfstatistic() {
		return sfstatistic;
	}

	public void setSfstatistic(Sfstatistic sfstatistic) {
		this.sfstatistic = sfstatistic;
	}

	
}