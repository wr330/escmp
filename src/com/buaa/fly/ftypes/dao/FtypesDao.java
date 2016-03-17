package com.buaa.fly.ftypes.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.domain.Tasklist;
import com.buaa.out.domain.Handover;

@Repository("ftypesDao")
public class FtypesDao extends HibernateBaseDao {

	/**
	 * 信息查询
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Ftypes> queryFtypes(Map<String, Object> parameter) 
			throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		String hql = "from " + Ftypes.class.getName() + " a where 1=1 order by ftypename";

		if (null != parameter && !parameter.isEmpty()) {
		}
		
		return this.query(hql, args);
	}

	/**
	 * 数据添加
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Ftypes detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	public Integer conlumIdentity() {
		String hql = "select count(*) from " + Ftypes.class.getName()
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
	public void updateData(Ftypes detail) throws Exception {
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
	public void deleteData(Ftypes detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	/**
	 * 这个方法用来判断在添加时机型是否已经存在
	 * 
	 * @param ftype
	 *            用户输入的机型
	 */
	public String ftypeIsExists(String ftype) {
		String hql = "select count(*) from " + Ftypes.class.getName()
				+ " u where u.ftypename = :ftypename";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("ftypename", ftype);
		int count = this.queryForInt(hql, parameterMap);
		String returnStr = null;
		if (count > 0) {
			returnStr = "此机型已存在！";
		}
		return returnStr;
	}

}