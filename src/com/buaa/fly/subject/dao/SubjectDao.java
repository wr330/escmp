package com.buaa.fly.subject.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.common.HibernateBaseDao;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Subject;
import com.buaa.out.domain.Technicaldocument;

@Repository("subjectDao")
public class SubjectDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public Collection<Subject> querySubject(Map<String, Object> parameter) throws Exception {
		
		Map<String,Object> map=new HashMap<String,Object>();
		String parentnode=(String) parameter.get("parentnode");
		String hql="from "+Subject.class.getName()+" u where 1=1";
		String ftype =(String) parameter.get("ftype");
		if(StringUtils.isEmpty(ftype)){
			hql+=" and u.ftype is null";
		}else
			hql+=" and u.ftype ='" + ftype + "'";
		
		if(StringUtils.isEmpty(parentnode)){
			hql+=" and u.parentnode is null ";
			return this.query(hql,map);
		}else{
			map.put("parentnode",parentnode);
			hql+=" and u.parentnode=:parentnode ";
			return this.query(hql,map);			
		}
		
	}
	
	/**
	 * 指针对通用试飞科目
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public Collection<Subject> queryChildren(String parameter) throws Exception {		
		String hql="from "+Subject.class.getName()+" u where 1=1 and u.ftype is null";
		if(StringUtils.isEmpty(parameter)){
			hql+=" and u.parentnode is null ";
			return this.query(hql);
		}else{
			hql+=" and u.parentnode='"+parameter+"'";
			return this.query(hql);			
		}
		
	}
	
	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public Subject querySubjectbyId(String parameter) throws Exception {
		String hql="from "+Subject.class.getName()+" u where oid='"+parameter+"'";
			List<Subject> lst= this.query(hql);			
			return lst.get(0);
		
	}
	
	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public Subject queryCommonSubject() throws Exception {
		String hql="from "+Subject.class.getName()+" u where u.ftype is null and u.parentnode is null ";
			List<Subject> lst= this.query(hql);			
			return lst.get(0);
		
	}
public Collection<Subject> deleteSubject(Map<String, Object> parameter) throws Exception {
		String hql="from "+Subject.class.getName()+" u where 1=1";
		String ftype =(String) parameter.get("ftype");
		if(StringUtils.isEmpty(ftype)){
			hql+=" and u.ftype is null";
		}else
			hql+=" and u.ftype ='" + ftype + "'";
			return this.query(hql);			
}
	

	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Subject detail) throws Exception {
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
	 * 复制数据
	 * @param detail
	 * @throws Exception
	 */
	public void copyData(Subject detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
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
	public void updateData(Subject detail) throws Exception {
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
	public void deleteData(Subject detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public int countChildren(Map<String, Object> parameter) {
		String parentnode=(String) parameter.get("parentnode");
		String ftype=(String) parameter.get("ftype");
		String hql = "select count(*) from " + Subject.class.getName() + " d where d.parentnode = :parentnode";
		if(StringUtils.isEmpty(ftype)){
			hql+=" and d.ftype is null";
		}else
			hql+=" and d.ftype ='" + ftype + "'";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("parentnode", parentnode);
		return this.queryForInt(hql, parameterMap);
	}    
	public String subjectIsExists(String oid,String name,String ftype) {
		String hql = "select count(*) from " + Subject.class.getName()
				+ " u where u.name = :name";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		if(ftype!=null){
			hql+=" and u.ftype = :ftype";
			parameterMap.put("ftype", ftype);}
		else
			hql+=" and u.ftype is null";
		parameterMap.put("name", name);
		if (oid != null) {
			hql += " and u.oid != :oid";
			parameterMap.put("oid", oid);
			
		}
		int count = this.queryForInt(hql, parameterMap);

		String returnStr = null;
		if (count > 0) {
			returnStr = "此科目名已存在！";
		}
		return returnStr;
	}  
	
	
	
	public String subjectIsOld(String pid) {
		String hql = "select count(*) from " + Outlineexecution.class.getName()
				+ " u where u.project.oid = :pid";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("pid", pid);
		String returnStr = null;
		int count = this.queryForInt(hql, parameterMap);
		if (count > 0) {
			returnStr = "此科目已存在大纲条目！";
		}
		return returnStr;
	}  
	/**
	 * 查找通用试飞科目
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void querygenericSubject(Page<Subject> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Subject.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String ftype = (String)parameter.get("ftype");
        	if(StringUtils.isNotEmpty( ftype )){
        		coreHql.append(" and a.ftype ='" + ftype + "'");
        	}
        }
       else
        	coreHql.append("and a.ftype is null");
		
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
	}