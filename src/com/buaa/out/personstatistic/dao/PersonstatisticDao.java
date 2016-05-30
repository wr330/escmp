package com.buaa.out.personstatistic.dao;

import java.util.ArrayList;
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

import com.buaa.out.domain.Personstatistic;
import com.buaa.out.domain.Supportitem;
import com.buaa.out.domain.Supportprogram;

@Repository("personstatisticDao")
public class PersonstatisticDao extends HibernateBaseDao {

	/**
	 * 通过SQL语句返回Collection<Object []>
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Personstatistic> queryPersonstatistic(Map<String, Object> parameter) throws Exception {
        StringBuffer coreHql = new StringBuffer("select a.workaddress, b.registrationexecutor, SUM(b.days) AS SumDays from " + Supportprogram.class.getName() + " a," + Supportitem.class.getName() + " b where a.oid = b.supportprogram");
        if(null != parameter && !parameter.isEmpty()){
        	String address = (String)parameter.get("address");
        	if(StringUtils.isNotEmpty( address )){
        		coreHql.append(" and a.workaddress = '" + address +"'");
        	}
        	String person = (String)parameter.get("person");
        	if(StringUtils.isNotEmpty( person )){
        		coreHql.append(" and b.registrationexecutor = '" + person +"'");	
        	}
        	Integer year = (Integer)parameter.get("year");
			if(year != null){
				String time = year + "/01/01";
				String time1 = year + "/12/31";
				coreHql.append(" and ((b.startexecutiontime  <'" + time + "' and b.endexecutiontime > '" + time +  "') or (b.startexecutiontime >='" + time + "' and b.startexecutiontime < '" + time1 +"'))");
			}
			coreHql.append("group by a.workaddress, b.registrationexecutor");
        }
        String hql = coreHql.toString();
        Collection<Object []> items = this.query(hql);
        Collection<Personstatistic> details = new ArrayList<Personstatistic>();
	    if (null != items && items.size() > 0) {
	    	for(Object[] item : items) {
	    		Personstatistic detail =new Personstatistic();
	    		detail = transformPersonStatistic(item);
	    		details.add(detail);
	    	}
	    }
        return details;
	}
	
	/**
	 * 数据库搜索出的记录转换为人员统计(Personstatistci)对象
	 */
    private Personstatistic transformPersonStatistic(Object[] item){
    	Personstatistic detail =new Personstatistic();
		String str = (String) item[0];
		detail.setWorkaddress(str);
		String str1 = (String) item[1];
		detail.setRegistrationexecutor(str1);
		Number str2 = (Number) item[2];
		detail.setDays(str2.intValue());
		return detail;
    }   
}