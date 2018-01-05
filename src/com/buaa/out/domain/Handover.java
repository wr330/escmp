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
@Table(name="OUT_HANDOVER")
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
	private String sectionChiefSure ;
	private byte[] datablock;//因为在model中类型无法确定，所以此字段在model中没有加
	private Supportprogram supportprogram;
	private String sectionChief ;
	private String sectionChiefName ;


	@Id
		@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="EFILE_")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile=efile;
	}
	@Column(name="FILENAME_")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename=filename;
	}
	@Column(name="PERSONTO_")
	public String getPersonto() {
		return personto;
	}
	public void setPersonto(String personto) {
		this.personto=personto;
	}
	@Column(name="FILENUMBER_")
	public String getFilenumber() {
		return filenumber;
	}
	public void setFilenumber(String filenumber) {
		this.filenumber=filenumber;
	}
	@Column(name="BYTES_")
	public Integer getBytes() {
		return bytes;
	}
	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}
	@Column(name="PERSONFROM_")
	public String getPersonfrom() {
		return personfrom;
	}
	public void setPersonfrom(String personfrom) {
		this.personfrom=personfrom;
	}
	@Column(name="HANDOVERTIME_")
	public Date getHandovertime() {
		return handovertime;
	}
	public void setHandovertime(Date handovertime) {
		this.handovertime=handovertime;
	}
	@Column(name="SECTIONCHIEFSURE_")
	public String getSectionChiefSure() {
		return sectionChiefSure;
	}
	public void setSectionChiefSure(String sectionChiefSure) {
		this.sectionChiefSure = sectionChiefSure;
	}
	@Column(name="DATABLOCK_")
	public byte[] getDatablock() {
		return datablock;
	}
	public void setDatablock(byte[] datablock) {
		this.datablock=datablock;
	}

	@Column(name="SECTIONCHIEF_")
	public String getSectionChief() {
		return sectionChief;
	}
	public void setSectionChief(String sectionChief) {
		this.sectionChief = sectionChief;
	}
	
	@Column(name="SECTIONCHIEFNAME_")
	public String getSectionChiefName() {
		return sectionChiefName;
	}
	public void setSectionChiefName(String sectionChiefName) {
		this.sectionChiefName = sectionChiefName;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "SUPPORTPROGRAM_")
	public Supportprogram getSupportprogram() {
		return supportprogram;
	}
	public void setSupportprogram(Supportprogram supportprogram) {
		this.supportprogram = supportprogram;
	}

}