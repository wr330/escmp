package com.bstek.bdf2.core.service.impl;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;

import com.bstek.bdf2.core.CoreHibernateDao;
import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.service.IDeptService;
import com.bstek.bdf2.core.service.IFrameworkService;
import com.bstek.bdf2.core.service.IGroupService;
import com.bstek.bdf2.core.service.IPositionService;
import com.bstek.bdf2.core.view.user.UserMaintain;
import com.bstek.dorado.core.Configure;
import com.bstek.dorado.web.DoradoContext;
import com.google.code.kaptcha.Constants;

public class DefaultFrameworkService extends CoreHibernateDao implements IFrameworkService {
	public static final String BEAN_ID="bdf2.frameworkService";
	private IDeptService deptService;
	private IPositionService positionService;
	private IGroupService groupService;
	private PasswordEncoder passwordEncoder;
	private UserMaintain userMaintain;
	
	public void authenticate(IUser user,UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		this.preChecks(authentication);
		DefaultUser defaultUser=(DefaultUser)user;
		String password = user.getPassword();
		password = password.substring(1);
		String presentedPassword = authentication.getCredentials().toString();
        /*
         * 连续输错五次密码，用户锁定，密码正确后“密码错误计数器”清零
         */
		if (!((DefaultUser) user).getLock()){
        	if (!passwordEncoder.isPasswordValid(password, presentedPassword,defaultUser.getSalt())) {
        		int loginCount = ((DefaultUser) user).getLoginCount();
        		((DefaultUser)user).setLoginCount(++loginCount);
        		if(loginCount >= 5){
        			((DefaultUser) user).setLock(true);
        		}
        		Session session = this.getSessionFactory().openSession();
        		try {
        			session.update(user);
        		} finally {
        			session.flush();
        			session.close();
        		}
        		throw new BadCredentialsException("用户名或密码不正确！");
        	}else{
        		((DefaultUser) user).setLoginCount(0);
        		Session session = this.getSessionFactory().openSession();
        		try {
        			session.update(user);
        		} finally {
        			session.flush();
        			session.close();
        		}
        		
        		String userIP = defaultUser.getIp();
        		if(null != userIP && !userIP.equals("")){
        			String loginIP = ContextHolder.getRequest().getRemoteAddr();//获取客户端IP
        			if(loginIP.equals("0:0:0:0:0:0:0:1"))
        				loginIP = "127.0.0.1";//win7系统下localhost会解析为"0:0:0:0:0:0:0:1"
        			if(!userIP.equals(loginIP))
        				throw new BadCredentialsException("该用户不允许在此IP登录！");
        			return;
        		}
        		defaultUser.setDepts(deptService.loadUserDepts(user.getUsername()));
        		defaultUser.setPositions(positionService.loadUserPositions(user.getUsername()));
        		defaultUser.setGroups(groupService.loadUserGroups(user.getUsername()));
        		}
        }
        else{
        	throw new BadCredentialsException("用户名已被锁定，请找管理员解锁！");
        }
	}
	private void preChecks(UsernamePasswordAuthenticationToken authentication)throws AuthenticationException{
		boolean useCaptcha=Configure.getBoolean("bdf2.useCaptchaForLogin");
		if(useCaptcha){
			String key=ContextHolder.getRequest().getParameter("captcha_");
			if(StringUtils.isNotEmpty(key)){
				String sessionkey=(String)ContextHolder.getHttpSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
				if(sessionkey==null){
					throw new BadCredentialsException("验证码过期");
				}else if(!sessionkey.equals(key)){
					throw new BadCredentialsException("验证码不正确");					
				}
			}else{
				throw new BadCredentialsException("验证码不能为空");					
			}
		}
		if (authentication.getPrincipal() == null) {
			throw new BadCredentialsException("Username can not be null");
		}
		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException("password can not be null");
		}
	}
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}
	public void setPositionService(IPositionService positionService) {
		this.positionService = positionService;
	}
	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}
}
