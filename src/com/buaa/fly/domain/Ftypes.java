package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="FLY_FTYPES")
public class  Ftypes implements Serializable {
	private static final long serialVersionUID = 1L;
    public Ftypes(){}
   public Ftypes(String ftypename) {
      this.ftypename=ftypename;
 	}	

	private String ftypename ;
	private String alias ;
	private String moreinfo ;
	private Collection<Fpici> fpici;
	private Collection<Subject> subject;
	
	@Id
	@Column(name = "FTYPENAME_", unique = true, nullable = false)
	public String getFtypename() {
		return ftypename;
	}
	public void setFtypename(String ftypename) {
		this.ftypename=ftypename;
	}
	
	@Column(name="ALIAS_")
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias=alias;
	}
	@Column(name="MOREINFO_")
	public String getMoreinfo() {
		return moreinfo;
	}
	public void setMoreinfo(String moreinfo) {
		this.moreinfo=moreinfo;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "fTypeName")
    public Collection<Fpici> getFpici() {
		return fpici;
	}
	public void setFpici(Collection<Fpici> fpici) {
		this.fpici=fpici;
	}
	@Transient
	public Collection<Subject> getSubject() {
		return subject;
	}
	public void setSubject(Collection<Subject> subject) {
		this.subject = subject;
	}

}