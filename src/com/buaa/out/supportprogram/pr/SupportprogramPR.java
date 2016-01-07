package com.buaa.out.supportprogram.pr;



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
		* 分页查询信息，带有criteria
		* @param page    
		* @param map
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
		
}
