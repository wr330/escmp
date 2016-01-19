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

@Entity
@Table(name="Out_Handover")
public class  Handover implements Serializable {
	private static final long serialVersionUID = 1L;
    public Handover(){}
   public Handover(String oid) {
      this.oid=oid;
 	}	

	private String oid ;
	private Integer bytes ;
	private String efile ;
	private String filename ;
	private String personto ;
	private String filenumber ;
	private String personfrom ;
	private Date handovertime ;
	private byte[] datablock;//因为在model中类型无法确定，所以此字段在model中没有加
	private Supportprogram supportprogram;


	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="EFile")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile=efile;
	}
	@Column(name="FileName")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename=filename;
	}
	@Column(name="PersonTo")
	public String getPersonto() {
		return personto;
	}
	public void setPersonto(String personto) {
		this.personto=personto;
	}
	@Column(name="FileNumber")
	public String getFilenumber() {
		return filenumber;
	}
	public void setFilenumber(String filenumber) {
		this.filenumber=filenumber;
	}
	@Column(name="bytes")
	public Integer getBytes() {
		return bytes;
	}
	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}
	@Column(name="PersonFrom")
	public String getPersonfrom() {
		return personfrom;
	}
	public void setPersonfrom(String personfrom) {
		this.personfrom=personfrom;
	}
	@Column(name="HandoverTime")
	public Date getHandovertime() {
		return handovertime;
	}
	public void setHandovertime(Date handovertime) {
		this.handovertime=handovertime;
	}
	@Column(name="datablock")
	public byte[] getDatablock() {
		return datablock;
	}
	public void setDatablock(byte[] datablock) {
		this.datablock=datablock;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "SupportProgram")
	public Supportprogram getSupportprogram() {
		return supportprogram;
	}
	public void setSupportprogram(Supportprogram supportprogram) {
		this.supportprogram = supportprogram;
	}

}