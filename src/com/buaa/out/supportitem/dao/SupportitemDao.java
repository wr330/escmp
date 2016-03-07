package com.buaa.out.supportitem.dao;



import java.text.SimpleDateFormat;
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
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.out.domain.Supportitem;
import com.buaa.out.domain.Supportprogram;
import com.common.HibernateBaseDao;



@Repository("supportitemDao")
public class SupportitemDao extends HibernateBaseDao {
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
	public void querySupportitem(Page<Supportitem> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Supportitem.class.getName()+" a  where 1=1 ");
        
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
        hql+="order by startexecutiontime asc ";
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**                  
	* 搜索从计划开始时间到结束时间涉及本年度的保障计划
	* @param parameter    
	* @throws Exception
	*/
	public Collection<Supportprogram> queryProgram(Map<String, Object> parameter) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		String hql="from " + Supportprogram.class.getName() + " u where 1=1";
		Integer year = (Integer)parameter.get("year");
		String time = year + "/01/01";
		String time1 = year + "/12/31";
		//hql+=" and ((u.worktime>='" + time + "' and u.endtime<='" + time1 + "') or (u.worktime<'" + time + "' and u.endtime>'" + time + "') or (u.worktime>='" + time + "' and u.worktime<'" + time1 + "'and u.endtime >'" + time1 + "') or (u.worktime >='" + time + "' and u.worktime < '" + time1 +"'))";
		hql+=" and ((u.worktime<'" + time + "' and u.endtime>'" + time +  "') or (u.worktime >='" + time + "' and u.worktime < '" + time1 +"'))";
		return this.query(hql,map);						
	}
	
	/**                  
	* 通过主表保障计划的主键搜索保障条目
	* @param parameter    
	* @throws Exception
	*/
	public Collection<Supportitem> queryItem(Map<String, Object> parameter) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		String hql="from " + Supportitem.class.getName() + " u where 1=1";
		String oid = (String)parameter.get("oid");
		Integer year = (Integer)parameter.get("year");
		String time = year + "/01/01";
		String time1 = year + "/12/31";
		//map.put("oid",oid);
		//hql+=" and u.project.parentnode=:parentnode";
		hql+=" and (u.supportprogram.oid = '" + oid + "') and ((u.startexecutiontime <'" + time + "' and u.endexecutiontime > '" + time +  "') or (u.startexecutiontime >='" + time + "' and u.startexecutiontime < '" + time1 +"'))";
		return this.query(hql,map);						
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Supportitem detail) throws Exception {
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
	public void updateData(Supportitem detail) throws Exception {
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
	public void deleteData(Supportitem detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
public Collection<Supportitem> queryRenyuan(Map<String, Object> parameter) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		String hql="from "+Supportitem.class.getName()+" u where 1=1";
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String time = df.format(now)+"/01/01";
		String time1 = df.format(now)+"/12/31";
		hql+=" and u.startexecutiontime <='"+ time1 +"' and u.endexecutiontime >= '"+ time +"'";
	
		return this.query(hql,map);				
	}
        
}