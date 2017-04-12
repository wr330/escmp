package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Fly_DailyAcc")
public class Dailyacc implements Serializable {
	private static final long serialVersionUID = 1L;

	public Dailyacc() {
	}

	private String filename;
	private String ftype;
	private String id;
	private Integer bytes;
	private String summary;
	private byte[] datablock;
	private String miji;
	private String inputPers;
	private String inputPersOid;

	@Column(name = "FileName")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "ftype")
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	@Id
	@Column(name = "Oid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "bytes")
	public Integer getBytes() {
		return bytes;
	}

	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}

	@Column(name = "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "datablock")
	public byte[] getDatablock() {
		return datablock;
	}

	public void setDatablock(byte[] datablock) {
		this.datablock = datablock;
	}

	@Column(name = "miji")
	public String getMiji() {
		return miji;
	}

	public void setMiji(String miji) {
		this.miji = miji;
	}
	
	@Column(name = "inputPers")
	public String getInputPers() {
		return inputPers;
	}
	
	public void setInputPers(String inputPers) {
		this.inputPers = inputPers;
	}

	@Column(name = "inputPersOid")
	public String getInputPersOid() {
		return inputPersOid;
	}

	public void setInputPersOid(String inputPersOid) {
		this.inputPersOid = inputPersOid;
	}

}