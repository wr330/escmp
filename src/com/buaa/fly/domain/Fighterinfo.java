package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Date;
import com.buaa.fly.domain.Fpici;

@Entity
@Table(name = "FLY_FIGHTERINFO")
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
	private Collection<Fighterout> fighterout;

	@Id
	@Column(name = "OUTFACTORYNO_", unique = true, nullable = false)
	public String getOutfactoryno() {
		return outfactoryno;
	}

	public void setOutfactoryno(String outfactoryno) {
		this.outfactoryno = outfactoryno;
	}

	@Column(name = "FTYPENAME_")
	public String getFtypeName() {
		return ftypeName;
	}

	public void setFtypeName(String ftypeName) {
		this.ftypeName = ftypeName;
	}

	@Column(name = "FIRSTFDATE_")
	public Date getFirstFDate() {
		return firstFDate;
	}

	public void setFirstFDate(Date firstFDate) {
		this.firstFDate = firstFDate;
	}

	@Column(name = "USERNO_")
	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	@Column(name = "OUTDATE_")
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outdate) {
		this.outDate = outdate;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PICIID_")
	public Fpici getPiciid() {
		return piciid;
	}

	public void setPiciid(Fpici piciid) {
		this.piciid = piciid;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "fighterinfo")
	public Collection<Fighterout> getFighterout() {
		return fighterout;
	}

	public void setFighterout(Collection<Fighterout> fighterout) {
		this.fighterout = fighterout;
	}
}