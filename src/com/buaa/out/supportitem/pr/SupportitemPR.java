package com.buaa.out.supportitem.pr;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.out.domain.Supportitem;
import com.buaa.out.domain.Supportprogram;
import com.buaa.out.supportitem.manager.SupportitemManager;
import com.buaa.out.supportprogram.manager.SupportprogramManager;



@Component("supportitemPR")
public class SupportitemPR{

    @Resource
	private SupportitemManager supportitemManager;
    @Resource
   	private SupportprogramManager supportprogramManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void querySupportitem(Page<Supportitem> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportitemManager.querySupportitem(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveSupportitem(Map<String, Collection> dataItems) throws Exception {
	    supportitemManager.saveSupportitem(dataItems);
	 }
	 
	 /**                  
	  * 保障计划执行的总人天统计，用于外场保障计划变更与执行管理    
	  * @param parameter
	  * @throws Exception
	  */
	@DataProvider
	public Map<String, Object> statisticSupportitem(Map<String, Object> parameter) throws Exception {
		List<Map<String, Object>> listSupport = new ArrayList<Map<String, Object>>();
		Collection<Supportprogram> dataItems = supportprogramManager.queryProgram(parameter);
		if(dataItems.isEmpty()){
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("data", null);
			map1.put("amountPersonDay",null);
			return map1;
		}
		Integer amountSP = dataItems.size();
		Integer yearInt = (Integer)parameter.get("year");
		Long num[] = new Long[amountSP];
		for(int i = 0; i < amountSP; i++){
				num[i] =  (long) 0;
		}
		String troop[] = new String[amountSP];
		int countSP = 0;
		for (Supportprogram item : dataItems) {
			String fatherOid = item.getOid();
			HashMap<String, Object> parameter1 = new HashMap<String, Object>();
			parameter1.put("fatherOid", fatherOid);
			parameter1.put("year", yearInt);
			Collection<Supportitem> dataSupportitem = supportitemManager.queryItem(parameter1);
			for(Supportitem itemSup : dataSupportitem){
				Long day = (long) 0;
				if(itemSup.getEndexecutiontime() == null){
					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					start.setTime(itemSup.getStartexecutiontime());
					end.set(yearInt, 11, 31);
					day = (end.getTimeInMillis() - start.getTimeInMillis()) / (24 * 60 * 60 * 1000); 
					num[countSP] = num[countSP] + day;
				}
				else{
					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					Calendar time = Calendar.getInstance();
					Calendar time1 = Calendar.getInstance();
					start.setTime(itemSup.getStartexecutiontime());
					end.setTime(itemSup.getEndexecutiontime());
					time.set(yearInt, 0, 1);
					time1.set(yearInt, 11, 31);
					int yearst = start.get(Calendar.YEAR);
					int yearen = end.get(Calendar.YEAR);
					//if(((start.compareTo(time) < 0) && (end.compareTo(time) > 0)) ||((start.compareTo(time) > 0) && (start.compareTo(time1) < 0))){
					if(yearst != yearen){
						if((yearst != yearInt)&&(yearen == yearInt)){
							day = (end.getTimeInMillis() - time.getTimeInMillis()) / (24 * 60 * 60 * 1000);
							num[countSP] = num[countSP] + day;
						}
						else if((yearst == yearInt)&&(yearen != yearInt)){
							day = (time1.getTimeInMillis() - start.getTimeInMillis()) / (24 * 60 * 60 * 1000);
							num[countSP] = num[countSP] + day;
						}
						else{
							day = (time1.getTimeInMillis() - time.getTimeInMillis()) / (24 * 60 * 60 * 1000);
							num[countSP] = num[countSP] + day;
						}
					}
					else{
						day = (end.getTimeInMillis() - start.getTimeInMillis()) / (24 * 60 * 60 * 1000);
						num[countSP] = num[countSP] + day;
					}
				}	
			}
			troop[countSP] = item.getTroop();
			countSP ++;
		}
		
		Long amountPersonDay = (long) 0;
		for(int i = 0; i < amountSP; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("troop", troop[i]);
			map.put("num", num[i]);
			listSupport.add(map);
			amountPersonDay = amountPersonDay + num[i];
		}
		
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("data", listSupport);
		map1.put("amountPersonDay",amountPersonDay);
		
		return map1;
	}
	
	/**                  
	  * 保障计划执行人对本保障计划执行情况的统计 ，用于人员统计   
	  * @param parameter
	  * @throws Exception
	  */
	@DataProvider
	public Map<String, Object> statisticPerson(Map<String, Object> parameter) throws Exception {
		Collection<Map<String, Object>> listSupportitem = new ArrayList<Map<String, Object>>();
		Collection<Map<String, Object>> listSupportPercent = new ArrayList<Map<String, Object>>();
		Collection<Supportitem> dataItems = supportitemManager.queryItem(parameter);
		Integer amountSP = dataItems.size();
		Long num[] = new Long[amountSP];
		for(int i = 0; i < amountSP; i++){
				num[i] =  (long) 0;
		}
		String person[] = new String[amountSP];
		int countSP = 0;
		for (Supportitem item : dataItems) {
			//考虑结束时间没有填写，默认在当年结束
			if(item.getEndexecutiontime() == null){
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				start.setTime(item.getStartexecutiontime());
				int year = start.get(Calendar.YEAR);
				end.set(year, 11, 31);
				//通过开始时间和结束时间计算相隔天数
				num[countSP] = (end.getTimeInMillis() - start.getTimeInMillis()) / (24 * 60 * 60 * 1000); 
			}
			else{
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				start.setTime(item.getStartexecutiontime());
				end.setTime(item.getEndexecutiontime());
				//通过开始时间和结束时间计算相隔天数
				num[countSP] = (end.getTimeInMillis() - start.getTimeInMillis()) / (24 * 60 * 60 * 1000);
				}
			person[countSP] = item.getRegistrationexecutor();
			countSP ++;
		}
		//利用Set类型来对字符数组去除重复元素
		Set<String> set = new TreeSet<String>();   
		int len = person.length;   
		for(int i=0;i<len;i++){     
			set.add(person[i]);//将所有字符串添加到Set   
		}
		int amountSP1 = set.size();
		String person1[] = new String[amountSP1];
		person1 = (String[]) set.toArray(new String[0]);
		Long num1[] = new Long[amountSP1];
		for(int i = 0; i < amountSP1; i++){
			num1[i] =  (long) 0;
		}
		//对相同的执行人的工作天数相加
		for(int i = 0;i < amountSP1;i ++ ){
			for(int j = 0;j < amountSP;j ++){
				if(person1[i] == person[j]){
					num1[i] = num1[i] + num[j];
				}
			}
		}
		Long amountNum = (long) 0;
		//把计划执行人和此人在此计划中执行的天数用Map封装
		for(int i = 0; i < amountSP1; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("person", person1[i]);
			map.put("num", num1[i]);
			listSupportitem.add(map);
			amountNum += num1[i];
		}
		//计算每个人工作量占总工作天数的百分比
		float percent = 1.00f;
		for(int i = 0; i < amountSP1; i++){
			HashMap<String, Object> mapPer = new HashMap<String, Object>();
			percent = (float)num1[i] / amountNum;
			mapPer.put("person", person1[i]);
			mapPer.put("percent", percent);
			listSupportPercent.add(mapPer);
		}
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("data", listSupportitem);
		map1.put("per", listSupportPercent);
		return map1;
	}
	
	/**                  
	  * 保障计划执行人对本保障计划执行情况的统计 ，用于人员统计   
	  * @param parameter
	  * @throws Exception
	  */
	@DataProvider
	public Map<String, Object> statisticPersonNumber(Map<String, Object> parameter) throws Exception {
		Collection<Supportitem> dataItems = supportitemManager.queryItem(parameter);
		Integer year = (Integer)parameter.get("year");
		List<Integer> num = new ArrayList<Integer>();
		List<String> date = new ArrayList<String>();
		Calendar time = Calendar.getInstance();		
		Calendar time1 = Calendar.getInstance();
		//Calendar currentDate = Calendar.getInstance();
		time.set(year, 0, 1, 0, 0, 0);
		time1.set(year, 11, 31, 0, 0, 0);
		//currentDate = time;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = (Calendar)time.clone();
		//判断是否是闰年,定义长度不一样的数组
		if(isLeapYear(year)){
			for(int i = 0; i < 366; i++){
				num.add(i, 0);
				date.add(i, sdf.format(currentDate.getTime()));
				currentDate.add(Calendar.DATE, 1);//日期增加一天
			}
		}
		else{
			for(int i = 0; i < 365; i++){
				num.add(i, 0);
				date.add(i, sdf.format(currentDate.getTime()));
				currentDate.add(Calendar.DATE, 1);//日期增加一天
			}
		}  
		for (Supportitem item : dataItems) {
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			//Calendar currentTime = Calendar.getInstance();
			start.setTime(item.getStartexecutiontime());
			end.setTime(item.getEndexecutiontime());
			int yearst = start.get(Calendar.YEAR);
			int yearen = end.get(Calendar.YEAR);
			if((yearst < year) && (yearen == year)){
				Calendar currentTime = (Calendar)time.clone();
				int j = 0;
				while(end.compareTo(currentTime) >= 0){
					num.set(j, num.get(j) + 1);
					currentTime.add(Calendar.DATE, 1);
					j++;
				}
			}
			else if((yearst < year) && (yearen > year)){
				Calendar currentTime = (Calendar)time.clone();
				int j = 0;
				while(time1.compareTo(currentTime) >= 0){
					num.set(j, num.get(j) + 1);
					currentTime.add(Calendar.DATE, 1);
					j++;
				}
			}
			else if((yearst == year) && (yearen > year)){
				Calendar currentTime = (Calendar)time.clone();
				int j = (int) ((start.getTimeInMillis() - time.getTimeInMillis()) / (24 * 60 * 60 * 1000));
				currentTime = (Calendar)start.clone();
				while(time1.compareTo(currentTime) >= 0){
					num.set(j, num.get(j) + 1);
					currentTime.add(Calendar.DATE, 1);
					j++;
					/*System.out.println(j); 
					Date dates = currentTime.getTime(); 
					Date dates1 = time1.getTime(); 
					System.out.println(dates); 
					System.out.println(dates1);*/
				}
			}
			else{
				Calendar currentTime = (Calendar)start.clone();
				int j = (int) ((start.getTimeInMillis() - time.getTimeInMillis()) / (24 * 60 * 60 * 1000));
				while(end.compareTo(currentTime) >= 0){
					num.set(j, num.get(j) +1);
					currentTime.add(Calendar.DATE, 1);
					j++;
					/*Date dates = currentTime.getTime(); 
					System.out.println(dates); */
				}
			}
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", num);
		map.put("date", date);
		return map;
	}
	
	/**                  
	  * 判断是否为闰年，是闰年返回true   
	  * @param year
	  */
	private boolean isLeapYear(Integer year){
		if(year%400==0){
			return true;
		}
		else if((year%4==0)&&(year%100!=0)){  
			return true;

		}
		else{   
			return false;
		}  
	}
}
