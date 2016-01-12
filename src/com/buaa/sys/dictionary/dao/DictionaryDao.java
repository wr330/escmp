package com.buaa.sys.dictionary.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.common.HibernateBaseDao;

import com.buaa.sys.domain.Dictionary;

@Repository("dictionaryDao")
public class DictionaryDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public Collection<Dictionary> queryDictionary(Map<String, Object> parameter,Criteria criteria) throws Exception {
       // Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Dictionary.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String dictype = (String)parameter.get("dictype");
        	if(StringUtils.isNotEmpty( dictype )){
        		coreHql.append(" and a.dictype ='" + dictype + "'");
        	}
			if (parameter.containsKey("status")) {
				Object status =  parameter.get("status");// 添加筛选：状态=有效
				if (status.toString() == "0") {
					coreHql.append(" and a.status =" + status);
				}
			}
        }
		
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and "+ result.getAssemblySql());
				//args.putAll(result.getValueMap());
			}
		}
        //String countHql = "select count(*) " + coreHql.toString();//取消分页
        String hql = coreHql.toString() + "order by name";
        return this.query(hql);
		//this.pagingQuery(page, hql, countHql, args);
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Dictionary detail) throws Exception {
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
	public void updateData(Dictionary detail) throws Exception {
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
	public void deleteData(Dictionary detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public String dictionaryIsExists(String oid,String name,String dictype) {
		String hql = "select count(*) from " + Dictionary.class.getName()
				+ " u where u.name = :name and dictype = '" + dictype + "'";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("name", name);
		if (oid != null) {
			hql += " and u.oid != :oid";
			parameterMap.put("oid", oid);
		}
		int count = this.queryForInt(hql, parameterMap);

		String returnStr = null;
		if (count > 0) {
			returnStr = "此名称已存在！";
		}
		return returnStr;
	}     
}