package com.bstek.bdf2.core.view.user;

import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.CoreHibernateDao;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.annotation.Expose;

/**
* 试飞模块用户选择“关注机型”后，保存到当前登录人的用户信息中
* @author wangRui
* @Time 2017-3-13 14:20:00
*/
@Component("loginUserSave")
public class LoginUserSave extends CoreHibernateDao{
	
	/**
	 * 把前台选择的“关注机型”（信息在parameter中），保存到数据库中
	 * 	
	 * @param parameter
	 * @throws Exception
	 */
	@Expose
	public void atteAric(Map<String, Object> parameter) throws Exception{
		
		if(null != parameter && !parameter.isEmpty()){
			Session session = this.getSessionFactory().openSession();
			//得到当前登录人的信息
			DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
			String airc = (String)parameter.get("airc");
			user.setAtteAirc(airc);//设置关注机型
			try {
				session.update(user);
			} finally {
				session.flush();
				session.close();
			}			
		}
	}
}
