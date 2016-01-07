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
@Table(name="Comm_TechnicAndContract")
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
	private String miji ;


	@Id
		@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="ContractName")
	public String getContractname() {
		return contractname;
	}
	public void setContractname(String contractname) {
		this.contractname=contractname;
	}
	@Column(name="ProjectNumber")
	public String getProjectnumber() {
		return projectnumber;
	}
	public void setProjectnumber(String projectnumber) {
		this.projectnumber=projectnumber;
	}
	@Column(name="PaymentDate")
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate=paymentdate;
	}
	@Column(name="SignContractDate")
	public Date getSigncontractdate() {
		return signcontractdate;
	}
	public void setSigncontractdate(Date signcontractdate) {
		this.signcontractdate=signcontractdate;
	}
	@Column(name="Major")
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major=major;
	}
	@Column(name="SubmitArgumentReportDate")
	public Date getSubmitargumentreportdate() {
		return submitargumentreportdate;
	}
	public void setSubmitargumentreportdate(Date submitargumentreportdate) {
		this.submitargumentreportdate=submitargumentreportdate;
	}
	@Column(name="ContractSubmitedAppoveDate")
	public Date getContractsubmitedappovedate() {
		return contractsubmitedappovedate;
	}
	public void setContractsubmitedappovedate(Date contractsubmitedappovedate) {
		this.contractsubmitedappovedate=contractsubmitedappovedate;
	}
	@Column(name="PaymentMethod")
	public String getPaymentmethod() {
		return paymentmethod;
	}
	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod=paymentmethod;
	}
	@Column(name="ProjectName")
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname=projectname;
	}
	@Column(name="ContractNumber")
	public String getContractnumber() {
		return contractnumber;
	}
	public void setContractnumber(String contractnumber) {
		this.contractnumber=contractnumber;
	}
	@Column(name="ContractMoney")
	public String getContractmoney() {
		return contractmoney;
	}
	public void setContractmoney(String contractmoney) {
		this.contractmoney=contractmoney;
	}
	@Column(name="ResponsiblePersion")
	public String getResponsiblepersion() {
		return responsiblepersion;
	}
	public void setResponsiblepersion(String responsiblepersion) {
		this.responsiblepersion=responsiblepersion;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}


}