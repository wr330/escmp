
package com.buaa.comm.jobconcern.manager;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Jobconcern;
import com.buaa.comm.domain.Jobstatistics;
import com.buaa.comm.jobconcern.dao.JobconcernDao;

@Component("jobconcernManager")
public class JobconcernManager {
	
	@Resource
	private JobconcernDao jobconcernDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryJobconcern(Page<Jobconcern> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    jobconcernDao.queryJobconcern(page,parameter,criteria);
	}
	//通过用户ID获取工作项目关注信息
	public Collection<Jobconcern> getJobConcernByUser(){
		return jobconcernDao.getJobConcernByUser();
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveJobconcern(Map<String, Collection> dataItems) throws Exception {
	    Collection<Jobconcern> details =(Collection<Jobconcern>) dataItems.get("dsJobconcern");
		this.saveJobconcern(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveJobconcern(Collection<Jobconcern> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Jobconcern item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					jobconcernDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					jobconcernDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					jobconcernDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState jobstatisticsState = EntityUtils.getState(item.getJobstatistics());
		
	if(jobstatisticsState.equals(EntityState.MODIFIED)){
	jobconcernDao.updateData(item);
	}
				}
							}
		}
	 }
	 
	 public void setJobConcern(Collection<Jobstatistics> details) throws Exception{
		 String username = ContextHolder.getLoginUserName();
		 jobconcernDao.deleteDataByUser(username);
		 for(Jobstatistics job : details){
			Jobconcern item = new Jobconcern();
			item.setJobstatistics(job);
			 item.setPersonid(username);
			 item.setConcerntime(new Date());
			 jobconcernDao.saveData(item);
		 }
	 } 
	
}
