package com.buaa.comm.btreport.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.comm.domain.Btreport;

@Repository("btreportDao")
public class BtreportDao extends HibernateBaseDao {
	@Resource
	private QueryUserData userService;
	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryBtreport(Page<Btreport> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Btreport.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String departmentHead = (String) parameter.get("departmentHead");
			if (StringUtils.isNotEmpty(departmentHead)) {
				coreHql.append(" and a.departmentHead = :dh ");
				args.put("dh", departmentHead);
			}
        	String sectionChief = (String) parameter.get("sectionChief");
			if (StringUtils.isNotEmpty(sectionChief)) {
				coreHql.append(" and a.sectionChief = :sc ");
				args.put("sc", sectionChief);
			}
			String user = (String) parameter.get("user");
			if (StringUtils.isNotEmpty(user)) {
				coreHql.append(" and a.writingPerson = :wp ");
				args.put("wp", user);
			}
			String status = (String) parameter.get("status");
			if (StringUtils.isNotEmpty(status)) {
				Integer sta = Integer.parseInt(status);
				coreHql.append(" and a.status >= :ss ");
				args.put("ss", sta);
			}
        }
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and "+ result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}
        String countHql = "select count(*) " + coreHql.toString();
        String hql = coreHql.toString();
        hql = userService.checkUser(hql);
        hql += " order by status asc, bttime desc";
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**                  
	* 根据用户名，按审阅室主任是此用户名且工作状态是审阅中的条件，搜索出差报告表
	* @param username    
	* @throws Exception
	*/
	public Integer queryBtreport(String username) throws Exception {
		Map<String, Object> args = new HashMap<String,Object>();
		StringBuffer coreHql = new StringBuffer("from " + Btreport.class.getName()+" a where 1=1 ");
		if (StringUtils.isNotEmpty(username)) {
			coreHql.append(" and a.sectionChief = :sc ");
			args.put("sc", username);
		}
        String hql = coreHql.toString() + "and a.status = 1";
        Collection<Btreport> dataItems = this.query(hql,args);
        Integer btreportSCount = dataItems.size();
        return btreportSCount;
	}
	
	/**                  
	* 根据用户名，按审阅部领导是此用户名且工作状态是审阅中的条件，搜索出差报告表
	* @param username    
	* @throws Exception
	*/
	public Integer queryBtreportDH(String username) throws Exception {
		Map<String, Object> args = new HashMap<String,Object>();
		StringBuffer coreHql = new StringBuffer("from " + Btreport.class.getName()+" a where 1=1 ");
		if (StringUtils.isNotEmpty(username)) {
			coreHql.append(" and a.departmentHead = :dh ");
			args.put("dh", username);
		}
        String hql = coreHql.toString() + "and a.status = 2";
        Collection<Btreport> dataItems = this.query(hql,args);
        Integer btreportDCount = dataItems.size();
        return btreportDCount;
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Btreport detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			//detail.setOid(UUID.randomUUID().toString());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据修改
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Btreport detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据删除
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Btreport detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}