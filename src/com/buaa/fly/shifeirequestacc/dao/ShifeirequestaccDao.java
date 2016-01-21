package com.buaa.fly.shifeirequestacc.dao;

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
import com.buaa.fly.domain.Shifeirequestacc;

@Repository("shifeirequestaccDao")
public class ShifeirequestaccDao extends HibernateBaseDao {
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
	public void queryShifeirequestacc(Page<Shifeirequestacc> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Shifeirequestacc.class.getName()+" a where 1=1 ");
        
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
		Session session = this.getSessionFactory().openSession();
		List a = session.createSQLQuery("select datablock from Fly_Shifeirequestacc").list();
		byte[] datablock =(byte[]) a.get(0);
		int aa  = datablock.length;
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Shifeirequestacc detail) throws Exception {
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
	public void updateData(Shifeirequestacc detail) throws Exception {
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
	public void deleteData(Shifeirequestacc detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	//通过ID查找记录
    public  Shifeirequestacc queryById(String id){
    	String hql="from " + Shifeirequestacc.class.getName()+" a where a.id='"+id+"'";
		return (Shifeirequestacc) this.query(hql).get(0);
    }           
}