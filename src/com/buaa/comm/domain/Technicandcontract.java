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

import java.util.Date;

@Entity
@Table(name="COMM_TECHNICANDCONTRACT")
public class  Technicandcontract implements Serializable {
	private static final long serialVersionUID = 1L;
    public Technicandcontract(){}
   public Technicandcontract(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String contractname ;
	private String projectnumber ;
	private Date paymentdate ;
	private Date signcontractdate ;
	private String major ;
	private Date submitargumentreportdate ;
	private Date contractsubmitedappovedate ;
	private String paymentmethod ;
	private String projectname ;
	private String contractnumber ;
	private String contractmoney ;
	private String responsiblepersion ;
	private int miji ;


	@Id
		@Column(name = "OID_", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="CONTRACTNAME_")
	public String getContractname() {
		return contractname;
	}
	public void setContractname(String contractname) {
		this.contractname=contractname;
	}
	@Column(name="PROJECTNUMBER_")
	public String getProjectnumber() {
		return projectnumber;
	}
	public void setProjectnumber(String projectnumber) {
		this.projectnumber=projectnumber;
	}
	@Column(name="PAYMENTDATE_")
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate=paymentdate;
	}
	@Column(name="SIGNCONTRACTDATE_")
	public Date getSigncontractdate() {
		return signcontractdate;
	}
	public void setSigncontractdate(Date signcontractdate) {
		this.signcontractdate=signcontractdate;
	}
	@Column(name="MAJOR_")
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major=major;
	}
	@Column(name="SUBMITARGUMENTREPORTDATE_")
	public Date getSubmitargumentreportdate() {
		return submitargumentreportdate;
	}
	public void setSubmitargumentreportdate(Date submitargumentreportdate) {
		this.submitargumentreportdate=submitargumentreportdate;
	}
	@Column(name="CONTRACTSUBMITEDAPPOVEDATE_")
	public Date getContractsubmitedappovedate() {
		return contractsubmitedappovedate;
	}
	public void setContractsubmitedappovedate(Date contractsubmitedappovedate) {
		this.contractsubmitedappovedate=contractsubmitedappovedate;
	}
	@Column(name="PAYMENTMETHOD_")
	public String getPaymentmethod() {
		return paymentmethod;
	}
	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod=paymentmethod;
	}
	@Column(name="PROJECTNAME_")
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname=projectname;
	}
	@Column(name="CONTRACTNUMBER_")
	public String getContractnumber() {
		return contractnumber;
	}
	public void setContractnumber(String contractnumber) {
		this.contractnumber=contractnumber;
	}
	@Column(name="CONTRACTMONEY_")
	public String getContractmoney() {
		return contractmoney;
	}
	public void setContractmoney(String contractmoney) {
		this.contractmoney=contractmoney;
	}
	@Column(name="RESPONSIBLEPERSION_")
	public String getResponsiblepersion() {
		return responsiblepersion;
	}
	public void setResponsiblepersion(String responsiblepersion) {
		this.responsiblepersion=responsiblepersion;
	}
	@Column(name="MIJI_")
	public int getMiji() {
		return miji;
	}
	public void setMiji(int miji) {
		this.miji = miji;
	}


}