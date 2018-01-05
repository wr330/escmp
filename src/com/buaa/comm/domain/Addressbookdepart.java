package com.buaa.comm.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.buaa.comm.domain.Addressbook;
import com.buaa.fly.domain.Subject;

@Entity
@Table(name="COMM_ADDRESSBOOKDEPART")
public class  Addressbookdepart implements Serializable {
	private static final long serialVersionUID = 1L;
    public Addressbookdepart(){}
    public Addressbookdepart(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String parentnode ;
	private String code ;
	private String address ;
	private String name ;
	private String postcode ;
	private List<Addressbookdepart> children;

	private Collection<Addressbook> addressbook;

	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="PARENTNODE_")
	public String getParentnode() {
		return parentnode;
	}
	public void setParentnode(String parentnode) {
		this.parentnode=parentnode;
	}
	@Column(name="CODE_")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code=code;
	}
	@Column(name="ADDRESS_")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	@Column(name="NAME_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="POSTCODE_")
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode=postcode;
	}
	@Transient
	public List<Addressbookdepart> getChildren() {
		return children;
	}
	public void setChildren(List<Addressbookdepart> children) {
		this.children = children;
	}


	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,mappedBy = "addressbookdepart")
    public Collection<Addressbook> getAddressbook() {
		return addressbook;
	}
	public void setAddressbook(Collection<Addressbook> addressbook) {
		this.addressbook=addressbook;
	}
}