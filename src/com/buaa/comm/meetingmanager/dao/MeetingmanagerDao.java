package com.buaa.comm.meetingmanager.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.comm.domain.Meetingmanager;
import com.buaa.out.domain.Technicaldocument;

@Repository("meetingmanagerDao")
public class MeetingmanagerDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryMeetingmanager(Page<Meetingmanager> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Meetingmanager.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String address = (String)parameter.get("address");
        	if(StringUtils.isNotEmpty( address )){
        		coreHql.append(" and a.meetingaddress  ='" + address + "'");	
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
        hql+=" order by meetingstime desc ";
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Meetingmanager detail) throws Exception {
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
	public void updateData(Meetingmanager detail) throws Exception {
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
	public void deleteData(Meetingmanager detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
   
	
	/**
	 * 用于判断用户所输入申请会议室时间段是否已有预定
	 * @param parameter Map<String, Object> 参数为Autoform表单中当前记录
	 * @throws Exception
	 */
	public Boolean MeetingSTimeIsExists(Map<String, Object> parameter) {
		Meetingmanager mm = (Meetingmanager)parameter.get("meeting");
		Date startDa = mm.getMeetingstime();
		Date endDa = mm.getMeetingetime();
		String addr = mm.getMeetingaddress();
		
		//以Map方式来拼接sql查询语句,查询用户所输入申请会议室时间段是否与已有预定时间段冲突
		Map<String, Object> args = new HashMap<String,Object>();	
		String hql = "select count(*) from " + Meetingmanager.class.getName()
				+ " u where u.meetingaddress = :addr" 
				+ " and ((u.meetingstime < :start and u.meetingetime > :start)" 
				+ " or (u.meetingstime <:end  and u.meetingetime >:end)" 
				+ " or (u.meetingstime >:start and u.meetingetime < :end))";
		args.put("start",startDa);
		args.put("end",endDa);
		args.put("addr",addr);
		int count = this.queryForInt(hql,args);	
		
		/*
		 * 将时间转换成特定格式（2017-04-05 16:58:32），并拼接sql查询语句，查询用户所输入申请会议室时间段是否与已有预定时间段冲突
		*/
		
		/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = formatter.format(startDa);
		String end = formatter.format(endDa);	
		String hql = "select count(*) from " + Meetingmanager.class.getName()
				+ " u where u.meetingaddress = '" + addr 
				+ "' and ((u.meetingstime < '" + start + "' and u.meetingetime > '" + start 
				+ "') or (u.meetingstime < '" + end + "' and u.meetingetime > '" + end 
				+ "') or (u.meetingstime > '" + start + "' and u.meetingetime < '" + end 
				+ "'))";
		int count = this.queryForInt(hql);*/
			
		
		Boolean returnStr = null;
		if (count > 0) {
			returnStr = true;
		}
		else{
			returnStr = false;
		}
		return returnStr;
	}         
	
}