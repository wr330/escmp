
package com.buaa.fly.tasklist.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.tasklist.dao.TasklistDao;

@Component("tasklistManager")
public class TasklistManager {
	
	@Resource
	private TasklistDao tasklistDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryTasklist(Page<Tasklist> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    tasklistDao.queryTasklist(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveTasklist(Map<String, Collection> dataItems) throws Exception {
	    Collection<Tasklist> details =(Collection<Tasklist>) dataItems.get("dsTasklist");
		this.saveTasklist(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveTasklist(Collection<Tasklist> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Tasklist item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					//System.out.println(tasklistIsExists(item.getTasknumber()));
					if(tasklistIsExists(item.getTasknumber()).equals("此单号已存在！"))
					throw new Exception("此单号已存在！");
					
					tasklistDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					tasklistDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										tasklistDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState subjectState = EntityUtils.getState(item.getSubject());
		
	if(subjectState.equals(EntityState.MODIFIED)){
	tasklistDao.updateData(item);
	}
									}
			}
		}
	 }
	 
		/**
		 * 这个方法用来判断在添加新软件时型号是否已经存在
		 * 
		 * @param tasknumber
		 *            用户输入的任务单号
		 */
		@Expose
		public String tasklistIsExists(String tasknumber) {
			return tasklistDao.tasklistIsExists(tasknumber);
		}
	
}
