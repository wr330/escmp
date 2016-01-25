package com.buaa.fly.fighterinfo.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.domain.Fpici;

@Repository("fighterinfoDao")
public class FighterinfoDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryFighterinfo(Page<Fighterinfo> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Fighterinfo.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
	String piciname = (String)parameter.get("piciname");
	if(StringUtils.isNotEmpty( piciname )){
		coreHql.append(" and a.piciid.piciID like :piciname ");
		args.put("piciname",piciname);	
	}
 	/*String ftypename = (String)parameter.get("ftypename");
	if(StringUtils.isNotEmpty( ftypename )){
		coreHql.append(" and a.ftypes.ftypename like :ftypename ");
		args.put("ftypename",ftypename);	
	}*/
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
	public void saveData(Fighterinfo detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	public Fighterinfo queryFighterinfo1(String parameter) {
		String hql = " from " + Fighterinfo.class.getName()
				+ " u where u.outfactoryno like '"+parameter+"'";
		List<Fighterinfo> info= this.query(hql);
		return info.get(0);
	} 
	
	public Collection<Fighterinfo> queryFighterinfobyPici(String parameter) {
		String hql = " from " + Fighterinfo.class.getName()
				+ " u where u.piciid.piciid like '"+parameter+"'";
		Collection<Fighterinfo> info= this.query(hql);
		return info;
	} 
	

	public Integer conlumIdentity() {
		String hql = "select count(*) from " + Fighterinfo.class.getName()
				+ " u where 1=1";
		int count = this.queryForInt(hql);
		return count+1;
	} 
	/**
	 * 数据修改
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Fighterinfo detail) throws Exception {
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
	public void deleteData(Fighterinfo detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}