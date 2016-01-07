package com.buaa.fly.sfstatistic.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.buaa.fly.domain.Sfstatistic;
import com.common.HibernateBaseDao;
@Component("totalService")
public class TotalService   extends HibernateBaseDao {
	//获取总起落数及总飞行时间
	@DataProvider
	public Total loadTotal(Map<String, Object> parameter){
		String hql = "from " + Sfstatistic.class.getName()+" a where 1=1";
		 if(null != parameter && !parameter.isEmpty()){
	        	String ftype = (String)parameter.get("ftype");
	        	if(StringUtils.isNotEmpty( ftype )){
	        		hql += " and a.ftype ='" + ftype + "'";
	        	}
	     }
		Collection<Sfstatistic> sfstatistics = this.query(hql);
		int count = 0,thisyearcount = 0;
		long timespan1 = 0,timespan2 = 0;
		int thisyear = new Date().getYear();
		for(Sfstatistic sfstatistic : sfstatistics){
			Date timeland = sfstatistic.getTimeland();
			Date timeqifei = sfstatistic.getTimeqifei();
			if(timeland != null && timeqifei != null){
			    timespan1 += (timeland.getTime()-timeqifei.getTime())/1000;
			    if(timeqifei.getYear() == thisyear){//是否为本年度
			    	timespan2 += (timeland.getTime()-timeqifei.getTime())/1000;
			    	thisyearcount++;
			    }
			}
			count++;
		}
		Total total = new Total();
		total.setCount("总起落数：" + count);
		total.setTime("总飞行时间为：" + timeFormat(timespan1));
	    total.setThisyearcount("本年度总起落数：" + thisyearcount);
	    total.setThisyeartime("本年度总飞行时间为：" + timeFormat(timespan2));
		return total;
	}
	
	//获取单机总起落数及总飞行时间
	@DataProvider
	public Total loadflight(String parameter){
		String hql = "from " + Sfstatistic.class.getName()+" a where 1=1";
		 //if(null != parameter && !parameter.isEmpty()){
	        	//String ftype = (String)parameter.get("ftype");
	        	if(StringUtils.isNotEmpty( parameter )){
	        		hql += " and a.fighterno ='" + parameter + "'";
	        	}
	     //}
		Collection<Sfstatistic> sfstatistics = this.query(hql);
		int count = 0,thisyearcount = 0;
		long timespan1 = 0,timespan2 = 0;
		int thisyear = new Date().getYear();
		for(Sfstatistic sfstatistic : sfstatistics){
			Date timeland = sfstatistic.getTimeland();
			Date timeqifei = sfstatistic.getTimeqifei();
			if(timeland != null && timeqifei != null){
			    timespan1 += (timeland.getTime()-timeqifei.getTime())/1000;
			    if(timeqifei.getYear() == thisyear){//是否为本年度
			    	timespan2 += (timeland.getTime()-timeqifei.getTime())/1000;
			    	thisyearcount++;
			    }
			}
			count++;
		}
		Total total = new Total();
		total.setCount("总起落数：" + count);
		total.setTime("总飞行时间为：" + timeFormat(timespan1));
	    total.setThisyearcount("本年度总起落数：" + thisyearcount);
	    total.setThisyeartime("本年度总飞行时间为：" + timeFormat(timespan2));
		return total;
	}
	//获取月架次统计
	@DataProvider
	public Collection<MonthTotal> loadMonthTotal(Map<String, Object> parameter){
		ArrayList<MonthTotal> monthTotals = new ArrayList<MonthTotal>();
		String hql = "from " + Sfstatistic.class.getName()+" a where 1=1";
		 if(null != parameter && !parameter.isEmpty()){
	        	String ftype = (String)parameter.get("ftype");
	        	if(StringUtils.isNotEmpty( ftype )){
	        		hql += " and a.ftype ='" + ftype + "'";
	        	}
	     }
		int thisyear = new Date().getYear() + 1900;
		for(int month = 1;month<=12;month++){
			String coreHql = hql + " and a.fdate>='" + thisyear + "-" + month +"-1 00:00:00' and a.fdate<'" + (month==12?thisyear+1:thisyear) + "-" +(month==12?1:month+1) + "-1 00:00:00'";
			Collection<Sfstatistic> sfstatistics = this.query(coreHql);
			int count = 0;
			long timespan = 0;
			for(Sfstatistic sfstatistic : sfstatistics){
				Date timeland = sfstatistic.getTimeland();
				Date timeqifei = sfstatistic.getTimeqifei();
				if(timeland != null && timeqifei != null){
				    timespan += (timeland.getTime()-timeqifei.getTime())/1000;
				}
				count++;
			}
			MonthTotal monthTotal = new MonthTotal();
			monthTotal.setMonth(month);
			monthTotal.setCount(count);
			monthTotal.setTime(timeFormat(timespan));
			monthTotals.add(monthTotal);
		}
		return monthTotals;
	}
	private String timeFormat(long timespan){
	    long miao = timespan%60;
	    long fen = (timespan/60)%60;
	    long shi = timespan/3600;
	    return (shi<10?"0"+shi:shi) + ":" +  (fen<10?"0"+fen:fen) + ":" +  (miao<10?"0"+miao:miao);
	}
}
