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

import com.buaa.comm.domain.Btreport;

@Entity
@Table(name="Comm_AppendixDocument")
public class  Appendixdocument implements Serializable {
	private static final long serialVersionUID = 1L;
    public Appendixdocument(){}
   public Appendixdocument(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String efile ;

	private Btreport btreport;
	
	private String miji ;


	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="EFile")
	public String getEfile() {
		return efile;
	}
	public void setEfile(String efile) {
		this.efile=efile;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "BtReport")
    public Btreport getBtreport() {
		return btreport;
	}
	public void setBtreport(Btreport btreport) {
		this.btreport=btreport;
	}

}