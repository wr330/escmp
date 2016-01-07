package com.buaa.comm.jobconcern.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.comm.domain.Jobconcern;

@Repository("jobconcernDao")
public class JobconcernDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryJobconcern(Page<Jobconcern> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Jobconcern.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
	String jobstatistics = (String)parameter.get("jobstatistics");
	if(StringUtils.isNotEmpty( jobstatistics )){
		coreHql.append(" and a.jobstatistics.oid like :oid ");
		args.put("oid","%" + jobstatistics + "%");	
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
		this.pagingQuery(page, hql, countHql, args);
	}
	//通过用户ID获取工作项目关注信息
	public Collection<Jobconcern> getJobConcernByUser(){
	    String username = ContextHolder.getLoginUserName();
	    String hql = "from " + Jobconcern.class.getName()+" where personid = '" + username + "'";
	    return this.query(hql);
	}
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Jobconcern detail) throws Exception {
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
	public void updateData(Jobconcern detail) throws Exception {
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
	public void deleteData(Jobconcern detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	//通过用户删除信息
	public void deleteDataByUser(String username) throws Exception {
		String hql = "delete from " +Jobconcern.class.getName()+" where personid = '" + username + "'";
		Session session = this.getSessionFactory().openSession();
		try {
			session.createQuery(hql).executeUpdate();
		} finally {
			session.flush();
			session.close();
		}
	}    
}