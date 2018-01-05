package com.buaa.out.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.buaa.fly.domain.Ftypes;

import java.util.Date;
import java.lang.String;


@Entity
@Table(name="OUT_SUPPORTPROGRAM")
public class  Supportprogram implements Serializable {
	private static final long serialVersionUID = 1L;
    public Supportprogram(){}
   public Supportprogram(String oid) {
      this.oid=oid;
 	}	

	private String oid ;
	private String maker ;
	private String troop ;
	private Integer staffrequirement ;
	private String supporttype ;
	private Integer changeperiod ;
	private String workaddress ;
	private Date worktime ;
	private Date endtime ;
	private Date maketime ;
	private int miji ;

	private Collection<Supportitem> supportitem;
	private Collection<Handover> handover;

	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="TROOP_")
	public String getTroop() {
		return troop;
	}
	public void setTroop(String troop) {
		this.troop=troop;
	}
	@Column(name="STAFFREQUIREMENT_")
	public Integer getStaffrequirement() {
		return staffrequirement;
	}
	public void setStaffrequirement(Integer staffrequirement) {
		this.staffrequirement=staffrequirement;
	}
	@Column(name="SUPPORTTYPE_")
	public String getSupporttype() {
		return supporttype;
	}
	public void setSupporttype(String supporttype) {
		this.supporttype=supporttype;
	}
	@Column(name="CHANGEPERIOD_")
	public Integer getChangeperiod() {
		return changeperiod;
	}
	public void setChangeperiod(Integer changeperiod) {
		this.changeperiod=changeperiod;
	}
	@Column(name="WORKADDRESS_")
	public String getWorkaddress() {
		return workaddress;
	}
	public void setWorkaddress(String workaddress) {
		this.workaddress=workaddress;
	}
	@Column(name="WORKTIME_")
	public Date getWorktime() {
		return worktime;
	}
	public void setWorktime(Date worktime) {
		this.worktime=worktime;
	}
	@Column(name="MAKER_")
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	@Column(name="ENDTIME_")
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	@Column(name="MAKETIME_")
	public Date getMaketime() {
		return maketime;
	}
	public void setMaketime(Date maketime) {
		this.maketime = maketime;
	}
	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supportprogram")
    public Collection<Supportitem> getSupportitem() {
		return supportitem;
	}
	public void setSupportitem(Collection<Supportitem> supportitem) {
		this.supportitem=supportitem;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "supportprogram")
    public Collection<Handover> getHandover() {
		return handover;
	}
	public void setHandover(Collection<Handover> handover) {
		this.handover = handover;
	}
}