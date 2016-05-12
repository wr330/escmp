package com.buaa.fly.domain;

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
@Table(name="Fly_FighterOut")
public class  Fighterout implements Serializable {
	private static final long serialVersionUID = 1L;
    public Fighterout(){}
    public Fighterout(String oid) {
      this.oid=oid;
 	}	

	private String oid ;
	private Date outdate ;
	private String outaddress ;
	private Fighterinfo fighterinfo;

	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="OutDate")
	public Date getOutdate() {
		return outdate;
	}
	public void setOutdate(Date outdate) {
		this.outdate=outdate;
	}
	
	@Column(name="OutAddress")
	public String getOutaddress() {
		return outaddress;
	}
	public void setOutaddress(String outaddress) {
		this.outaddress=outaddress;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "FighterInfo")
	public Fighterinfo getFighterinfo() {
		return fighterinfo;
	}
	public void setFighterinfo(Fighterinfo fighterinfo) {
		this.fighterinfo = fighterinfo;
	}


}