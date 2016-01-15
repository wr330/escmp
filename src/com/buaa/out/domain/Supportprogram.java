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
@Table(name="Out_SupportProgram")
public class  Supportprogram implements Serializable {
	private static final long serialVersionUID = 1L;
    public Supportprogram(){}
   public Supportprogram(String oid) {
      this.oid=oid;
 	}	

	private String oid ;
	private String maker ;
	private String troop ;
	private String staffrequirement ;
	private String supporttype ;
	private String changeperiod ;
	private String workaddress ;
	private Date worktime ;
	private Date endtime ;
	private List<Supportprogram> children;
	private String miji ;

	private Collection<Supportitem> supportitem;
	private Collection<Handover> handover;

	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="Troop")
	public String getTroop() {
		return troop;
	}
	public void setTroop(String troop) {
		this.troop=troop;
	}
	@Column(name="StaffRequirement")
	public String getStaffrequirement() {
		return staffrequirement;
	}
	public void setStaffrequirement(String staffrequirement) {
		this.staffrequirement=staffrequirement;
	}
	@Column(name="SupportType")
	public String getSupporttype() {
		return supporttype;
	}
	public void setSupporttype(String supporttype) {
		this.supporttype=supporttype;
	}
	@Column(name="ChangePeriod")
	public String getChangeperiod() {
		return changeperiod;
	}
	public void setChangeperiod(String changeperiod) {
		this.changeperiod=changeperiod;
	}
	@Column(name="WorkAddress")
	public String getWorkaddress() {
		return workaddress;
	}
	public void setWorkaddress(String workaddress) {
		this.workaddress=workaddress;
	}
	@Column(name="WorkTime")
	public Date getWorktime() {
		return worktime;
	}
	public void setWorktime(Date worktime) {
		this.worktime=worktime;
	}
	@Column(name="Maker")
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	@Column(name="EndTime")
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	@Transient
	public List<Supportprogram> getChildren() {
		return children;
	}
	public void setChildren(List<Supportprogram> children) {
		this.children = children;
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