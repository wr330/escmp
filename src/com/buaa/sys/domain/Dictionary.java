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
@Table(name="SYS_DICTIONARY")
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
	private Integer numericalOrder;



	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="STATUS_")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status=status;
	}
	@Column(name="NAME_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="DICTYPE_")
	public String getDictype() {
		return dictype;
	}
	public void setDictype(String dictype) {
		this.dictype=dictype;
	}
	@Column(name="DESCRIPTION_")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description=description;
	}
	@Column(name="NUMERICALORDER_")
	public Integer getNumericalOrder() {
		return numericalOrder;
	}
	public void setNumericalOrder(Integer numericalOrder) {
		this.numericalOrder = numericalOrder;
	}


}