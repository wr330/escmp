package com.buaa.comm.btReportSharePerson.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.comm.domain.BtReportSharePerson;
import com.common.HibernateBaseDao;

@Repository("btReportSharePersonDao")
public class BtReportSharePersonDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryBtReportSharePerson(Page<BtReportSharePerson> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + BtReportSharePerson.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
			String oid = (String) parameter.get("oid");
			if (StringUtils.isNotEmpty(oid)) {
				coreHql.append(" and a.btreport.oid = :oid ");
				args.put("oid", oid);
			}
			String sharePerson = (String) parameter.get("sharePerson");
			if (StringUtils.isNotEmpty(sharePerson)) {
				coreHql.append(" and a.userName = :spun ");
				args.put("spun", sharePerson);
			}
			String status = (String) parameter.get("status");
			if (StringUtils.isNotEmpty(status)) {
				Integer sta = Integer.parseInt(status);
				coreHql.append(" and a.btreport.status >= :ss ");
				args.put("ss", sta);
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
        hql += " order by readStatus asc, firstReadTime desc";
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**                  
	* 根据用户名，按共享人用户名是此用户名且阅读状态是未阅的条件，搜索出差报告共享人群表
	* @param username    
	* @throws Exception
	*/
	public Integer queryBtreportShare(String username) throws Exception {
		Map<String, Object> args = new HashMap<String,Object>();
		StringBuffer coreHql = new StringBuffer("from " + BtReportSharePerson.class.getName()+" a where 1=1 ");
		if (StringUtils.isNotEmpty(username)) {
			coreHql.append(" and a.userName = :spun ");
			args.put("spun", username);
		}
        String hql = coreHql.toString() + "and a.readStatus = 0";
        Collection<BtReportSharePerson> dataItems = this.query(hql,args);
        Integer btreportShareCount = dataItems.size();
        return btreportShareCount;
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(BtReportSharePerson detail) throws Exception {
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
	public void updateData(BtReportSharePerson detail) throws Exception {
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
	public void deleteData(BtReportSharePerson detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}
