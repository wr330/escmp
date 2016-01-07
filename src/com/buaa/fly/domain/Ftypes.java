package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Ftypes")
public class  Ftypes implements Serializable {
	private static final long serialVersionUID = 1L;
    public Ftypes(){}
   public Ftypes(String ftypename) {
      this.ftypename=ftypename;
 	}	

	private String ftypename ;

	private Integer fighterid ;
	private Integer parentnode ;
	private String alias ;
	private String moreinfo ;
	private List<Ftypes> categories;

	private Collection<Fpici> fpici;
	private Collection<Fighterinfo> fighterinfo;
	
	@Id
		@Column(name = "ftypename", unique = true, nullable = false)
	public String getFtypename() {
		return ftypename;
	}
	public void setFtypename(String ftypename) {
		this.ftypename=ftypename;
	}
	
	@Column(name="parentnode")
	public Integer getParentnode() {
		return parentnode;
	}
	public void setParentnode(Integer parentnode) {
		this.parentnode = parentnode;
	}
	@Column(name="fighterid")
	public Integer getFighterid() {
		return fighterid;
	}
	public void setFighterid(Integer fighterid) {
		this.fighterid=fighterid;
	}
	@Column(name="alias")
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias=alias;
	}
	@Column(name="moreinfo")
	public String getMoreinfo() {
		return moreinfo;
	}
	public void setMoreinfo(String moreinfo) {
		this.moreinfo=moreinfo;
	}

	@Transient
	public List<Ftypes> getCategories() {
		return categories;
	}
	public void setCategories(List<Ftypes> categories) {
		this.categories = categories;
	}
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "ftypes")
    public Collection<Fpici> getFpici() {
		return fpici;
	}
	public void setFpici(Collection<Fpici> fpici) {
		this.fpici=fpici;
	}
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "fpici")
    public Collection<Fighterinfo> getFighterinfo() {
		return fighterinfo;
	}
	public void setFighterinfo(Collection<Fighterinfo> fighterinfo) {
		this.fighterinfo=fighterinfo;
	}
}