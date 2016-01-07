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
@Table(name="Fpici")
public class  Fpici implements Serializable {
	private static final long serialVersionUID = 1L;
    public Fpici(){}

	private String piciname ;
	private Integer piciid ;
	private Integer fighterid ;
	private String more ;
	private Ftypes ftypes;

	private Collection<Fighterinfo> fighterinfo;

	@Id
		@Column(name = "piciName", unique = true, nullable = false)
	public String getPiciname() {
		return piciname;
	}
	public void setPiciname(String piciname) {
		this.piciname=piciname;
	}
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="piciID")
	public Integer getPiciid() {
		return piciid;
	}
	public void setPiciid(Integer piciid) {
		this.piciid=piciid;
	}
	@Column(name="fighterID")
	public Integer getFighterid() {
		return fighterid;
	}
	public void setFighterid(Integer fighterid) {
		this.fighterid=fighterid;
	}
	@Column(name="more")
	public String getMore() {
		return more;
	}
	public void setMore(String more) {
		this.more=more;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "fTypeName")
    public Ftypes getFtypes() {
		return ftypes;
	}
	public void setFtypes(Ftypes ftypes) {
		this.ftypes=ftypes;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "fpici")
    public Collection<Fighterinfo> getFighterinfo() {
		return fighterinfo;
	}
	public void setFighterinfo(Collection<Fighterinfo> fighterinfo) {
		this.fighterinfo=fighterinfo;
	}
}