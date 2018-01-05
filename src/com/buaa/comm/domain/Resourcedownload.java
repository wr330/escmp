package com.buaa.comm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="COMM_RESOURCEDOWNLOAD")
public class  Resourcedownload implements Serializable {
	private static final long serialVersionUID = 1L;
    public Resourcedownload(){}
   public Resourcedownload(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String efile ;
	private String resourcetype ;
	private String description ;
	private String filename ;
	private int miji ;
	private int downloadAmount;
	private Date uploadTime ;
	private String uploadPerson ;
	private String uploadPersonOid ;

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
	@Column(name="RESOURCETYPE_")
	public String getResourcetype() {
		return resourcetype;
	}
	public void setResourcetype(String resourcetype) {
		this.resourcetype=resourcetype;
	}
	@Column(name="DESCRIPTION_")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description=description;
	}
	@Column(name="FILENAME_")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename=filename;
	}
	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}
	
	@Column(name="DOWNLOADAMOUNT_")
	public int getDownloadAmount() {
		return downloadAmount;
	}
	public void setDownloadAmount(int downloadAmount) {
		this.downloadAmount = downloadAmount;
	}
	
	@Column(name="UPLOADTIME_")
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	@Column(name="UPLOADPERSON_")
	public String getUploadPerson() {
		return uploadPerson;
	}
	public void setUploadPerson(String uploadPerson) {
		this.uploadPerson = uploadPerson;
	}
	@Column(name="UPLOADPERSONOID_")
	public String getUploadPersonOid() {
		return uploadPersonOid;
	}
	public void setUploadPersonOid(String uploadPersonOid) {
		this.uploadPersonOid = uploadPersonOid;
	}

}