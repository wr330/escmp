package com.buaa.out.handover.dao;

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
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Dailyacc;
import com.buaa.out.domain.Handover;

@Repository("handoverDao")
public class HandoverDao extends HibernateBaseDao {
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
	public void queryHandover(Page<Handover> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Handover.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String oid = (String)parameter.get("oid");
        	if(StringUtils.isNotEmpty( oid )){
        		coreHql.append(" and a.supportprogram.oid = :oid ");
        		args.put("oid", oid);
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
        hql += "order by handovertime desc ";
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Handover detail) throws Exception {
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
	public void updateData(Handover detail) throws Exception {
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
	public void deleteData(Handover detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public String handoverIsExists(String oid,String number) {
		String hql = "select count(*) from " + Handover.class.getName()
				+ " u where u.filenumber = :number";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("number", number);
		if (oid != null) {
			hql += " and u.oid != :oid";
			parameterMap.put("oid", oid);
		}
		int count = this.queryForInt(hql, parameterMap);

		String returnStr = null;
		if (count > 0) {
			returnStr = "此编号已存在！";
		}
		return returnStr;
	}   
	//通过ID查找记录
    public  Handover queryById(String oid){
    	String hql="from " + Handover.class.getName()+" a where a.oid = "+oid;
		return (Handover) this.query(hql).get(0);
    }   
}