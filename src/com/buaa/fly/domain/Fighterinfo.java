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
@Table(name="Fighterinfo")
public class  Fighterinfo implements Serializable {
	private static final long serialVersionUID = 1L;
    public Fighterinfo(){}
    
	private String outfactoryno ;
	private Integer ftypeid ;
	private Integer piciid ;
	private Integer id ;
	private Date firstfdate ;
	private String userno ;
	private Date outdate ;

	private Fpici fpici;
	private Ftypes ftypes;

	@Id
	@Column(name = "OutFactoryNo", unique = true, nullable = false)
	public String getOutfactoryno() {
		return outfactoryno;
	}
	public void setOutfactoryno(String outfactoryno) {
		this.outfactoryno=outfactoryno;
	}
	@Column(name="ftypeid")
	public Integer getFtypeid() {
		return ftypeid;
	}
	public void setFtypeid(Integer ftypeid) {
		this.ftypeid=ftypeid;
	}
	@Column(name="PiCiid")
	public Integer getPiciid() {
		return piciid;
	}
	public void setPiciid(Integer piciid) {
		this.piciid=piciid;
	}
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id=id;
	}
	@Column(name="FirstFDate")
	public Date getFirstfdate() {
		return firstfdate;
	}
	public void setFirstfdate(Date firstfdate) {
		this.firstfdate=firstfdate;
	}
	@Column(name="UserNo")
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno=userno;
	}
	@Column(name="OutDate")
	public Date getOutdate() {
		return outdate;
	}
	public void setOutdate(Date outdate) {
		this.outdate=outdate;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ftypeName")
    public Ftypes getFtypes() {
		return ftypes;
	}
	public void setFtypes(Ftypes ftypes) {
		this.ftypes=ftypes;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "piciName")
	public Fpici getFpici() {
		return fpici;
	}
	public void setFpici(Fpici fpici) {
		this.fpici=fpici;
	}
}