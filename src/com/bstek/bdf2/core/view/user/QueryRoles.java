package com.bstek.bdf2.core.view.user;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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

}
