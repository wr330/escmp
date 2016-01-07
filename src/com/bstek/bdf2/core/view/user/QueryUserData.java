package com.bstek.bdf2.core.view.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.service.IUserService;
import com.bstek.dorado.annotation.Expose;
@Component("userService")
public class QueryUserData {
	@Autowired
	@Qualifier("bdf2.userService")
	private  IUserService userService;
	
	public String checkUser(String sql){
		
		IUser user=ContextHolder.getLoginUser();
		UserDetails user1 = userService.loadUserByUsername(user.getUsername());
		DefaultUser user2=(DefaultUser)user1;
		sql+=" and a.miji <= "+user2.getMiji();
		
		return sql;
	}
}
