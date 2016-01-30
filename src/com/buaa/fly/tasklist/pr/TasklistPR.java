package com.buaa.fly.tasklist.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Subject;
import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.tasklist.manager.TasklistManager;

@Component("tasklistPR")
public class TasklistPR{

    @Resource
	private TasklistManager tasklistManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryTasklist(Page<Tasklist> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    tasklistManager.queryTasklist(page,parameter,criteria);
	}
	
	
	   /**                  
		* @throws Exception
		*/
		@DataProvider
		public Collection<Tasklist> queryTaskOutline(Map<String, Object> parameter) throws Exception {
			return tasklistManager.queryTaskOutline(parameter);   
		}
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveTasklist(Map<String, Collection> dataItems) throws Exception {
	    tasklistManager.saveTasklist(dataItems);
	 }
	
}
