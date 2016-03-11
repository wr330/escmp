package com.buaa.fly.flightrestrict.dao;

import java.util.Collection;
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

import com.buaa.fly.domain.Fighterxzh;
import com.buaa.fly.domain.Flightrestrict;
import com.buaa.out.domain.Technicaldocument;

@Repository("flightrestrictDao")
public class FlightrestrictDao extends HibernateBaseDao {
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
	public void queryFlightrestrict(Page<Flightrestrict> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Flightrestrict.class.getName() + " a where 1=1 ");

		if (null != parameter && !parameter.isEmpty()) {
			String ftype = (String) parameter.get("ftype");
			if (StringUtils.isNotEmpty(ftype)) {
				coreHql.append(" and a.ftype ='" + ftype + "'");
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
		hql = userService.checkUser(hql);
		this.pagingQuery(page, hql, countHql, args);
	}

	/**
	 * 查询使用限制信息方法
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Flightrestrict> queryshfxzh(Map<String, Object> parameter)
			throws Exception {
		String Hql = "from " + Flightrestrict.class.getName() + " a where 1=1 ";
		if (null != parameter && !parameter.isEmpty()) {
			String ftype = (String) parameter.get("ftypename");
			if (StringUtils.isNotEmpty(ftype)) {
				Hql += " and a.id in ( select flightRestrict from "
						+ Fighterxzh.class.getName()
						+ " b where b.referenceName = '" + ftype + "')";
			}
			String piciID = (String) parameter.get("piciID");
			if (StringUtils.isNotEmpty(piciID)) {
				Hql += " and a.id in ( select flightRestrict from "
						+ Fighterxzh.class.getName()
						+ " b where b.referenceName = '" + piciID + "')";
			}
			String outfactoryno = (String) parameter.get("outfactoryno");
			if (StringUtils.isNotEmpty(outfactoryno)) {
				Hql += " and a.id in ( select flightRestrict from "
						+ Fighterxzh.class.getName()
						+ " b where b.referenceName = '" + outfactoryno + "')";
			}
		}
		Hql = userService.checkUser(Hql);
		return this.query(Hql);
	}

	/**
	 * 数据添加
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Flightrestrict detail) throws Exception {
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
	public void updateData(Flightrestrict detail) throws Exception {
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
	public void deleteData(Flightrestrict detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	/**
	 * 添加校验，判断文件编号唯一
	 * 
	 * @param id
	 *            使用限制表id
	 * @param fno
	 *            文件编号
	 * 
	 */
	public String flightrestrictIsExists(String id, String fno) {
		String hql = "select count(*) from " + Flightrestrict.class.getName()
				+ " u where u.fno = :fno";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("fno", fno);
		if (id != null) {
			hql += " and u.id != :id";
			parameterMap.put("id", id);
		}
		int count = this.queryForInt(hql, parameterMap);

		String returnStr = null;
		if (count > 0) {
			returnStr = "此编号已存在！";
		}
		return returnStr;
	}

	/**
	 * 通过ID查找记录
	 * 
	 * @param id
	 */
	public Flightrestrict queryById(String id) {
		String hql = "from " + Flightrestrict.class.getName()
				+ " a where a.id=" + id;
		return (Flightrestrict) this.query(hql).get(0);
	}
}