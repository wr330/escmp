package com.buaa.comm.domain;

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


@Entity
@Table(name="Comm_ResourceDownLoad")
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
	private String miji ;
	private int downloadAmount;

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
	@Column(name="ResourceType")
	public String getResourcetype() {
		return resourcetype;
	}
	public void setResourcetype(String resourcetype) {
		this.resourcetype=resourcetype;
	}
	@Column(name="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description=description;
	}
	@Column(name="FileName")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename=filename;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	
	@Column(name="DownloadAmount")
	public int getDownloadAmount() {
		return downloadAmount;
	}
	public void setDownloadAmount(int downloadAmount) {
		this.downloadAmount = downloadAmount;
	}

}