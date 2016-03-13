package com.buaa.fly.outlineexecution.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Dailyacc;
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Subject;
import com.buaa.fly.outlineexecution.dao.OutlineexecutionDaoforJDBC.OutlineexecutionRowMapper;

@Repository("outlineexecutionDao")
public class OutlineexecutionDao extends HibernateBaseDao {

	/**
	 * 查询方法，该方法未应用
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Outlineexecution> queryOutlineexecution(
			Map<String, Object> parameter) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String parentnode = (String) parameter.get("parentnode");
		String hql = "from " + Outlineexecution.class.getName()
				+ " u where 1=1";
		String ftype = (String) parameter.get("ftype");
		
		if (StringUtils.isEmpty(ftype)) {
			hql += " and u.aircrafttype is NULL";
			return this.query(hql);
		} else
			hql += " and u.aircrafttype ='" + ftype + "'";

		if (StringUtils.isEmpty(parentnode)) {
			hql += " and u.parentnode is null order by u.orderno asc";
			return this.query(hql, map);
		} else {
			map.put("parentnode", parentnode);
			hql += " and u.parentnode=:parentnode order by u.orderno asc";
			return this.query(hql, map);
		}
	}

	/**
	 * 查询方法，该方法未应用
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Outlineexecution> query(Map<String, Object> parameter)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from " + Outlineexecution.class.getName()
				+ " u where 1=1";
		String ftype = (String) parameter.get("ftype");
		String parentnode = (String) parameter.get("parentnode");
		String subject = (String) parameter.get("subject");
		
		if (StringUtils.isEmpty(ftype)) {
			hql += " and u.aircrafttype is NULL";
			return this.query(hql);
		} else
			hql += " and u.aircrafttype ='" + ftype + "'";
		
		if (StringUtils.isEmpty(subject)) {
			
		} else {
			map.put("subject", subject);
			hql += " and u.project.oid=:subject";
		}
		
		if (StringUtils.isEmpty(parentnode)) {
			
			return this.query(hql, map);
		} else {
			map.put("parentnode", parentnode);
			hql += " and u.project.parentnode=:parentnode";
			return this.query(hql, map);
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
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void queryOutline(Page<Outlineexecution> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		String oid = (String) parameter.get("subject");
		String sql = "with cte as(	select a.oid from Fly_Subject a where Oid='"
				+ oid
				+ "'	union all select k.oid from Fly_Subject k inner join cte c on c.oid=k.ParentNode) select oid from cte";
		Session session = this.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sql).addScalar("oid",
				Hibernate.STRING);// 设置返回值类型，不然会报错
		List<String> aa = query.list();
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Outlineexecution.class.getName() + " a where 1=1 ");
		coreHql.append(" and a.project.oid in(:aa)");
		args.put("aa", aa);
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and " + result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}
		String countHql = "select count(*) " + coreHql.toString();
		String hql = coreHql.toString();
		this.pagingQuery(page, hql, countHql, args);
	}

	/**
	 * 查询方法
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Outlineexecution> queryOutlineforText(String subjectOid)
			throws Exception {
		String oid = subjectOid;
		String sql = "with cte as(	select a.oid from Fly_Subject a where Oid='"
				+ oid
				+ "'	union all select k.oid from Fly_Subject k inner join cte c on c.oid=k.ParentNode) select oid from cte";
		Session session = this.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sql).addScalar("oid",
				Hibernate.STRING);// 设置返回值类型，不然会报错
		List<String> aa = query.list();
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Outlineexecution.class.getName() + " a where 1=1 ");
		coreHql.append(" and a.project.oid in(:aa)");
		args.put("aa", aa);
		String hql = coreHql.toString();
		return this.query(hql, args);
	}

	/**
	 * 数据添加
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Outlineexecution detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 完成架次统计方法，该方法未应用
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void statisticMainSubject(Outlineexecution detail) throws Exception {
		Integer count = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from " + Outlineexecution.class.getName()
				+ " u where 1=1";
		hql += " and u.parentnode = '" + detail.getSubject() + "'";
		hql += " and u.aircrafttype = '" + detail.getAircrafttype() + "'";
		Collection<Outlineexecution> items = this.query(hql, map);
		for (Outlineexecution item : items) {
			this.statisticSubject(item);
			count += item.getJiaci();
		}
		detail.setJiaci(count);
		this.updateData(detail);

	}

	/**
	 * 完成架次统计方法，该方法未应用
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void statisticSubject(Outlineexecution detail) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String corehql = "select count(*) from " + Sfstatistic.class.getName()
				+ " u where 1=1";
		corehql += " and u.subject like '%," + detail.getSubject() + ",%'";
		corehql += " and u.ftype = '" + detail.getAircrafttype() + "'";
		Integer count = this.queryForInt(corehql, map);
		detail.setJiaci(count);
		this.updateData(detail);
	}

	/**
	 * 数据修改
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Outlineexecution detail) throws Exception {
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
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Outlineexecution detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	/**
	 * 查询大纲树上某一节点是否具有子节点，该方法未应用
	 * 
	 * @param parameter
	 */
	public int countChildren(Map<String, Object> parameter) {
		String parentnode = (String) parameter.get("parentnode");
		String ftype = (String) parameter.get("ftype");
		String hql = "select count(*) from " + Outlineexecution.class.getName()
				+ " d where d.parentnode = :parentnode";
		if (StringUtils.isEmpty(ftype)) {
			hql += " and d.aircrafttype is null";
		} else
			hql += " and d.aircrafttype ='" + ftype + "'";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("parentnode", parentnode);
		return this.queryForInt(hql, parameterMap);
	}

	/**
	 * 通过id查询试飞大纲
	 * 
	 * @param id
	 */
	public Outlineexecution queryById(String id) {
		String hql = "from " + Outlineexecution.class.getName()
				+ " a where a.oid=" + "'" + id + "'";
		return (Outlineexecution) this.query(hql).get(0);
	}

}