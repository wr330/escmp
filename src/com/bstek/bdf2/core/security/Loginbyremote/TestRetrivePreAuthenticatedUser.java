package com.bstek.bdf2.core.security.Loginbyremote;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.security.IRetrivePreAuthenticatedUser;
import com.bstek.bdf2.core.service.IDeptService;
import com.bstek.bdf2.core.service.IGroupService;
import com.bstek.bdf2.core.service.IPositionService;
import com.bstek.bdf2.core.service.IUserService;
import com.zcore.sso.LtpaToken;

@Component
public class TestRetrivePreAuthenticatedUser implements IRetrivePreAuthenticatedUser {
	@Autowired
	@Qualifier("bdf2.userService")
	private  IUserService userService;
	@Autowired
	@Qualifier("bdf2.groupService")
	private  IGroupService groupService;
	@Autowired
	@Qualifier("bdf2.positionService")
	private  IPositionService positionService;
	@Autowired
	@Qualifier("bdf2.deptService")
	private  IDeptService deptService;
	
	/**
	 * (non-Javadoc)
	 * @覆写该方法就可以实现用户未登录的访问
	 */
public IUser retrive(HttpServletRequest request,HttpServletResponse response) throws ServletException {
	
	//从cookie中获取身份加密串
	String LtpaPlaintext="";
	Cookie[] cookies;
	try {
		cookies = request.getCookies();
		if (cookies != null) {
			for (int iCookieCounter = 0; iCookieCounter < cookies.length; iCookieCounter++) {
				cookies[iCookieCounter].getDomain();
				if (cookies[iCookieCounter].getName().toLowerCase().equals("LtpaToken".toLowerCase())) {
					LtpaPlaintext=cookies[iCookieCounter].getValue();
				}
			}
		}
	} catch (Exception e) {
		System.out.print("Cookie Err!");
	}
	//解密获取用户身份（职工编号）
	if(LtpaPlaintext!=null&&!LtpaPlaintext.equals("")){
		LtpaToken token = new LtpaToken();
		String ltpa3DESKey = "JM27DQ0pEkRt/2W7Jz5PhlZSJH/w8bX9dhdw3jBhNe8="; // LTPA 3DES 密钥，集成时根据管理员提供的LTPA文件内容替换
		String ltpaPassword = "user@611cadi"; // LTPA 密钥密码，集成时根据管理员提供密码替换
		try{
			token.getInstance(ltpa3DESKey,ltpaPassword,LtpaPlaintext);
			String userdn = token.getUserDN();
			String[] usertokens = userdn.split(",");
			String username = null;
			for(int i=0;i<usertokens.length;i++){
				String str = usertokens[i];
				if( str.startsWith("uid") ||  str.startsWith("UID") ){
					username = str.trim().substring(14);	//**证书类型，此处注意需要针对发放的证书信息进行特殊处理，核心功能就是从证书信息中获取用户身份（职工编号）
				} else if( str.startsWith("cn") || str.startsWith("CN") ) {
					username = str.trim().substring(3);	//**证书类型
				}
			}
			
			if(null!=username){
				//String name=request.getParameter("name");
				UserDetails user1 = userService.loadUserByUsername(username);
				DefaultUser user=(DefaultUser)user1;
				user.setDepts(deptService.loadUserDepts(user.getUsername()));
				user.setPositions(positionService.loadUserPositions(user.getUsername()));
				user.setGroups(groupService.loadUserGroups(user.getUsername()));
				return user;
			}			else{
				return null;
							}
		}catch (Exception e) {
			System.out.print("Cookie Err!");
		}
	}	
	
	return null;
}

}





