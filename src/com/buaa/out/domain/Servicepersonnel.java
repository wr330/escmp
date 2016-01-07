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
@Table(name="Out_ServicePersonnel")
public class  Servicepersonnel implements Serializable {
	private static final long serialVersionUID = 1L;
    public Servicepersonnel(){}
   public Servicepersonnel(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String outfield ;
	private String job ;
	private Date datefrom ;
	private Date dateto ;
	private String name ;
	private String staffnumber ;
	private String miji ;



	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="Outfield")
	public String getOutfield() {
		return outfield;
	}
	public void setOutfield(String outfield) {
		this.outfield=outfield;
	}
	@Column(name="Job")
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job=job;
	}
	@Column(name="DateFrom")
	public Date getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(Date datefrom) {
		this.datefrom=datefrom;
	}
	@Column(name="DateTo")
	public Date getDateto() {
		return dateto;
	}
	public void setDateto(Date dateto) {
		this.dateto=dateto;
	}
	@Column(name="Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="StaffNumber")
	public String getStaffnumber() {
		return staffnumber;
	}
	public void setStaffnumber(String staffnumber) {
		this.staffnumber=staffnumber;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}


}