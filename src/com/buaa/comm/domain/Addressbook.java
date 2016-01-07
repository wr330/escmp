package com.buaa.comm.domain;

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

import com.buaa.comm.domain.Addressbookdepart;

@Entity
@Table(name="Comm_AddressBook")
public class  Addressbook implements Serializable {
	private static final long serialVersionUID = 1L;
    public Addressbook(){}
   public Addressbook(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String position ;
	private String email ;
	private String fax ;
	private String name ;
	private String note ;
	private String telephone ;
	private String mobilephone ;
	private String miji ;

	private Addressbookdepart addressbookdepart;


	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="Position")
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position=position;
	}
	@Column(name="Email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	@Column(name="Fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax=fax;
	}
	@Column(name="Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="Note")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note=note;
	}
	@Column(name="telephone")
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone=telephone;
	}
	@Column(name="MobilePhone")
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone=mobilephone;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "AddressBookDepart")
    public Addressbookdepart getAddressbookdepart() {
		return addressbookdepart;
	}
	public void setAddressbookdepart(Addressbookdepart addressbookdepart) {
		this.addressbookdepart=addressbookdepart;
	}

}