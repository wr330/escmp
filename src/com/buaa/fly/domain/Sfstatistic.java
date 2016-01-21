package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.math.BigDecimal;

@Entity
@Table(name="Fly_SFstatistic")
public class  Sfstatistic implements Serializable {
	private static final long serialVersionUID = 1L;
    public Sfstatistic(){}


	private String ftype ;
	private Date fdate ;
	private String id ;
	private Tasklist taskNo ;
	private String jiaci ;
	private String config ;
	private String address ;
	private String subject ;
	private Integer bytes2 ;
	private String filename4 ;
	private Integer bytes1 ;
	private String pilot ;
	private String checker ;
	private byte[] datablock3 ;
	private String dailymajor ;
	private String jiangpingmajor ;
	private String feixingqk ;
	private String gdasdirjp ;
	private Date timeland ;
	private String jiangpingname ;
	private Date timeqifei ;
	private String filename3 ;
	private String filename2 ;
	private String filename5 ;
	private byte[] datablock5 ;
	private String pilotjp ;
	private byte[] datablock1 ;
	private String xianchang ;
	private Integer bytes4 ;
	private String jiankongjl ;
	private String action ;
	private Integer yuyou ;
	private String dataaddr ;
	private String towerdir ;
	private Integer sequence ;
	private Integer bytes3 ;
	private Integer jiayou ;
	private String towerdirjp ;
	private byte[] datablock4 ;
	private String result ;
	private String dailyname ;
	private String pilotpj ;
	private String mainsubject ;
	private String summary ;
	private String fighterno ;
	private Integer bytes5 ;
	private String gdasdir ;
	private String filename1 ;
	private byte[] datablock2 ;
	private String content ;
	private String miji ;





	@Column(name="ftype")
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype=ftype;
	}
	@Column(name="fdate")
	public Date getFdate() {
		return fdate;
	}
	public void setFdate(Date fdate) {
		this.fdate=fdate;
	}
	@Id
	@Column(name="Oid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "TaskNo")
    public Tasklist getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(Tasklist taskNo) {
		this.taskNo = taskNo;
	}
	
	@Column(name="JiaCi")
	public String getJiaci() {
		return jiaci;
	}
	public void setJiaci(String jiaci) {
		this.jiaci=jiaci;
	}
	@Column(name="config")
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config=config;
	}
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	@Column(name="subject")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject=subject;
	}
	@Column(name="bytes2")
	public Integer getBytes2() {
		return bytes2;
	}
	public void setBytes2(Integer bytes2) {
		this.bytes2=bytes2;
	}
	@Column(name="FileName4")
	public String getFilename4() {
		return filename4;
	}
	public void setFilename4(String filename4) {
		this.filename4=filename4;
	}
	@Column(name="bytes1")
	public Integer getBytes1() {
		return bytes1;
	}
	public void setBytes1(Integer bytes1) {
		this.bytes1=bytes1;
	}
	@Column(name="pilot")
	public String getPilot() {
		return pilot;
	}
	public void setPilot(String pilot) {
		this.pilot=pilot;
	}
	@Column(name="Checker")
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker=checker;
	}
	@Column(name="datablock3")
	public byte[] getDatablock3() {
		return datablock3;
	}
	public void setDatablock3(byte[] datablock3) {
		this.datablock3=datablock3;
	}
	@Column(name="DailyMajor")
	public String getDailymajor() {
		return dailymajor;
	}
	public void setDailymajor(String dailymajor) {
		this.dailymajor=dailymajor;
	}
	@Column(name="JiangPingMajor")
	public String getJiangpingmajor() {
		return jiangpingmajor;
	}
	public void setJiangpingmajor(String jiangpingmajor) {
		this.jiangpingmajor=jiangpingmajor;
	}
	@Column(name="FeiXingQK")
	public String getFeixingqk() {
		return feixingqk;
	}
	public void setFeixingqk(String feixingqk) {
		this.feixingqk=feixingqk;
	}
	@Column(name="GDASDirJP")
	public String getGdasdirjp() {
		return gdasdirjp;
	}
	public void setGdasdirjp(String gdasdirjp) {
		this.gdasdirjp=gdasdirjp;
	}
	@Column(name="TimeLand")
	public Date getTimeland() {
		return timeland;
	}
	public void setTimeland(Date timeland) {
		this.timeland=timeland;
	}
	@Column(name="JiangPingName")
	public String getJiangpingname() {
		return jiangpingname;
	}
	public void setJiangpingname(String jiangpingname) {
		this.jiangpingname=jiangpingname;
	}
	@Column(name="TimeQifei")
	public Date getTimeqifei() {
		return timeqifei;
	}
	public void setTimeqifei(Date timeqifei) {
		this.timeqifei=timeqifei;
	}
	@Column(name="FileName3")
	public String getFilename3() {
		return filename3;
	}
	public void setFilename3(String filename3) {
		this.filename3=filename3;
	}
	@Column(name="FileName2")
	public String getFilename2() {
		return filename2;
	}
	public void setFilename2(String filename2) {
		this.filename2=filename2;
	}
	@Column(name="FileName5")
	public String getFilename5() {
		return filename5;
	}
	public void setFilename5(String filename5) {
		this.filename5=filename5;
	}
	@Column(name="datablock5")
	public byte[] getDatablock5() {
		return datablock5;
	}
	public void setDatablock5(byte[] datablock5) {
		this.datablock5=datablock5;
	}
	@Column(name="PilotJP")
	public String getPilotjp() {
		return pilotjp;
	}
	public void setPilotjp(String pilotjp) {
		this.pilotjp=pilotjp;
	}
	@Column(name="datablock1")
	public byte[] getDatablock1() {
		return datablock1;
	}
	public void setDatablock1(byte[] datablock1) {
		this.datablock1=datablock1;
	}
	@Column(name="Xianchang")
	public String getXianchang() {
		return xianchang;
	}
	public void setXianchang(String xianchang) {
		this.xianchang=xianchang;
	}
	@Column(name="bytes4")
	public Integer getBytes4() {
		return bytes4;
	}
	public void setBytes4(Integer bytes4) {
		this.bytes4=bytes4;
	}
	@Column(name="JianKongJL")
	public String getJiankongjl() {
		return jiankongjl;
	}
	public void setJiankongjl(String jiankongjl) {
		this.jiankongjl=jiankongjl;
	}
	@Column(name="action")
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action=action;
	}
	@Column(name="YuYou")
	public Integer getYuyou() {
		return yuyou;
	}
	public void setYuyou(Integer yuyou) {
		this.yuyou=yuyou;
	}
	@Column(name="dataAddr")
	public String getDataaddr() {
		return dataaddr;
	}
	public void setDataaddr(String dataaddr) {
		this.dataaddr=dataaddr;
	}
	@Column(name="TowerDir")
	public String getTowerdir() {
		return towerdir;
	}
	public void setTowerdir(String towerdir) {
		this.towerdir=towerdir;
	}
	@Column(name="sequence")
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence=sequence;
	}
	@Column(name="bytes3")
	public Integer getBytes3() {
		return bytes3;
	}
	public void setBytes3(Integer bytes3) {
		this.bytes3=bytes3;
	}
	@Column(name="JiaYou")
	public Integer getJiayou() {
		return jiayou;
	}
	public void setJiayou(Integer jiayou) {
		this.jiayou=jiayou;
	}
	@Column(name="TowerDirJP")
	public String getTowerdirjp() {
		return towerdirjp;
	}
	public void setTowerdirjp(String towerdirjp) {
		this.towerdirjp=towerdirjp;
	}
	@Column(name="datablock4")
	public byte[] getDatablock4() {
		return datablock4;
	}
	public void setDatablock4(byte[] datablock4) {
		this.datablock4=datablock4;
	}
	@Column(name="Result")
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result=result;
	}
	@Column(name="DailyName")
	public String getDailyname() {
		return dailyname;
	}
	public void setDailyname(String dailyname) {
		this.dailyname=dailyname;
	}
	@Column(name="PilotPJ")
	public String getPilotpj() {
		return pilotpj;
	}
	public void setPilotpj(String pilotpj) {
		this.pilotpj=pilotpj;
	}
	@Column(name="mainsubject")
	public String getMainsubject() {
		return mainsubject;
	}
	public void setMainsubject(String mainsubject) {
		this.mainsubject=mainsubject;
	}
	@Column(name="summary")
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary=summary;
	}
	@Column(name="fighterNo")
	public String getFighterno() {
		return fighterno;
	}
	public void setFighterno(String fighterno) {
		this.fighterno=fighterno;
	}
	@Column(name="bytes5")
	public Integer getBytes5() {
		return bytes5;
	}
	public void setBytes5(Integer bytes5) {
		this.bytes5=bytes5;
	}
	@Column(name="GDASDir")
	public String getGdasdir() {
		return gdasdir;
	}
	public void setGdasdir(String gdasdir) {
		this.gdasdir=gdasdir;
	}
	@Column(name="FileName1")
	public String getFilename1() {
		return filename1;
	}
	public void setFilename1(String filename1) {
		this.filename1=filename1;
	}
	@Column(name="datablock2")
	public byte[] getDatablock2() {
		return datablock2;
	}
	public void setDatablock2(byte[] datablock2) {
		this.datablock2=datablock2;
	}
	@Column(name="miji")
	public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	
	@Column(name="content")
	public void setContent(String content) {
		this.content = content;
	}
    public String getContent() {
    	return content;
	}


}