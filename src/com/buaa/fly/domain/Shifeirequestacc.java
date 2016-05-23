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
@Table(name = "Fly_ShifeiRequestAcc")
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
	private String miji;

	@Column(name = "FileNo")
	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
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

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Column(name = "authorDepartment")
	public String getAuthorDepartment() {
		return authorDepartment;
	}

	public void setAuthorDepartment(String authorDepartment) {
		this.authorDepartment = authorDepartment;
	}
	
	@Column(name = "autorSection")
	public String getAutorSection() {
		return autorSection;
	}

	public void setAutorSection(String autorSection) {
		this.autorSection = autorSection;
	}

	@Column(name = "datablock")
	public byte[] getDatablock() {
		return datablock;
	}

	public void setDatablock(byte[] datablock) {
		this.datablock = datablock;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "FileName")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "miji")
	public String getMiji() {
		return miji;
	}

	public void setMiji(String miji) {
		this.miji = miji;
	}

}