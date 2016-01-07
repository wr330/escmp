package com.bstek.bdf2.core.view.user;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.bstek.bdf2.core.CoreHibernateDao;
import com.bstek.bdf2.core.business.IDept;
import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.exception.NoneLoginException;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.bdf2.core.service.IDeptService;
import com.bstek.bdf2.core.service.IGroupService;
import com.bstek.bdf2.core.service.IRoleService;
import com.bstek.bdf2.core.service.IUserService;
import com.bstek.bdf2.core.service.MemberType;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

/**
 * @since 2013-1-29
 * @author Jacky.gao
 */
public class UserMaintain extends CoreHibernateDao {
	private PasswordEncoder passwordEncoder;
	private IUserService userService;
	private IDeptService deptService;
	private IRoleService roleService;
	private IGroupService groupService;
	
	@DataProvider
	public void loadUsers(Page<IUser> page, Criteria criteria) throws Exception {
		IUser user = ContextHolder.getLoginUser();
		if (user == null) {
			throw new NoneLoginException("Please login first");
		}
		String companyId=user.getCompanyId();
		if(StringUtils.isNotEmpty(getFixedCompanyId())){
			companyId=getFixedCompanyId();
		}
		userService.loadPageUsers(page,companyId, criteria);
	}
	//用于用户下拉框
	@DataProvider
    public Collection<IUser> loadUsers2(String deptId) {
        return userService.loadUsersByDeptId(deptId);
    }
	@DataProvider
	public Collection<IDept> loadUserDepts(String username) {
		return deptService.loadUserDepts(username);
	}

	@DataResolver
	public void saveUsers(Collection<DefaultUser> users) throws Exception {
		IUser loginuser=ContextHolder.getLoginUser();
		if(loginuser==null){
			throw new NoneLoginException("Please login first!");
		}
		String companyId=loginuser.getCompanyId();
		if(StringUtils.isNotEmpty(getFixedCompanyId())){
			companyId=getFixedCompanyId();
		}
		Session session = this.getSessionFactory().openSession();
		try{
			for (DefaultUser user : users) {
				EntityState state = EntityUtils.getState(user);
				if (state.equals(EntityState.NEW)) {
					String salt = String.valueOf(RandomUtils.nextInt(100));
					String password =  "$" + passwordEncoder.encodePassword(user.getPassword(), salt);
					user.setPassword(password);
					user.setSalt(salt);
					user.setCompanyId(companyId);
					session.save(user);
				} else if (state.equals(EntityState.MODIFIED)) {
					session.update(user);
				} else if (state.equals(EntityState.DELETED)) {
					roleService.deleteRoleMemeber(user.getUsername(), MemberType.User);
					groupService.deleteGroupMemeber(user.getUsername(), MemberType.User);
					session.delete(user);
					org.hibernate.Criteria c = session.createCriteria(UserDept.class);
					c.add(Restrictions.eq("username", user.getUsername()));
					List<UserDept> items = c.list();
					for (UserDept item : items) {
						session.delete(item);
					}
				}
			}
		}finally{
			session.flush();
			session.close();
		}
	}

	/**
	 * 这个方法用来判断在添加新用户时用户名是否已经存在
	 * 
	 * @param username
	 *            用户输入的用户名
	 */
	@Expose
	public String userIsExists(String username) {
		String hql = "select count(*) from " + DefaultUser.class.getName()
				+ " u where u.username = :username";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("username", username);
		int count = this.queryForInt(hql, parameterMap);

		String returnStr = null;
		if (count > 0) {
			returnStr = "此用户已存在！";
		}
		return returnStr;
	}

	/*
	 * 生成随机字符串
	 * 
	 * @param int length
	 * @return String sb.toString()
	 */
	private static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+0123456789";
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }
	/**
	 * 重置指定用户密码
	 * 
	 * @param username
	 * @return
	 */
	@Expose
	public String resetPassword(String username) {
		//Integer newPassword = (int) (Math.random() * 900000 + 100000); 
		String newPassword = getRandomString(8);
		userService.changePassword(username, newPassword);
		return newPassword.toString();
	}

	@Expose
	public void insertUserPosition(String username, String ids) {
		Session session = this.getSessionFactory().openSession();
		try{
			session.createQuery(
					"delete " + UserPosition.class.getName()
					+ " u where u.username = :username")
					.setString("username", username).executeUpdate();
			
			if (StringUtils.isNotEmpty(ids)) {
				UserPosition userPosition;
				for (String id : ids.split(",")) {
					userPosition = new UserPosition();
					userPosition.setId(UUID.randomUUID().toString());
					userPosition.setPositionId(id);
					userPosition.setUsername(username);
					session.save(userPosition);
				}
			}
		}finally{
			session.flush();
			session.close();
		}
	}

	@Expose
	public void insertUserDept(String username, String ids) {
		Session session = this.getSessionFactory().openSession();
		try{
			session.createQuery(
					"delete " + UserDept.class.getName()
					+ " u where u.username = :username")
					.setString("username", username).executeUpdate();
			
			if (StringUtils.isNotEmpty(ids)) {
				UserDept userDept;
				for (String id : ids.split(",")) {
					userDept = new UserDept();
					userDept.setId(UUID.randomUUID().toString());
					userDept.setDeptId(id);
					userDept.setUsername(username);
					session.save(userDept);
				}
			}
		}finally{
			session.flush();
			session.close();
		}
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}


	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}
	
}
