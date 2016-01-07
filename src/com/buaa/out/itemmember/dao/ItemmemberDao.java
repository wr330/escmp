package com.buaa.out.itemmember.dao;



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
import com.buaa.out.domain.Itemmember;
import com.common.HibernateBaseDao;



@Repository("itemmemberDao")
public class ItemmemberDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryItemmember(Page<Itemmember> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Itemmember.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
	String recipientoid = (String)parameter.get("recipientoid");
	if(StringUtils.isNotEmpty( recipientoid )){
		coreHql.append(" and a.handover.oid like :oid ");
		args.put("oid","%" + recipientoid + "%");	
	}
   	String tid = (String)parameter.get("tid");
	if(StringUtils.isNotEmpty( tid )){
		coreHql.append(" and a.supportitem.id like :id ");
		args.put("id","%" + tid + "%");	
	}
           }
		
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
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Itemmember detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setId(UUID.randomUUID().toString());
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
	public void updateData(Itemmember detail) throws Exception {
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
	public void deleteData(Itemmember detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}