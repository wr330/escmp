package com.bstek.bdf2.core.view.user;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.annotation.Expose;

@Component("queryRoles")
public class QueryRoles extends HibernateDao{
	
	//通过用户名找到对应所有角色
	@SuppressWarnings("unchecked")
	public Collection<Role> queryRoles(String UserName,Session session){
		List<Role> roles = new ArrayList<Role>(); 
		String hql = "from " + RoleMember.class.getName() + " where USERNAME_= :username";
		List<RoleMember> members = session.createQuery(hql).setString("username",UserName).list();
		if(members != null && members.size()!=0){
			for(RoleMember member:members){
				hql = "from " + Role.class.getName() + " where ID_= :id";
				List<Role> role = session.createQuery(hql).setString("id",member.getRoleId()).list();
				roles.add(role.get(0));
			}
		}
		return roles;
	}
	//返回角色名
	
	
	
	@Expose
	public  List<String> getRoleName(){
		Session session = this.getSessionFactory().openSession();
		try{
		IUser user = ContextHolder.getLoginUser();
		List<String> names = new ArrayList<String>();
		Collection<Role> roles= queryRoles(user.getUsername(),session);
		if(roles != null && roles.size()!=0){
			for(Role role:roles){
				names.add(role.getName());
			}
		}
		return names;
		}
		finally
		{
			session.flush();
			session.close();
		}
	}
	/**
	 * 添加校验，判断角色名称唯一
	 */
	@Expose
	public String roleIsExists(String id, String name) {
		String hql = "select count(*) from " + Role.class.getName()
				+ " u where u.name = :name";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("name", name);
		if (id != null) {
			hql += " and u.id != :id";
			parameterMap.put("id", id);
		}
		int count = this.queryForInt(hql, parameterMap);

		String returnStr = null;
		if (count > 0) {
			returnStr = "此编号已存在！";
		}
		return returnStr;
	}
}
