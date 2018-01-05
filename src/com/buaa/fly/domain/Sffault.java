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

@Entity
@Table(name = "FLY_SFFAULT")
public class Sffault implements Serializable {
	private static final long serialVersionUID = 1L;

	public Sffault() {
	}

	private String ftype;
	private String id;
	private String name;
	private String relatebumen;
	private String type;
	private String yuanyinfx;
	private String deal;
	private String qingkuang;
	private String judge;
	private String gzfs;
	private String partname;
	private String repeat;
	private String summary;
	private String status;
	private String faulteffect;
	private String system;
	private String foundopp;
	private String xccharger;
	private String miaoshu;
	private Date date;
	private String laiyuan;
	private String fighterno;
	private String partmodel;
	private String yiliuwt;
	private String zongshi;
	private String zerenren;
	private String jiancanr;
	private Date inputdate;
	private String pici;
	private String danwei;
	private String waichangxxms;
	private String guiling;
	private String relatemajor;
	private String majorsystem;
	private String repeattime;
	private String fileno;
	private String adrs;
	private String inputer;
	private String xianchanggz;
	private Integer sequence;
	private String gengxinjl;
	private String bumen;
	private String result;
	private String fdata;
	private int miji;

	@Column(name = "FTYPE_")
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	@Id
	@Column(name = "OID_")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME_")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "RELATEBUMEN_")
	public String getRelatebumen() {
		return relatebumen;
	}

	public void setRelatebumen(String relatebumen) {
		this.relatebumen = relatebumen;
	}

	@Column(name = "TYPE_")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "YUANYINFX_")
	public String getYuanyinfx() {
		return yuanyinfx;
	}

	public void setYuanyinfx(String yuanyinfx) {
		this.yuanyinfx = yuanyinfx;
	}

	@Column(name = "DEAL_")
	public String getDeal() {
		return deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	@Column(name = "QINGKUANG_")
	public String getQingkuang() {
		return qingkuang;
	}

	public void setQingkuang(String qingkuang) {
		this.qingkuang = qingkuang;
	}

	@Column(name = "JUDGE_")
	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	@Column(name = "GZFS_")
	public String getGzfs() {
		return gzfs;
	}

	public void setGzfs(String gzfs) {
		this.gzfs = gzfs;
	}

	@Column(name = "PARTNAME_")
	public String getPartname() {
		return partname;
	}

	public void setPartname(String partname) {
		this.partname = partname;
	}

	@Column(name = "REPEAT_")
	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	@Column(name = "SUMMARY_")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "STATUS_")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "FAULTEFFECT_")
	public String getFaulteffect() {
		return faulteffect;
	}

	public void setFaulteffect(String faulteffect) {
		this.faulteffect = faulteffect;
	}

	@Column(name = "SYSTEM_")
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Column(name = "FOUNDOPP_")
	public String getFoundopp() {
		return foundopp;
	}

	public void setFoundopp(String foundopp) {
		this.foundopp = foundopp;
	}

	@Column(name = "XCCHARGER_")
	public String getXccharger() {
		return xccharger;
	}

	public void setXccharger(String xccharger) {
		this.xccharger = xccharger;
	}

	@Column(name = "MIAOSHU_")
	public String getMiaoshu() {
		return miaoshu;
	}

	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}

	@Column(name = "SFFAULTDATE_")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "LAIYUAN_")
	public String getLaiyuan() {
		return laiyuan;
	}

	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}

	@Column(name = "FIGHTERNO_")
	public String getFighterno() {
		return fighterno;
	}

	public void setFighterno(String fighterno) {
		this.fighterno = fighterno;
	}

	@Column(name = "PARTMODEL_")
	public String getPartmodel() {
		return partmodel;
	}

	public void setPartmodel(String partmodel) {
		this.partmodel = partmodel;
	}

	@Column(name = "YILIUWT_")
	public String getYiliuwt() {
		return yiliuwt;
	}

	public void setYiliuwt(String yiliuwt) {
		this.yiliuwt = yiliuwt;
	}

	@Column(name = "ZONGSHI_")
	public String getZongshi() {
		return zongshi;
	}

	public void setZongshi(String zongshi) {
		this.zongshi = zongshi;
	}

	@Column(name = "ZERENREN_")
	public String getZerenren() {
		return zerenren;
	}

	public void setZerenren(String zerenren) {
		this.zerenren = zerenren;
	}

	@Column(name = "JIANCANR_")
	public String getJiancanr() {
		return jiancanr;
	}

	public void setJiancanr(String jiancanr) {
		this.jiancanr = jiancanr;
	}

	@Column(name = "INPUTDATE_")
	public Date getInputdate() {
		return inputdate;
	}

	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}

	@Column(name = "PICI_")
	public String getPici() {
		return pici;
	}

	public void setPici(String pici) {
		this.pici = pici;
	}

	@Column(name = "DANWEI_")
	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	@Column(name = "WAICHANGXXMS_")
	public String getWaichangxxms() {
		return waichangxxms;
	}

	public void setWaichangxxms(String waichangxxms) {
		this.waichangxxms = waichangxxms;
	}

	@Column(name = "GUILING_")
	public String getGuiling() {
		return guiling;
	}

	public void setGuiling(String guiling) {
		this.guiling = guiling;
	}

	@Column(name = "RELATEMAJOR_")
	public String getRelatemajor() {
		return relatemajor;
	}

	public void setRelatemajor(String relatemajor) {
		this.relatemajor = relatemajor;
	}

	@Column(name = "MAJORSYSTEM_")
	public String getMajorsystem() {
		return majorsystem;
	}

	public void setMajorsystem(String majorsystem) {
		this.majorsystem = majorsystem;
	}

	@Column(name = "REPEATTIME_")
	public String getRepeattime() {
		return repeattime;
	}

	public void setRepeattime(String repeattime) {
		this.repeattime = repeattime;
	}

	@Column(name = "FILENO_")
	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	@Column(name = "ADRS_")
	public String getAdrs() {
		return adrs;
	}

	public void setAdrs(String adrs) {
		this.adrs = adrs;
	}

	@Column(name = "INPUTER_")
	public String getInputer() {
		return inputer;
	}

	public void setInputer(String inputer) {
		this.inputer = inputer;
	}

	@Column(name = "XIANCHANGGZ_")
	public String getXianchanggz() {
		return xianchanggz;
	}

	public void setXianchanggz(String xianchanggz) {
		this.xianchanggz = xianchanggz;
	}

	@Column(name = "SEQUENCE_")
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Column(name = "GENGXINJL_")
	public String getGengxinjl() {
		return gengxinjl;
	}

	public void setGengxinjl(String gengxinjl) {
		this.gengxinjl = gengxinjl;
	}

	@Column(name = "BUMEN_")
	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}

	@Column(name = "RESULT_")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "MIJI_")
	public int getMiji() {
		return miji;
	}

	public void setMiji(int miji) {
		this.miji = miji;
	}

	@Column(name = "FDATA_")
	public String getFdata() {
		return fdata;
	}

	public void setFdata(String fdata) {
		this.fdata = fdata;
	}
}