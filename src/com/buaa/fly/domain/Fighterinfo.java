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
import com.buaa.fly.domain.Fpici;

@Entity
@Table(name = "Fly_Fighterinfo")
public class Fighterinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	public Fighterinfo() {
	}

	private String outfactoryno;
	private String ftypeName;
	private Date firstFDate;
	private String userno;
	private Date outDate;
	private Fpici piciid;

	@Id
	@Column(name = "outFactoryNo", unique = true, nullable = false)
	public String getOutfactoryno() {
		return outfactoryno;
	}

	public void setOutfactoryno(String outfactoryno) {
		this.outfactoryno = outfactoryno;
	}

	@Column(name = "ftypeName")
	public String getFtypeName() {
		return ftypeName;
	}

	public void setFtypeName(String ftypeName) {
		this.ftypeName = ftypeName;
	}

	@Column(name = "firstFDate")
	public Date getFirstFDate() {
		return firstFDate;
	}

	public void setFirstFDate(Date firstFDate) {
		this.firstFDate = firstFDate;
	}

	@Column(name = "userNo")
	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	@Column(name = "outDate")
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outdate) {
		this.outDate = outdate;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "piciid")
	public Fpici getPiciid() {
		return piciid;
	}

	public void setPiciid(Fpici piciid) {
		this.piciid = piciid;
	}
}