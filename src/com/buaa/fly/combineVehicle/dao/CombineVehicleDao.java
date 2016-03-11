package com.buaa.fly.combineVehicle.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.fly.domain.CombineVehicle;
import com.common.HibernateBaseDao;

@Repository("combineVehicleDao")
public class CombineVehicleDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryCombineVehicle(Page<CombineVehicle> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ CombineVehicle.class.getName() + " a where 1=1 ");

		if (null != parameter && !parameter.isEmpty()) {
			String OutlineExecution = (String) parameter
					.get("OutlineExecution");
			if (StringUtils.isNotEmpty(OutlineExecution)) {
				coreHql.append(" and a.outlineExecution.oid like :oid ");
				args.put("oid", "%" + OutlineExecution + "%");
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
		this.pagingQuery(page, hql, countHql, args);
	}

	/**
	 * 根据试飞大纲查询结合架次
	 * 
	 * @param oid
	 * @throws Exception
	 */
	public Collection<CombineVehicle> queryCVbyOutline(String oid)
			throws Exception {
		String hql = "from " + CombineVehicle.class.getName()
				+ " a where 1=1 and a.outlineExecution = '" + oid + "'";
		return this.query(hql);

	}

	/**
	 * 数据添加
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(CombineVehicle detail) throws Exception {
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
	 * 查询结合架次数
	 */
	public Integer conlumIdentity() {
		String hql = "select count(*) from " + CombineVehicle.class.getName()
				+ " u where 1=1";
		int count = this.queryForInt(hql);
		return count + 1;
	}

	/**
	 * 数据修改
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(CombineVehicle detail) throws Exception {
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
	public void deleteData(CombineVehicle detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

}