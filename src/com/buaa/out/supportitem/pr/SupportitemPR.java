package com.buaa.out.supportitem.pr;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.out.domain.Supportitem;
import com.buaa.out.domain.Supportprogram;
import com.buaa.out.supportitem.manager.SupportitemManager;



@Component("supportitemPR")
public class SupportitemPR{

    @Resource
	private SupportitemManager supportitemManager;

     
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
	 @DataProvider
		public Collection<Map<String, Object>> statisticRenyuan(Map<String, Object> parameter) throws Exception {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Collection<Supportitem> dataItems = supportitemManager.queryRenyuan(parameter);
			Integer num[] = new Integer[12];
			for(int i = 0; i < 12; i++){
				num[i] = 0;
			}
			for (Supportitem item : dataItems) {
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				start.setTime(item.getStartexecutiontime());
				end.setTime(item.getEndexecutiontime());
				int mouthst = start.get(Calendar.MONTH);
				int mouthen = end.get(Calendar.MONTH);
				if(mouthen==mouthst)
					num[mouthen]++;
				else{
					for(int i = 0; i < 12; i++){
					if(mouthst<=i&&i<=mouthen){
						num[i]++;
					}
					}
				}	
			}	
			for(int i = 0; i < 12; i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
				Integer yue = i + 1;
				String mouth = yue.toString() +"月";
				map.put("mouth", mouth);
				map.put("num", num[i]);
				list.add(map);
			}
			return list;
		}
	
	 /**                  
	  * 保障计划执行的总人天统计    
	  * @param map
	  * @throws Exception
	  */
	@DataProvider
	public Map<String, Object> statisticSupportitem(Map<String, Object> parameter) throws Exception {
		List<Map<String, Object>> listSupport = new ArrayList<Map<String, Object>>();
		Collection<Supportprogram> dataItems = supportitemManager.queryProgram(parameter);
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
			String oid = item.getOid();
			HashMap<String, Object> parameter1 = new HashMap<String, Object>();
			parameter1.put("oid", oid);
			parameter1.put("year", yearInt);
			Collection<Supportitem> dataSupportitem = supportitemManager.queryItem(parameter1);
			for(Supportitem itemSup : dataSupportitem){
				Long day = (long) 0;
				if(itemSup.getEndexecutiontime() == null){
					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					start.setTime(itemSup.getStartexecutiontime());
					end.set(yearInt, 12, 31);
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
					time.set(yearInt, 1, 1);
					time1.set(yearInt, 12, 31);
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
	
}
