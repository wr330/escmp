package com.buaa.fly.fighterxzh.dao;

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

import com.buaa.fly.domain.Fighterxzh;

@Repository("fighterxzhDao")
public class FighterxzhDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryFighterxzh(Page<Fighterxzh> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Fighterxzh.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String ftypename = (String)parameter.get("ftypename");
        	if(StringUtils.isNotEmpty( ftypename )){
        		coreHql.append(" and a.ftypename = :ftypename ");
        		args.put("ftypename", ftypename );	
        	}
        	String filename = (String)parameter.get("filename");
        	if(StringUtils.isNotEmpty( filename )){
        		coreHql.append(" and a.filename = :filename ");
        		args.put("filename", filename );	
        	}
        	String piciname = (String)parameter.get("piciname");
        	if(StringUtils.isNotEmpty( piciname )){
        		coreHql.append(" and a.piciname = :piciname ");
        		args.put("piciname", piciname );	
        	}
        	String outfactoryno = (String)parameter.get("outfactoryno");
        	if(StringUtils.isNotEmpty( outfactoryno )){
        		coreHql.append(" and a.outfactoryno = :outfactoryno ");
        		args.put("outfactoryno", outfactoryno );	
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
	public void saveData(Fighterxzh detail) throws Exception {
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
	public void updateData(Fighterxzh detail) throws Exception {
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
	public void deleteData(Fighterxzh detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}