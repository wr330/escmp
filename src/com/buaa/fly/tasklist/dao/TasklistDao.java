package com.buaa.fly.tasklist.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Subject;
import com.buaa.fly.domain.Tasklist;

@Repository("tasklistDao")
public class TasklistDao extends HibernateBaseDao {
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
	public void queryTasklist(Page<Tasklist> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Tasklist.class.getName() + " a where 1=1 ");
		if (null != parameter && !parameter.isEmpty()) {
			String tasknumber = (String) parameter.get("tasknumber");
			if (StringUtils.isNotEmpty(tasknumber)) {
				coreHql.append(" and a.tasknumber = :tn ");
				args.put("tn", tasknumber);
			}
			List<String> subject = new ArrayList<String>();
			subject = (List<String>) parameter.get("subject");
			if ((subject != null)) {
				if (subject.size() != 0) {
					coreHql.append(" and(");
					for (int i = 0; i < subject.size(); i++) {
						coreHql.append(" a.subject like '%" + subject.get(i)
								+ "%' or");
					}
					coreHql.append(" 0=1)");
				}
			}
			String outlinesub = (String) parameter.get("outlinesub");
			if (StringUtils.isNotEmpty(outlinesub)) {
				coreHql.append(" and a.subject like :outlinesub ");
				args.put("outlinesub", "%" + outlinesub + "%");
			}
			String aircrafttype = (String) parameter.get("aircrafttype");
			if (StringUtils.isNotEmpty(aircrafttype)) {
				coreHql.append(" and a.aircrafttype = '" + aircrafttype + "'");
			} else
				coreHql.append(" and a.aircrafttype = null ");
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
		hql = userService.checkUser(hql);
		this.pagingQuery(page, hql, countHql, args);

	}

	/**
	 * 数据添加
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Tasklist detail) throws Exception {
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
	public void updateData(Tasklist detail) throws Exception {
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
	public void deleteData(Tasklist detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	/**
	 * 这个方法用来判断在添加时任务单是否已经存在
	 * 
	 * @param tasknumber
	 *            用户输入的任务单号
	 */
	public String tasklistIsExists(String tasknumber) {
		String hql = "select count(*) from " + Tasklist.class.getName()
				+ " u where u.tasknumber = :tasknumber";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("tasknumber", tasknumber);
		int count = this.queryForInt(hql, parameterMap);
		String returnStr = "1";
		if (count > 0) {
			returnStr = "此单号已存在！";
		}
		return returnStr;
	}
	
	/**
	 * 任务管理单查询
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Tasklist> queryTaskOutline(Map<String, Object> parameter) {
		String name = (String) parameter.get("subject");
		String hql = "from " + Tasklist.class.getName() + " u where 1=1";
		String ftype = (String) parameter.get("ftype");
		if (StringUtils.isEmpty(ftype)) {
			hql += " and u.aircrafttype is null";
		} else
			hql += " and u.aircrafttype ='" + ftype + "'";
		if (StringUtils.isEmpty(name)) {
			hql += "";
		} else {
			hql += "";
		}
		return this.query(hql);
	}
}