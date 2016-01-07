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
	
	
}
