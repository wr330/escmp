package com.buaa.fly.flightrestrict.dao;

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
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Fighterxzh;
import com.buaa.fly.domain.Flightrestrict;

@Repository("flightrestrictDao")
public class FlightrestrictDao extends HibernateBaseDao {
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
	public void queryFlightrestrict(Page<Flightrestrict> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Flightrestrict.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String ftype = (String)parameter.get("ftype");
        	if(StringUtils.isNotEmpty( ftype )){
        		coreHql.append(" and a.ftype ='" + ftype + "'");
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
	
	public Collection<Flightrestrict> queryshfxzh(Map<String, Object> parameter) throws Exception {
        String Hql = "from " + Flightrestrict.class.getName()+" a where 1=1 ";
        if(null != parameter && !parameter.isEmpty()){
        	String ftype = (String)parameter.get("ftypename");
        	if(StringUtils.isNotEmpty( ftype )){
        		Hql += " and a.ftype ='" + ftype + "'";
        	}
        	String piciname = (String)parameter.get("piciname");
        	if(StringUtils.isNotEmpty( piciname )){
        		Hql += " and a.filename in ( select filename from " + Fighterxzh.class.getName() + " b where b.piciname = '" 
        				+ piciname + "')";
        	}
        	String outfactoryno = (String)parameter.get("outfactoryno");
        	if(StringUtils.isNotEmpty( outfactoryno )){
        		Hql += " and a.filename in ( select filename from " + Fighterxzh.class.getName() + " b where b.outfactoryno = '" 
        				+ outfactoryno + "')";
        	}
        }
        Hql=userService.checkUser(Hql);
		return this.query(Hql);
	}
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Flightrestrict detail) throws Exception {
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
	public void updateData(Flightrestrict detail) throws Exception {
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
	public void deleteData(Flightrestrict detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	//通过ID查找记录
    public  Flightrestrict queryById(String id){
    	String hql="from " + Flightrestrict.class.getName()+" a where a.id="+id;
		return (Flightrestrict) this.query(hql).get(0);
    }
}