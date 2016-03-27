package com.bstek.bdf2.core.service.impl;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.orm.jdbc.JdbcDao;
import com.bstek.bdf2.core.service.IUserService;
import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;


public class DefaultUserService extends JdbcDao implements IUserService {
	private PasswordEncoder passwordEncoder;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql="select * from Sys_User where Username=?";
        List<IUser> users=this.getJdbcTemplate().query(sql, new Object[]{username}, new DefaultUserRowMapper());
        if(users.size()==0){
            throw new UsernameNotFoundException("User ["+username+"]  is not exist!");
        }
        return users.get(0);
    }
    
    public void loadPageUsers(Page<IUser> page, String companyId,
            Criteria criteria) {
//        String sql="select * from Sys_User where CompanyId='" + companyId + "'";
//        Collection<IUser> users=this.getJdbcTemplate().query(sql,new UserMapper());
//        page.setEntities(users);
        String sql = "SELECT * FROM Sys_User x WHERE x.Username != 'admin' and ";
		ParseResult result = this.parseCriteria(criteria, false, "x");
		if (result != null) {
			StringBuffer sb = result.getAssemblySql();
			Map<String, Object> valueMap = result.getValueMap();
			valueMap.put("companyId", companyId);
			sql += sb.toString() + " and x.companyId=?";
			this.pagingQuery(page, sql, valueMap.values().toArray(),
					new DefaultUserRowMapper());
		} else {
			this.pagingQuery(page, sql + "x.companyId=?",
					new Object[] { companyId }, new DefaultUserRowMapper());
		}
    }
    public Collection<IUser> loadUsersByDeptId(String deptId) {
		String sql = "select * from Sys_User";
		if(deptId != null)
				sql += " where Username in (select USERNAME_ from BDF2_USER_DEPT where DEPT_ID_ ='" + deptId + "')";
		return this.getJdbcTemplate().query(sql, new DefaultUserRowMapper());
    }
    public Collection<IUser> loadUsersByRole(String roleName) {
		String sql = "select * from Sys_User where Username in (select USERNAME_ from BDF2_ROLE_MEMBER where GRANTED_ = 1 and ROLE_ID_ in (select ID_ from BDF2_ROLE where NAME_ ='" + roleName + "'))";
		return this.getJdbcTemplate().query(sql, new DefaultUserRowMapper());
	}
    public String checkPassword(String username, String password) {
    	DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
		String salt = user.getSalt();
		String usePassword = user.getPassword();
		usePassword = usePassword.substring(1);
		if (!passwordEncoder.isPasswordValid(usePassword, password, salt)) {
			return "密码不正确";
		} else {
			return null;
		}
    }
    public void changePassword(String username, String newPassword) {
    	String sql = "UPDATE Sys_User SET Password=?,Salt=? WHERE Username=?";
    	int salt = RandomUtils.nextInt(1000);
    	String password =  "$" + passwordEncoder.encodePassword(newPassword, salt);
    	this.getJdbcTemplate().update(
			sql,
			new Object[] {
					password,
					String.valueOf(salt), username });
    }
    public void registerAdministrator(String username, String cname,
            String ename, String password, String email, String mobile,
            String companyId) {
    	String sql = "INSERT INTO BDF2_USER(Username,Password,Salt,Cname,Male,Birthday,Administrator,LeagueMember,PartyMember,Enabled,TechnicalLevel,CompanyId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		int salt = RandomUtils.nextInt(1000);
		password = passwordEncoder.encodePassword(password, salt);
		this.getJdbcTemplate().update(
				sql,new Object[] { username,password,String.valueOf(salt),cname, true, null,true, false,false,true,"",companyId});
    }
    public IUser newUserInstance(String username) {
        return new DefaultUser(username);
    }
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
    class DefaultUserRowMapper implements RowMapper<IUser>{
        public IUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DefaultUser user=new DefaultUser();
            user.setCname(rs.getString("Cname"));
            user.setUsername(rs.getString("Username"));
    		user.setAdministrator(rs.getBoolean("Administrator"));
    		user.setBirthday(rs.getDate("Birthday"));
    		user.setCompanyId(rs.getString("CompanyId"));
    		user.setEnabled(rs.getBoolean("Enabled"));
    		user.setMale(rs.getBoolean("Male"));
    		user.setPassword(rs.getString("Password"));
    		user.setSalt(rs.getString("Salt"));
    		user.setLeagueMember(rs.getBoolean("LeagueMember"));
    		user.setPartyMember(rs.getBoolean("PartyMember"));
    		user.setTechnicalLevel(rs.getString("TechnicalLevel"));
    		user.setIp(rs.getString("IP"));
    		user.setMiji(rs.getString("miji"));
    		user.setLoginCount(rs.getInt("loginCount"));
    		user.setLock(rs.getBoolean("Lock"));
            return user;
        }
    }
}