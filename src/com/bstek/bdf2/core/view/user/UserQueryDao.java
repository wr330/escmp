package com.bstek.bdf2.core.view.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.annotation.DataProvider;
import com.common.HibernateBaseDao;

/**
* 用于处理用户信息查找搜索的类
* @author wangRui
* @Time 2017-3-13 14:20:00
*/
@Repository("userQueryDao")
public class UserQueryDao extends HibernateBaseDao{
	
	/**
	 * 根据用户名和科室名，进行用户信息的查找，现在用于部门常用共享人群的选择
	 * 	
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<DefaultUser> queryUser(Map<String, Object> parameter) throws Exception {
		Map<String, Object> args = new HashMap<String,Object>();
		StringBuffer coreHql = new StringBuffer("from "+ DefaultUser.class.getName() + " a where 1!=1");        
        if(null != parameter && !parameter.isEmpty()){
        	List<String> person = (List<String>)parameter.get("persons");
        	List<String> department = (List<String>)parameter.get("departments");        	
        	if(null != person && !person.isEmpty()){
        		args.put("per",person);
        		coreHql.append(" or a.username in(:per)");	
        	}     
        	if(null != department && !department.isEmpty()){
            	args.put("dep",department);
            	coreHql.append(" or a.department in(:dep)");	
            }	
        }  
        String hql = coreHql.toString();		
        return this.query(hql, args);
	}
	
	/**
	 * 根据岗位，进行用户信息的查找，现在用于根据岗位搜索用户信息的下拉框
	 * 	
	 * @param parameter
	 * @throws Exception
	 */
	@DataProvider
	public Collection<DefaultUser> queryUserDro(Map<String, Object> parameter) throws Exception {
		Map<String, Object> args = new HashMap<String,Object>();
		StringBuffer coreHql = new StringBuffer("from "+ DefaultUser.class.getName() + " a where 1=1");        
        if(null != parameter && !parameter.isEmpty()){
        	String position = (String)parameter.get("position");
        	if(StringUtils.isNotEmpty( position )){
        		coreHql.append(" and a.position like :position ");
        		args.put("position","%" + position + "%");	
        	}	
        }  
        String hql = coreHql.toString();		
        return this.query(hql, args);
	}
}
