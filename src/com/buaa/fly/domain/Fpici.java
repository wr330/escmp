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

import java.math.BigDecimal;
import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.domain.Fighterinfo;

@Entity
@Table(name = "Fly_fpici")
public class Fpici implements Serializable {
	private static final long serialVersionUID = 1L;

	public Fpici() {
	}

	private String piciName;
	private String piciID;
	private String more;
	private Ftypes fTypeName;
	private Collection<Fighterinfo> fighterinfo;

	@Id
	@Column(name = "piciID", unique = true, nullable = false)
	public String getPiciID() {
		return piciID;
	}

	public void setPiciID(String piciID) {
		this.piciID = piciID;
	}

	@Column(name = "piciName")
	public String getPiciName() {
		return piciName;
	}

	public void setPiciName(String piciName) {
		this.piciName = piciName;
	}

	public void setPiciid(String piciName) {
		this.piciName = piciName;
	}

	@Column(name = "more")
	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "fTypeName")
	public Ftypes getfTypeName() {
		return fTypeName;
	}

	public void setfTypeName(Ftypes fTypeName) {
		this.fTypeName = fTypeName;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "piciid")
	public Collection<Fighterinfo> getFighterinfo() {
		return fighterinfo;
	}

	public void setFighterinfo(Collection<Fighterinfo> fighterinfo) {
		this.fighterinfo = fighterinfo;
	}
}