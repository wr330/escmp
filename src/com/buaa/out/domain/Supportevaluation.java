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

	private String supportGrade ;
	private Date supportEndTime ;
	private String supportAddress ;
	private String feedbackQuestion ;
	private Date supportStartTime ;
	private String supportSuggest ;
	private String customerSatisfactionSurvey ;
	private String miji ;



	@Id
	@Column(name = "Oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}

	@Column(name="SupportGrade")
	public String getSupportGrade() {
		return supportGrade;
	}
	public void setSupportGrade(String supportGrade) {
		this.supportGrade = supportGrade;
	}
	
	@Column(name="SupportEndTime")
	public Date getSupportEndTime() {
		return supportEndTime;
	}
	public void setSupportEndTime(Date supportEndTime) {
		this.supportEndTime = supportEndTime;
	}
	
	@Column(name="SupportAddress")
	public String getSupportAddress() {
		return supportAddress;
	}
	public void setSupportAddress(String supportAddress) {
		this.supportAddress = supportAddress;
	}
	
	@Column(name="FeedbackQuestion")
	public String getFeedbackQuestion() {
		return feedbackQuestion;
	}
	public void setFeedbackQuestion(String feedbackQuestion) {
		this.feedbackQuestion = feedbackQuestion;
	}
	
	@Column(name="SupportStartTime")
	public Date getSupportStartTime() {
		return supportStartTime;
	}
	public void setSupportStartTime(Date supportStartTime) {
		this.supportStartTime = supportStartTime;
	}
	
	@Column(name="SupportSuggest")
	public String getSupportSuggest() {
		return supportSuggest;
	}
	public void setSupportSuggest(String supportSuggest) {
		this.supportSuggest = supportSuggest;
	}
	
	@Column(name="CustomerSatisfactionSurvey")
	public String getCustomerSatisfactionSurvey() {
		return customerSatisfactionSurvey;
	}
	public void setCustomerSatisfactionSurvey(String customerSatisfactionSurvey) {
		this.customerSatisfactionSurvey = customerSatisfactionSurvey;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}

}