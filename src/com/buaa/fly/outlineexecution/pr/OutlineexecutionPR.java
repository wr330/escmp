package com.buaa.fly.outlineexecution.pr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Subject;
//import com.buaa.fly.domain.vo.OutlineStatistic;
import com.buaa.fly.outlineexecution.manager.OutlineexecutionManager;
import com.buaa.fly.sfstatistic.dao.SfstatisticDao;
import com.buaa.fly.subject.manager.SubjectManager;

@Component("outlineexecutionPR")
public class OutlineexecutionPR{

    @Resource
	private OutlineexecutionManager outlineexecutionManager;
    @Resource
    private SubjectManager subjectManager;
    @Resource
    private SfstatisticDao sfstatisticDao;

     
    /**                  
   	* @throws Exception
   	*/
   	@DataProvider
   	public Collection<Outlineexecution> queryOutlineexecution(Map<String, Object> parameter) throws Exception {
   		return outlineexecutionManager.queryOutlineexecution(parameter);   
   	}

    /**                  
   	* @throws Exception
   	*/
   	@DataProvider
   	public Collection<Outlineexecution> query(Map<String, Object> parameter) throws Exception {
   		return outlineexecutionManager.query(parameter);   
   	}
   	
    /**                  
   	* @throws Exception
   	*/
   	@DataProvider
   	public Collection<Outlineexecution> queryOutlineexecutionforJDBC(Map<String, Object> parameter) throws Exception {
   		return outlineexecutionManager.queryOutlineexecutionforJDBC(parameter);   
   	}
    /**                  
 	* 分页查询信息，带有criteria
 	* @param page    
 	* @param map
 	* @throws Exception
 	*/
 	@DataProvider
 	public void queryOutline(Page<Outlineexecution> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
 		outlineexecutionManager.queryOutline(page,parameter,criteria);
 	}	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveOutlineexecution(Map<String, Collection> dataItems) throws Exception {
	    outlineexecutionManager.saveOutlineexecution(dataItems);
	 }
	 /**
		 * 数据保存，包括增删改
		 * @param dataItems
		 * @throws Exception
		 */
		 @SuppressWarnings("rawtypes")
		 @DataResolver
		 public void saveOutline(Map<String, Collection> dataItems) throws Exception {
		    outlineexecutionManager.saveOutline(dataItems);
		 }
		 /**
			 * 数据保存，包括增删改
			 * @param dataItems
			 * @throws Exception
			 */
		 @Expose
			 public void statisticOutlineexecution(Map<String, Object> parameter) throws Exception {
			 Collection<Outlineexecution> dataItems = outlineexecutionManager.queryOutlineexecution(parameter);
			    outlineexecutionManager.statisticOutlineexecution(dataItems);
			 }		 
	 @Expose
		public int countChildren(Map<String, Object> parameter) {
			return outlineexecutionManager.countChildren(parameter);
		}
	/**                  
		* 
		* 
		*/
	@DataProvider
	public Collection<Map<String, Object>> getNumbers(
			Map<String, Object> parameter) throws Exception {
		String label1 = new String("未完成");
		String label2 = new String("已完成");
		Map<String, Object> map1 = null;
		Map<String, Object> map2 = null;
		if (null != parameter && !parameter.isEmpty()) {
			Integer count = (Integer) parameter.get("count");
			Integer ucount = (Integer) parameter.get("ucount");
			map1 = new HashMap<String, Object>();
			map2 = new HashMap<String, Object>();
			map1.put("price", ucount);
			map1.put("name", label1);
			map2.put("price", count - ucount);
			map2.put("name", label2);
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		list.add(map1);
		list.add(map2);

		return list;
	}
	@DataProvider
	public Collection<Map<String, Object>> statisticOutline(Map<String, Object> parameter) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String parentnode = (String) parameter.get("parentnode");
		Collection<Subject> dataItems;
		List<Integer> num = new ArrayList<Integer>();
		if(parentnode != null){
			dataItems = subjectManager.querySubject(parameter);
			num = sfstatisticDao.querySubjectJiaci(parameter);
		}else{
		Collection<Subject> dataItems0 = subjectManager.querySubject(parameter);
		List<Subject> items = (ArrayList<Subject>) dataItems0;
		Subject item0 = items.get(0);
		HashMap<String, Object> map0 = new HashMap<String, Object>();
		map0.put("parentnode", item0.getOid());
		map0.put("ftype", item0.getFtype());
		dataItems = subjectManager.querySubject(map0);
		num = sfstatisticDao.querySubjectJiaci(map0);
		}
		//Collection<Outlineexecution> dataItems = outlineexecutionManager.queryOutlineexecution(parameter);
		for (Subject item : dataItems) {
			int i = 0;
			HashMap<String, Object> map = new HashMap<String, Object>();
			Float statistic = (float) 0;
			if(item.getOutlineexecution().size() > 0 && item.getOutlineexecution() != null)
			if(item.getOutlineexecution().get(0).getShijijiaci() != null){
				statistic = (float) (item.getOutlineexecution().get(0).getShijijiaci())/(float)(item.getOutlineexecution().get(0).getOutlineFlights());
			}else{
				statistic = (float) (num.get(i))/(float)(item.getOutlineexecution().get(0).getOutlineFlights());
			}
			map.put("price", statistic*100);
			map.put("name", item.getName());
			list.add(map);
			i++;
		}
		return list;
	}
}
