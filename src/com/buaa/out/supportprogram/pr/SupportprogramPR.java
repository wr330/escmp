package com.buaa.out.supportprogram.pr;



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
import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.ftypes.dao.FtypesDao;
import com.buaa.out.domain.Supportitem;
import com.buaa.out.domain.Supportprogram;
import com.buaa.out.supportprogram.manager.SupportprogramManager;
import com.buaa.out.supportprogram.dao.SupportprogramDao;



@Component("supportprogramPR")
public class SupportprogramPR{

    @Resource
	private SupportprogramManager supportprogramManager;
    @Resource
	private SupportprogramDao supportprogramDao;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void querySupportprogram(Page<Supportprogram> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportprogramManager.querySupportprogram(page,parameter,criteria);
	}
	/**                  
	* 查询顶级节点
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public Collection<Supportprogram> currentTree() throws Exception {
	    
	return supportprogramDao.query("from " + Supportprogram.class.getName()+" where parentnode = 0"
		);
	}
	
	/**                  
	  * 根据地点和时间查询保障计划执行情况表($此方法太繁琐，只在首页用，renyuantongji.view中已经停止使用了，改到SupportitemDao中进行统一查询$)
	  * @param parameter   
	  * @throws Exception
	  */
	@DataProvider
	public Collection<Supportitem> queryRenyuan(Map<String, Object> parameter) throws Exception {
		   return supportprogramManager.queryRenyuan(parameter);
	}
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveSupportprogram(Map<String, Collection> dataItems) throws Exception {
	    supportprogramManager.saveSupportprogram(dataItems);
	 }
	   /**                  
		* 分页查询信息，带有criteria
		* @param page    
		* @param map
		* @throws Exception
		*/
		@DataProvider
		public Collection<Map<String,Object>> getNumbers(Map<String, Object> parameter) throws Exception {
			 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			if(parameter!=null){
			   List<Map<String,Object>> list1= (List<Map<String, Object>>) parameter.get("count"); 
			  
			   Map<String,Object> map=null;
			  // Integer[] values=new Integer[]{1,2,4};
			   String labels[]=new String[]{"1月","2月","3月"};
			   for(int i=0,j=labels.length;i<j;i++){
				   map=new HashMap<String,Object>();
				   map.put("price", list1.get(i));
				   map.put("name", labels[i]);
				   list.add(map);
			   }
		   }
		   return list;
		}
		
		/**                  
		  * 保障计划的最大保障人数的统计    
		  * @param map
		  * @throws Exception
		  */
		@DataProvider
		public Map<String, Object> statisticSupport(Map<String, Object> parameter) throws Exception {
			List<Map<String, Object>> listSupport = new ArrayList<Map<String, Object>>();
			Collection<Supportprogram> dataItems = supportprogramManager.queryProgram(parameter);
			if(dataItems.isEmpty()){
				HashMap<String, Object> map1 = new HashMap<String, Object>();
				map1.put("data", null);
				map1.put("maxNumber",null);
				map1.put("maxMonth",null);
				return map1;
			}
			Integer yearInt = (Integer)parameter.get("year");
			Integer num[] = new Integer[12];
			for(int i = 0; i < 12; i++){
					num[i] = 0;
			}
			for (Supportprogram item : dataItems) {
				if(item.getEndtime() == null){
					Calendar start = Calendar.getInstance();
					start.setTime(item.getWorktime());
					int monthst = start.get(Calendar.MONTH);
					int people = item.getStaffrequirement();
					for(int i = monthst;i <= 11;i ++){
						num[i] = num[i] + people;
					}
				}
				else{
					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					start.setTime(item.getWorktime());
					end.setTime(item.getEndtime());
					int people = item.getStaffrequirement();
					int yearst = start.get(Calendar.YEAR);
					int yearen = end.get(Calendar.YEAR);
					int monthst = start.get(Calendar.MONTH);
					int monthen = end.get(Calendar.MONTH);
					if(yearst != yearen){
						if((yearst != yearInt)&&(yearen == yearInt)){
							for(int i = 0;i <= monthen;i ++){
								num[i] = num[i] + people;
							}
						}
						else if((yearst == yearInt)&&(yearen != yearInt)){
							for(int i = monthst;i <= 11;i ++){
								num[i] = num[i] + people;
							}
						}
						else{
							for(int i = 0;i <= 11;i ++){
								num[i] = num[i] + people;
							}
						}
					}
					else{
						if(monthen == monthst){
							num[monthen] = num[monthen] + people;
						}
						else{
							for(int i = 0; i < 12; i++){
								if(monthst<=i&&i<=monthen){
									num[i] = num[i] + people;
								}
							}
						}
					}	
				}
			}
			String maxMonth = null;
			int maxNumber = 0;
			for(int i = 0; i < 12; i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
				Integer yue = i + 1;
				String month = yue.toString() +"月";
				map.put("month", month);
				map.put("num", num[i]);
				listSupport.add(map);
				if(maxNumber < num[i]){
					maxNumber = num[i];
					maxMonth = month;
				}
				else if((maxNumber == num[i]) && (maxNumber != 0)){
					maxMonth = maxMonth + "," + month;
				}
			}
			
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("data", listSupport);
			map1.put("maxNumber",maxNumber);
			map1.put("maxMonth",maxMonth);
			
			return map1;
		}	
}
