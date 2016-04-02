package com.buaa.comm.uflo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.model.DefaultUser;;

public class ApproverService {
	
	@SuppressWarnings("unchecked")
	public Collection<DefaultUser> getManager(String promoterName,Session session) {
		    List<DefaultUser> manager = new ArrayList<DefaultUser>(); 
		    String hql = "from " + Role.class.getName() + " where NAME_= :name";
		    List<Role> role1= session.createQuery(hql).setString("name","新闻审核").list();
		    if(role1 != null && role1.size()!=0){
				hql = "from " + RoleMember.class.getName() + " where ROLE_ID_= :roleId";
				List<RoleMember> members = session.createQuery(hql).setString("roleId",role1.get(0).getId()).list();
				if(members != null && members.size()!=0){
					for(RoleMember member:members){
						hql = "from " + DefaultUser.class.getName() + " where Username = :name";
						List<DefaultUser> user = session.createQuery(hql).setString("name",member.getUsername()).list();
						if(member.isGranted() && !manager.contains(user.get(0)))
							manager.add(user.get(0));
					}
				}
		    }
			hql = "from " + UserDept.class.getName() + " where USERNAME_= :name";
			List<UserDept> userDepts1 = session.createQuery(hql).setString("name",promoterName).list();
			if(userDepts1 == null || userDepts1.size()==0) return manager;
			hql = "from " + Role.class.getName() + " where NAME_= :name";
			List<Role> role= session.createQuery(hql).setString("name","主任").list();
			if(role == null || role.size()==0) return manager;
			hql = "from " + RoleMember.class.getName() + " where ROLE_ID_= :roleId";
			List<RoleMember> members = session.createQuery(hql).setString("roleId",role.get(0).getId()).list();
			if(members == null || members.size()==0) return manager;
			for(RoleMember member:members){
				if(member.isGranted()){
					hql = "from " + UserDept.class.getName() + " where USERNAME_= :name";
					List<UserDept> userDepts2= session.createQuery(hql).setString("name",member.getUsername()).list();
					for(UserDept dept1:userDepts2){
						for(UserDept dept2:userDepts1){
							hql = "from " + DefaultUser.class.getName() + " where Username = :name";
							List<DefaultUser> user = session.createQuery(hql).setString("name",member.getUsername()).list();
							if(dept1.getDeptId().equals(dept2.getDeptId()) && !manager.contains(user.get(0))){
								manager.add(user.get(0));
								break;
							}
						}
					}
				}
			}
			return manager;
	}
	@SuppressWarnings("unchecked")
	public Collection<String> getMinister(Session session) {
		String hql = "from " + Role.class.getName() + " where NAME_= :name";
		List<Role> role= session.createQuery(hql).setString("name","部长").list();
		if(role == null || role.size()==0) return null;
		hql = "from " + RoleMember.class.getName() + " where ROLE_ID_= :roleId";
		List<RoleMember> members = session.createQuery(hql).setString("roleId",role.get(0).getId()).list();
		if(members == null || members.size()==0) return null;
		List<String> minister = new ArrayList<String>(); 
		for(RoleMember member:members){
			if(member.isGranted()){
				minister.add(member.getUsername());
			}
		}
		return minister;
	}
}
