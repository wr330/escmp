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
@Table(name="Fighterxzh")
public class  Fighterxzh implements Serializable {
	private static final long serialVersionUID = 1L;
    public Fighterxzh(){}
    
	private String outfactoryno ;
	private String id ;
	private String piciname ;
	private String ftypename;
	private String filename;

	@Id
	@Column(name="id", unique = true, nullable = false) 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}
	@Column(name = "OutFactoryNo", unique = true, nullable = false)
	public String getOutfactoryno() {
		return outfactoryno;
	}
	public void setOutfactoryno(String outfactoryno) {
		this.outfactoryno=outfactoryno;
	}
	@Column(name = "ftypeName")
	public String getFtypename() {
		return ftypename;
	}
	public void setFtypename(String ftypename) {
		this.ftypename = ftypename;
	}	
	@Column(name = "piciName")
	public String getPiciname() {
		return piciname;
	}
	public void setPiciname(String piciname) {
		this.piciname = piciname;
	}
	@Column(name = "fileName")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}