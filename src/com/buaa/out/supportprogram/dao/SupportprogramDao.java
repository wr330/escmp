package com.buaa.out.supportprogram.dao;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;



import java.util.Date;

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



@Repository("supportprogramDao")
public class SupportprogramDao extends HibernateBaseDao {
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
	public void querySupportprogram(Page<Supportprogram> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Supportprogram.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	
        	String address = (String)parameter.get("address");
        	if(StringUtils.isNotEmpty( address )){
        		coreHql.append(" and a.workaddress = :oid ");
        		args.put("oid", address );
        		}
        	String parentnode = (String)parameter.get("parentnode");
        	if(StringUtils.isNotEmpty( parentnode )){
        		coreHql.append(" and a.parentnode = :parentnode ");
        		args.put("parentnode", parentnode );
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
        hql = userService.checkUser(hql);
        hql += " order by worktime desc, workaddress asc";
		this.pagingQuery(page, hql, countHql, args);
	}
	
	public Collection<Supportprogram> queryAddress(String address) throws Exception {
		String hql="from "+Supportprogram.class.getName()+" u where 1=1";
		Map<String,Object> map=new HashMap<String,Object>();	
		if(StringUtils.isEmpty(address)){
			
			return this.query(hql,map);
		}else{
			map.put("workaddress",address);
			hql+=" and u.workaddress=:workaddress";
			
			return this.query(hql,map);			
		}
	}
	public Collection<Supportitem> queryTime(String oid) throws Exception {
		String hql="from "+Supportitem.class.getName()+" u where 1=1";
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isEmpty(oid)){
			hql+=" and u.supportprogram is null";
			return this.query(hql,map);
		}else{
			Date now = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(now);
			map.put("oid",oid);
			hql+=" and u.supportprogram.oid =:oid";
			hql+=" and ((u.startexecutiontime < '"+ time +"' and u.endexecutiontime > '"+ time +"') or (u.startexecutiontime < '"+ time +"' and u.endexecutiontime = null))";
			return this.query(hql,map);	
		}
	}

	/**                  
	* 根据地点搜索本年度的保障计划，并根据保障计划和此时的事件查询保障执行条目表
	* @param parameter    
	* @throws Exception
	*/
	public Collection<Supportitem> queryRenyuan(Map<String, Object> parameter) throws Exception {
	    String address = (String)parameter.get("address");
	    Collection<Supportprogram> details = this.queryAddress(address);
	    Collection<Supportitem> items = new ArrayList<Supportitem>();
	    if (null != details && details.size() > 0) {
	    	for(Supportprogram item : details) {
	    		String oid = item.getOid();
	    		Collection<Supportitem> detail = this.queryTime(oid);
	    		items.addAll(detail);
	    	}
	    }
		return items;
	}
	
	/**                  
	* 搜索计划开始时间到结束时间涉及本年度的保障计划
	* @param parameter    
	* @throws Exception
	*/
	public Collection<Supportprogram> queryProgram(Map<String, Object> parameter) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		String hql="from "+Supportprogram.class.getName()+" u where 1=1";
		Integer year = (Integer)parameter.get("year");
		String time = year + "/01/01";
		String time1 = year + "/12/31";
		//hql+=" and ((u.worktime>='" + time + "' and u.endtime<='" + time1 + "') or (u.worktime<'" + time + "' and u.endtime>'" + time + "') or (u.worktime>='" + time + "' and u.worktime<'" + time1 + "'and u.endtime >'" + time1 + "') or (u.worktime >='" + time + "' and u.worktime < '" + time1 +"'))";
		hql+=" and ((u.worktime<'" + time + "' and u.endtime>'" + time +  "') or (u.worktime >='" + time + "' and u.worktime < '" + time1 +"'))";
		return this.query(hql,map);						
	}
	
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Supportprogram detail) throws Exception {
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
	public void updateData(Supportprogram detail) throws Exception {
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
	public void deleteData(Supportprogram detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}