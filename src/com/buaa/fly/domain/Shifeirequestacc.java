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
@Table(name = "FLY_SHIFEIREQUESTACC")
public class Shifeirequestacc implements Serializable {
	private static final long serialVersionUID = 1L;

	public Shifeirequestacc() {
	}

	private String fileno;
	private String ftype;
	private String id;
	private Integer bytes;
	private Date date;
	private String author;	
	private String authorDepartment;
	private String autorSection;
	private byte[] datablock;
	private String type;
	private String filename;
	private int miji;
	private String inputPers;
	private String inputPersOid;

	@Column(name = "FILENO_")
	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

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

	@Column(name = "BYTES_")
	public Integer getBytes() {
		return bytes;
	}

	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}

	@Column(name = "SFRADATE_")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "AUTHOR_")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "AUTHORDEPARTMENT_")
	public String getAuthorDepartment() {
		return authorDepartment;
	}

	public void setAuthorDepartment(String authorDepartment) {
		this.authorDepartment = authorDepartment;
	}
	
	@Column(name = "AUTORSECTION_")
	public String getAutorSection() {
		return autorSection;
	}

	public void setAutorSection(String autorSection) {
		this.autorSection = autorSection;
	}

	@Column(name = "DATABLOCK_")
	public byte[] getDatablock() {
		return datablock;
	}

	public void setDatablock(byte[] datablock) {
		this.datablock = datablock;
	}

	@Column(name = "TYPE_")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "FILENAME_")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "MIJI_")
	public int getMiji() {
		return miji;
	}

	public void setMiji(int miji) {
		this.miji = miji;
	}

	@Column(name = "INPUTPERS_")
	public String getInputPers() {
		return inputPers;
	}

	public void setInputPers(String inputPers) {
		this.inputPers = inputPers;
	}

	@Column(name = "INPUTPERSOID_")
	public String getInputPersOid() {
		return inputPersOid;
	}

	public void setInputPersOid(String inputPersOid) {
		this.inputPersOid = inputPersOid;
	}

}