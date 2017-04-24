package com.bstek.bdf2.core.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bstek.bdf2.core.business.AbstractUser;
import com.bstek.bdf2.core.business.IDept;
import com.bstek.bdf2.core.business.IPosition;
import com.bstek.bdf2.core.model.Group;
import com.bstek.bdf2.core.model.Role;

@Entity
@Table(name="Sys_User")
public class DefaultUser extends AbstractUser  implements java.io.Serializable{
    private static final long serialVersionUID = -1363793377621174845L;
	@Id
	@Column(name="Username",length=60)
	private String username;
	@Column(name="Cname",length=50,nullable=false)
	private String cname;
    @Column(name="Administrator",nullable=false)
	private boolean administrator;
    @Column(name="Birthday")
	private Date birthday;
    @Column(name="CompanyId",length=60,nullable=false)
	private String companyId;
    @Column(name="Enabled",nullable=false)
	private boolean enabled;
    @Column(name="Male",nullable=false)
	private boolean male;
    @Column(name="Password",length=70,nullable=false)
	private String password;
    @Column(name="Salt",length=50,nullable=false)
	private String salt;
    @Column(name="LeagueMember",nullable=false)
	private boolean leagueMember;
    @Column(name="PartyMember",nullable=false)
	private boolean partyMember;
    @Column(name="TechnicalLevel",length=50)
	private String technicalLevel;
    @Column(name="miji",length=50)
	private String miji;
    @Column(name="IP",length=50)
	private String ip;
    @Column(name="LoginCount",length=50)
	private int loginCount;
    @Column(name="Lock",length=50)
	private boolean lock;
    @Column(name="Department",length=50)
   	private String department;
    @Column(name="Position",length=100)
   	private String position;
    @Column(name="AtteAirc",length=50)
   	private String atteAirc;
	@Transient
    private List<IDept> depts;
	@Transient
    private List<IPosition> positions;
	@Transient
    private List<Role> roles;
	@Transient
    private List<Group> groups;
    
	public DefaultUser(){}
	public DefaultUser(String username){
		this.username=username;
	}
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public boolean isAdministrator() {
		return administrator;
	}
	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isMale() {
		return male;
	}
	public void setMale(boolean male) {
		this.male = male;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public boolean isLeagueMember() {
		return leagueMember;
	}
	public void setLeagueMember(boolean leagueMember) {
		this.leagueMember = leagueMember;
	}
	public boolean isPartyMember() {
		return partyMember;
	}
	public void setPartyMember(boolean partyMember) {
		this.partyMember = partyMember;
	}
	public String getTechnicalLevel() {
		return technicalLevel;
	}
	public void setTechnicalLevel(String technicalLevel) {
		this.technicalLevel = technicalLevel;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
    public String getMiji() {
		return miji;
	}
	public void setMiji(String miji) {
		this.miji = miji;
	}
	/**
     * 当前用户所在部门
     * */
    public List<IDept> getDepts() {
        return depts;
    }
    /**
     * 当前用户所拥有的岗位
     * */
    public List<IPosition> getPositions() {
        return positions;
    }
    public void setPositions(List<IPosition> positions) {
        this.positions = positions;
    }
    public void setDepts(List<IDept> depts) {
        this.depts = depts;
    }
    /**
     * 当前用户所拥有的角色，权限需要使用
     * */
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    /**
     * 当前用户所在群组
     * */
    public List<Group> getGroups() {
        return groups;
    }
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    /**
     * 用户的其它元数据信息，如果我们的应用用不到，可直接返回null
     * */
    public Map<String, Object> getMetadata() {
        return null;
    }

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getEname() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getMobile() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int count) {
		this.loginCount = count;
	}
	public boolean getLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAtteAirc() {
		return atteAirc;
	}
	public void setAtteAirc(String atteAirc) {
		this.atteAirc = atteAirc;
	}
	
}