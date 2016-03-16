package com.buaa.fly.fighterinfo.dao;

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

import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.domain.Fpici;
import com.buaa.fly.domain.Ftypes;

@Repository("fighterinfoDao")
public class FighterinfoDao extends HibernateBaseDao {

	/**
	 * 信息查询
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fighterinfo> queryFighterinfo(
			Map<String, Object> parameter) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		String hql = "from " + Fighterinfo.class.getName() + " a where 1=1 ";

		if (null != parameter && !parameter.isEmpty()) {
			String piciname = (String) parameter.get("piciname");
			if (StringUtils.isNotEmpty(piciname)) {
				hql += " and a.piciid.piciID like :piciname ";
				args.put("piciname", piciname);
			}
		}
		hql += "order by outfactoryno ";
		return this.query(hql, args);
	}

	/**
	 * 数据添加
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Fighterinfo detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 根据飞机编号查询信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Fighterinfo queryFighterinfo1(String parameter) {
		String hql = " from " + Fighterinfo.class.getName()
				+ " u where u.outfactoryno like '" + parameter + "'";
		List<Fighterinfo> info = this.query(hql);
		return info.get(0);
	}

	/**
	 * 根据批次查询飞机信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fighterinfo> queryFighterinfobyPici(String parameter) {
		String hql = " from " + Fighterinfo.class.getName()
				+ " u where u.piciid like '" + parameter + "'";
		Collection<Fighterinfo> info = this.query(hql);
		return info;
	}
	
	/**
	 * 根据机型查询飞机信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fighterinfo> queryFighterinfobyFtype(Map<String, Object> parameter) {
		String ftype = (String) parameter.get("ftype");
		String hql = " from " + Fighterinfo.class.getName()
				+ " u where u.piciid.fTypeName.ftypename like '" + ftype + "'";
		Collection<Fighterinfo> info = this.query(hql);
		return info;
	}
	
	/**
	 * 查询飞机信息条目数
	 * 
	 * @throws Exception
	 */
	public Integer conlumIdentity() {
		String hql = "select count(*) from " + Fighterinfo.class.getName()
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
	public void updateData(Fighterinfo detail) throws Exception {
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
	public void deleteData(Fighterinfo detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	/**
	 * 这个方法用来判断在添加时，该飞机编号是否已经存在
	 * 
	 * @param fighterinfo
	 *            用户输入的飞机编号
	 */
	public String fighterinfoIsExists(String fighterinfo) {
		String hql = "select count(*) from " + Fighterinfo.class.getName()
				+ " u where u.outfactoryno = :fighterinfo";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("fighterinfo", fighterinfo);
		int count = this.queryForInt(hql, parameterMap);
		String returnStr = null;
		if (count > 0) {
			returnStr = "此飞机已存在！";
		}
		return returnStr;
	}

}