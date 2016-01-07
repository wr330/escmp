package com.buaa.fly.sffault.dao;

import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Sffault;

@Repository("sffaultDao")
public class SffaultDao extends HibernateBaseDao {
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
	public void querySffault(Page<Sffault> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Sffault.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String ftype = (String)parameter.get("ftype");
        	if(StringUtils.isNotEmpty( ftype )){
        		coreHql.append(" and a.ftype ='" + ftype + "'");
        	}
        	String gzfs = (String)parameter.get("gzfs");
        	if(StringUtils.isNotEmpty( gzfs )){
        		coreHql.append(" and a.gzfs like '%" + gzfs + "%'");
        	}
        	String type = (String)parameter.get("type");
        	if(StringUtils.isNotEmpty( type )){
        		coreHql.append(" and a.type like '%" + type + "%'");
        	}
        	String status = (String)parameter.get("status");
        	if(StringUtils.isNotEmpty( status )){
        		coreHql.append(" and a.status ='" + status + "'");
        	}
        	String bumen = (String)parameter.get("bumen");
        	if(StringUtils.isNotEmpty( bumen )){
        		coreHql.append(" and ( a.bumen ='" + bumen + "' or a.relatebumen ='" + bumen + "')");
        	}
        	String majorsystem = (String)parameter.get("majorsystem");
        	if(StringUtils.isNotEmpty( majorsystem )){
        		coreHql.append(" and ( a.majorsystem ='" + majorsystem + "' or a.relatemajor ='" + majorsystem + "')");
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
        hql=userService.checkUser(hql);
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Sffault detail) throws Exception {
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
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Sffault detail) throws Exception {
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
	public void deleteData(Sffault detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}