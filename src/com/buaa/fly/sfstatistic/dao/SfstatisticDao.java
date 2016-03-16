package com.buaa.fly.sfstatistic.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Subject;
import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.subject.manager.SubjectManager;

@Repository("sfstatisticDao")
public class SfstatisticDao extends HibernateBaseDao {
	@Resource
	private QueryUserData userService;
	@Resource
	private SubjectManager subjectManager;

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void querySfstatistic(Page<Sfstatistic> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Sfstatistic.class.getName() + " a where 1=1 ");

		if (null != parameter && !parameter.isEmpty()) {
			String ftype = (String) parameter.get("ftype");
			if (StringUtils.isNotEmpty(ftype)) {
				coreHql.append(" and a.ftype ='" + ftype + "'");
			} else {
				coreHql.append(" and a.ftype = null ");
			}
		}

		if (null != parameter && !parameter.isEmpty()) {
			String id = (String) parameter.get("id");
			if (id != null) {
				coreHql.append(" and a.id ='" + id + "'");
			}
		}
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and " + result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}

		String countHql = "select count(*) " + coreHql.toString();
		String hql = coreHql.toString();
		hql = userService.checkUser(hql);// 用户密级判断
		hql += " order by timeqifei desc";
		this.pagingQuery(page, hql, countHql, args);
	}

	/**
	 * 根据试飞科目查询架次数
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public Collection<Map<String, Object>> queryJiacinum(
			Map<String, Object> parameter) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String parentnode = (String) parameter.get("parentnode");
		Collection<Subject> dataItems;
		List<Integer> num = new ArrayList<Integer>();
		if (parentnode != null) {
			dataItems = subjectManager.querySubject(parameter);
			num = querySubjectJiaci(parameter);
		} else {
			Collection<Subject> dataItems0 = subjectManager
					.querySubject(parameter);
			List<Subject> items = (ArrayList<Subject>) dataItems0;
			Subject item0 = items.get(0);
			HashMap<String, Object> map0 = new HashMap<String, Object>();
			map0.put("parentnode", item0.getOid());
			map0.put("ftype", item0.getFtype());
			dataItems = subjectManager.querySubject(map0);
			num = querySubjectJiaci(map0);
		}
		int i = 0;
		for (Subject item : dataItems) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("count", num.get(i));
			map.put("name", item.getName());
			list.add(map);
			i++;
		}
		return list;
	}

	/**
	 * 依据飞行统计，根据试飞科目对架次进行统计
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public List<Integer> querySubjectJiaci(Map<String, Object> parameter)
			throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		List<Integer> num = new ArrayList<Integer>();
		String hql = "from " + Subject.class.getName() + " a where 1=1 ";
		Collection<Subject> subjects = new ArrayList<Subject>();
		if (null != parameter && !parameter.isEmpty()) {
			String parentnode = (String) parameter.get("parentnode");
			if (StringUtils.isNotEmpty(parentnode)) {
				hql += " and a.parentnode ='" + parentnode + "'";
			}
			String ftype = (String) parameter.get("ftype");
			if (StringUtils.isNotEmpty(ftype)) {
				hql += " and a.ftype ='" + ftype + "'";
			}else{
				hql += " and a.ftype = null ";
			}
			hql += " order by OrderNo ";
			subjects = this.query(hql);
			Integer i = 0;
			for (Subject subject : subjects) {
				Integer jiaci = 0;
				jiaci = querySubject(subject, jiaci);
				num.add(i, jiaci);
				i++;
			}
		}

		return num;
	}

	/**
	 * 统计该试飞科目在飞行统计中的架次数
	 * 
	 * @param subject
	 * @param num
	 * @throws Exception
	 */
	public Integer querySubject(Subject subject, Integer num) throws Exception {
		String hql = "from " + Subject.class.getName() + " a where 1=1 ";
		if (StringUtils.isNotEmpty(subject.getOid())) {
			hql += " and a.parentnode ='" + subject.getOid() + "'";
		}
		if (StringUtils.isNotEmpty(subject.getFtype())) {
			hql += " and a.ftype ='" + subject.getFtype() + "'";
		}else{
			hql += " and a.ftype = null ";
		}
		hql += " order by OrderNo ";
		Collection<Subject> subjects = this.query(hql);
		if (null != subjects && subjects.size() > 0) {
			for (Subject item : subjects) {
				num += querySubject(item, num);
			}
		} else {
			num = queryJiaci(subject.getName(), subject.getFtype());
		}
		return num;
	}
	
	/**
	 * 这个方法用来该任务单是否关联飞行统计数据
	 * 
	 * @param tasknumber
	 *            用户输入的任务单号
	 */
	public String statisticIsExists(String tasknumber) {
		String hql = "select count(*) from " + Sfstatistic.class.getName()
				+ " u where u.taskNo.tasknumber = :tasknumber";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("tasknumber", tasknumber);
		int count = this.queryForInt(hql, parameterMap);
		String returnStr = "1";
		if (count > 0) {
			returnStr = "该任务管理单关联飞行统计数据，不能删除！";
		}
		return returnStr;
	}

	/**
	 * 查询该科目在飞行统计中的架次数
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public int queryJiaci(String subject, String ftype) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		Collection<Sfstatistic> details = new ArrayList<Sfstatistic>();
		int jiaci = 0;
		String sql = "select oid from (select oid,','+subject+',' as newsubject from Fly_Tasklist) a where a.newsubject like '%,"
				+ subject + ",%'";
		Session session = this.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sql).addScalar("oid",
					Hibernate.STRING);// 设置返回值类型，不然会报错
			List<String> result = query.list();
			if (result.isEmpty()) {
			} else {
				StringBuffer coreHql = new StringBuffer("from "
						+ Sfstatistic.class.getName() + " a where 1=1 ");
				coreHql.append(" and a.ftype =:ftype");
				args.put("ftype", ftype);
				coreHql.append(" and a.taskNo.oid in(:result)");
				args.put("result", result);
				details = this.query(coreHql.toString(), args);
			}
		} finally {
			session.flush();
			session.close();
		}
		if (null != details && details.size() > 0) {
			for (Sfstatistic item : details) {
				jiaci += item.getJiaci();
			}
		}
		return jiaci;
	}

	/**
	 * 数据添加
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Sfstatistic detail) throws Exception {
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
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Sfstatistic detail) throws Exception {
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
	public void deleteData(Sfstatistic detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

}