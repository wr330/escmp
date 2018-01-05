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
@Table(name="COMM_ADDRESSBOOK")
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
	private int miji ;

	private Addressbookdepart addressbookdepart;


	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="POSITION_")
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position=position;
	}
	@Column(name="EMAIL_")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	@Column(name="FAX_")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax=fax;
	}
	@Column(name="NAME_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="NOTE_")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note=note;
	}
	@Column(name="TELEPHONE_")
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone=telephone;
	}
	@Column(name="MOBILEPHONE_")
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone=mobilephone;
	}
	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ADDRESSBOOKDEPART_")
    public Addressbookdepart getAddressbookdepart() {
		return addressbookdepart;
	}
	public void setAddressbookdepart(Addressbookdepart addressbookdepart) {
		this.addressbookdepart=addressbookdepart;
	}

}