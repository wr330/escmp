package com.buaa.fly.fpici.dao;

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
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.domain.Fpici;
import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.fighterinfo.dao.FighterinfoDao;

@Repository("fpiciDao")
public class FpiciDao extends HibernateBaseDao {
	@Resource
	private FighterinfoDao fighterinfoDao;

	/**
	 * 信息查询
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fpici> queryFpici(Map<String, Object> parameter)
			throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		String hql = "from " + Fpici.class.getName() + " a where 1=1 ";

		if (null != parameter && !parameter.isEmpty()) {
			String ftypename = (String) parameter.get("ftypename");
			if (StringUtils.isNotEmpty(ftypename)) {
				hql += " and a.fTypeName.ftypename = :ftypename ";
				args.put("ftypename", ftypename);
			}
		}
		return this.query(hql, args);
	}
	
	/**
	 * 根据机型查询批次，该方法没有用到
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fpici> queryFighterinfobyType(String parameter) {
		String hql = " from " + Fpici.class.getName()
				+ " u where u.fTypeName.ftypename like '" + parameter + "'";
		Collection<Fpici> info = this.query(hql);
		return info;
	}

	/**
	 * 数据添加
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Fpici detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setPiciID(UUID.randomUUID().toString());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	/**
	 * 查询批次数据的条目数，该方法没有用到
	 * 
	 * @throws Exception
	 */
	public Integer conlumIdentity() {
		String hql = "select count(*) from " + Fpici.class.getName()
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
	public void updateData(Fpici detail) throws Exception {
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
	public void deleteData(Fpici detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		Collection<Fighterinfo> items = fighterinfoDao
				.queryFighterinfobyPici(detail.getPiciID());
		if (items != null) {
			for (Fighterinfo item : items) {
				fighterinfoDao.deleteData(item);
			}
		}
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	/**
	 * 删除批次数据，该方法没有用到
	 * 
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Collection<Fpici> fpici) {
		if (null != fpici && fpici.size() > 0) {
			for (Fpici item : fpici) {
				try {
					this.deleteData(item);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 这个方法用来判断在添加时，该机型中该批次是否已经存在
	 * 
	 * @param ftype
	 * @param fpici
	 *            用户输入的批次
	 */
	public String fpiciIsExists(String ftype, String fpici) {
		String hql = "select count(*) from " + Fpici.class.getName()
				+ " u where u.fTypeName.ftypename = :ftypename and u.piciName = :fpici";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("ftypename", ftype);
		parameterMap.put("fpici", fpici);
		int count = this.queryForInt(hql, parameterMap);
		String returnStr = "1";
		if (count > 0) {
			returnStr = "此批次已存在！";
		}
		return returnStr;
	}

}