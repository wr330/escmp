package com.buaa.comm.addressbookdepart.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.comm.domain.Addressbookdepart;
import com.buaa.fly.domain.Subject;

@Repository("addressbookdepartDao")
public class AddressbookdepartDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public Collection<Addressbookdepart> queryAddressbookdepart(String parentnode) throws Exception {
		String hql="from "+Addressbookdepart.class.getName()+" u where 1=1";
		Map<String,Object> map=new HashMap<String,Object>();
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
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Addressbookdepart detail) throws Exception {
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
	public void updateData(Addressbookdepart detail) throws Exception {
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
	public void deleteData(Addressbookdepart detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	/**
	 * 计算节点下子节点个数
	 * @param parentnode
	 * @return
	 */
	public int countChildren(String parentnode) {
		String hql = "select count(*) from " + Addressbookdepart.class.getName() + " d where d.parentnode = :parentnode";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("parentnode", parentnode);
		return this.queryForInt(hql, parameterMap);
	}      
}