package com.buaa.sys.domain;

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


@Entity
@Table(name="Sys_Dictionary")
public class  Dictionary implements Serializable {
	private static final long serialVersionUID = 1L;
    public Dictionary(){}
   public Dictionary(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private Integer status ;
	private String name ;
	private String dictype ;
	private String description ;



	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="Status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status=status;
	}
	@Column(name="Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="DicType")
	public String getDictype() {
		return dictype;
	}
	public void setDictype(String dictype) {
		this.dictype=dictype;
	}
	@Column(name="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description=description;
	}


}