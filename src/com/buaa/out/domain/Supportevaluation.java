package com.buaa.out.domain;

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
import java.lang.String;

@Entity
@Table(name="Out_SupportEvaluation")
public class  Supportevaluation implements Serializable {
	private static final long serialVersionUID = 1L;
    public Supportevaluation(){}
   public Supportevaluation(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String supportgrade ;
	private Date supportendtime ;
	private String supportaddress ;
	private String feedbackquestion ;
	private Date supportstarttime ;
	private String customersatisfactionsurvey ;



	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="SupportGrade")
	public String getSupportgrade() {
		return supportgrade;
	}
	public void setSupportgrade(String supportgrade) {
		this.supportgrade=supportgrade;
	}
	@Column(name="SupportEndTime")
	public Date getSupportendtime() {
		return supportendtime;
	}
	public void setSupportendtime(Date supportendtime) {
		this.supportendtime=supportendtime;
	}
	@Column(name="SupportAddress")
	public String getSupportaddress() {
		return supportaddress;
	}
	public void setSupportaddress(String supportaddress) {
		this.supportaddress=supportaddress;
	}
	@Column(name="FeedbackQuestion")
	public String getFeedbackquestion() {
		return feedbackquestion;
	}
	public void setFeedbackquestion(String feedbackquestion) {
		this.feedbackquestion=feedbackquestion;
	}
	@Column(name="SupportStartTime")
	public Date getSupportstarttime() {
		return supportstarttime;
	}
	public void setSupportstarttime(Date supportstarttime) {
		this.supportstarttime=supportstarttime;
	}
	@Column(name="CustomerSatisfactionSurvey")
	public String getCustomersatisfactionsurvey() {
		return customersatisfactionsurvey;
	}
	public void setCustomersatisfactionsurvey(String customersatisfactionsurvey) {
		this.customersatisfactionsurvey=customersatisfactionsurvey;
	}


}