package com.buaa.comm.joballot.dao;

import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.comm.domain.Joballot;

@Repository("joballotDao")
public class JoballotDao extends HibernateBaseDao {
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
	public void queryJoballot(Page<Joballot> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Joballot.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String jobstatistics = (String)parameter.get("jobstatistics");
        	if(StringUtils.isNotEmpty( jobstatistics )){
        		coreHql.append(" and a.jobstatistics.oid like :oid ");
        		args.put("oid","%" + jobstatistics + "%");	
        	}
        	String user = (String) parameter.get("user");
			if (StringUtils.isNotEmpty(user)) {
				coreHql.append(" and a.personid = :pi ");
				args.put("pi", user);
			}
			String status = (String) parameter.get("status");
			if (StringUtils.isNotEmpty(status)) {
				Integer sta = Integer.parseInt(status);
				coreHql.append(" and a.workStatus >= :ss ");
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
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Joballot detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setOid(UUID.randomUUID().toString());
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
	public void updateData(Joballot detail) throws Exception {
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
	public void deleteData(Joballot detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public String personIsExists(String oid,String person,String jobstatistics) {
		String hql = "select count(*) from " + Joballot.class.getName()
				+ " u where u.personid = :person and jobstatistics = '" + jobstatistics + "'";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("person", person);
		if (oid != null) {
			hql += " and u.oid != :oid";
			parameterMap.put("oid", oid);
		}
		int count = this.queryForInt(hql, parameterMap);

		String returnStr = null;
		if (count > 0) {
			returnStr = "此人员已分配！";
		}
		return returnStr;
	}
    
}