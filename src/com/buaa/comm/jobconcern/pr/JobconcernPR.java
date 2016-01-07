package com.buaa.comm.jobconcern.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Jobconcern;
import com.buaa.comm.domain.Jobstatistics;
import com.buaa.comm.jobconcern.manager.JobconcernManager;

@Component("jobconcernPR")
public class JobconcernPR{

    @Resource
	private JobconcernManager jobconcernManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryJobconcern(Page<Jobconcern> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    jobconcernManager.queryJobconcern(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveJobconcern(Map<String, Collection> dataItems) throws Exception {
	    jobconcernManager.saveJobconcern(dataItems);
	 }
	//保存工作项目选择性关注信息
	 @Expose
	 public void setJobConcern(Object details) throws Exception{
		 jobconcernManager.setJobConcern((Collection<Jobstatistics>)details);
	 }
}
