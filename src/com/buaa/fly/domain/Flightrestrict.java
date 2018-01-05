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

import java.util.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "FLY_FLIGHTRESTRICT")
public class Flightrestrict implements Serializable {
	private static final long serialVersionUID = 1L;

	public Flightrestrict() {
	}

	private String ftype;
	private String id;
	private Date begindate;
	private Integer bytes;
	private String isnew;
	private byte[] datablock;
	private String fname;
	private Boolean isaffect;
	private String major;
	private String affect;
	private String filename;
	private Date modifydate;
	private String fno;
	private String maincontent;
	private int miji;
	private Collection<Fighterxzh> fighterxzh;

	@Column(name = "FTYPE_")
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	@Id
	@Column(name = "OID_")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "BEGINDATE_")
	public Date getBegindate() {
		return begindate;
	}

	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}

	@Column(name = "BYTES_")
	public Integer getBytes() {
		return bytes;
	}

	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}

	@Column(name = "ISNEW_")
	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	@Column(name = "DATABLOCK_")
	public byte[] getDatablock() {
		return datablock;
	}

	public void setDatablock(byte[] datablock) {
		this.datablock = datablock;
	}

	@Column(name = "FNAME_")
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "ISAFFECT_")
	public Boolean getIsaffect() {
		return isaffect;
	}

	public void setIsaffect(Boolean isaffect) {
		this.isaffect = isaffect;
	}

	@Column(name = "MAJOR_")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "AFFECT_")
	public String getAffect() {
		return affect;
	}

	public void setAffect(String affect) {
		this.affect = affect;
	}

	@Column(name = "FILENAME_")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "MODIFYDATE_")
	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	@Column(name = "FNO_")
	public String getFno() {
		return fno;
	}

	public void setFno(String fno) {
		this.fno = fno;
	}

	@Column(name = "MAINCONTENT_")
	public String getMaincontent() {
		return maincontent;
	}

	public void setMaincontent(String maincontent) {
		this.maincontent = maincontent;
	}

	@Column(name = "MIJI_")
	public int getMiji() {
		return miji;
	}

	public void setMiji(int miji) {
		this.miji = miji;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "flightRestrict")
	public Collection<Fighterxzh> getFighterxzh() {
		return fighterxzh;
	}

	public void setFighterxzh(Collection<Fighterxzh> fighterxzh) {
		this.fighterxzh = fighterxzh;
	}

}