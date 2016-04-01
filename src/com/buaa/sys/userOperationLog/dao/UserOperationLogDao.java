package com.buaa.sys.userOperationLog.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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

import com.buaa.sys.domain.UserOperationLog;

@Repository("userOperationLogDao")
public class UserOperationLogDao extends HibernateBaseDao{
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
	public void queryUserOperationLog(Page<UserOperationLog> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
		
		Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + UserOperationLog.class.getName()+" a where 1=1 ");
        if (null != parameter && !parameter.isEmpty()) {
			String operationType = (String) parameter.get("operationType");
			int a;
			if (StringUtils.isNotEmpty(operationType)) {
				coreHql.append(" and a.logOperationType = :ot ");
				a = Integer.valueOf(operationType).intValue();
				
				args.put("ot",  a);
			} 
        
        }
        
        if (null != parameter && !parameter.isEmpty()) {
			String operationPerson = (String) parameter.get("operationPerson");
			if (StringUtils.isNotEmpty(operationPerson)) {
				coreHql.append(" and a.operationPerson = :op ");
				args.put("op",  operationPerson );
			}
        
        }
        
        if (null != parameter && !parameter.isEmpty()) {
			Date operationTime1 = (Date) parameter.get("operationTime1");
			Date operationTime2 = (Date) parameter.get("operationTime2");
			if (operationTime1 != null) {
				coreHql.append(" and a.operationTime between :operationTime1 and :operationTime2");
				args.put("operationTime1",  operationTime1 );
				args.put("operationTime2",  operationTime2 );
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
	public void saveData(UserOperationLog detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setOid(UUID.randomUUID().toString());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	public Integer conlumIdentity() {
		String hql = "select count(*) from " + UserOperationLog.class.getName()
				+ " u where 1=1";
		int count = this.queryForInt(hql);
		return count+1;
	} 
	
	/**
	 * 数据修改
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(UserOperationLog detail) throws Exception {
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
	public void deleteData(UserOperationLog detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}  
}
