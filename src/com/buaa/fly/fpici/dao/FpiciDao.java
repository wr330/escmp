package com.buaa.fly.fpici.dao;

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
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.domain.Fpici;
import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.fighterinfo.dao.FighterinfoDao;

@Repository("fpiciDao")
public class FpiciDao extends HibernateBaseDao {
	@Resource
private	FighterinfoDao fighterinfoDao;
	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryFpici(Page<Fpici> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Fpici.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
	String ftypename = (String)parameter.get("ftypename");
	if(StringUtils.isNotEmpty( ftypename )){
		coreHql.append(" and a.fTypeName.ftypename = :ftypename ");
		args.put("ftypename", ftypename );	
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
	
	
	public Collection<Fpici> queryFighterinfobyType(String parameter) {
		String hql = " from " + Fpici.class.getName()
				+ " u where u.fTypeName.ftypename like '"+parameter+"'";
		Collection<Fpici> info= this.query(hql);
		return info;
	} 
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Fpici detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setPiciID(UUID.randomUUID().toString());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	
	public Integer conlumIdentity() {
		String hql = "select count(*) from " + Fpici.class.getName()
				+ " u where 1=1";
		int count = this.queryForInt(hql);
		return count+1;
	} 
	/**
	 * 数据修改
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Fpici detail) throws Exception {
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
	public void deleteData(Fpici detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		Collection<Fighterinfo> items=fighterinfoDao.queryFighterinfobyPici(detail.getPiciID());
		if(items!=null){
			for(Fighterinfo item:items){
				fighterinfoDao.deleteData(item);
			}
		}
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	public void deleteData(Collection<Fpici> fpici) {
		if (null != fpici && fpici.size() > 0) {
			for (Fpici item : fpici) {
			try {
				this.deleteData(item);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	}
        
}