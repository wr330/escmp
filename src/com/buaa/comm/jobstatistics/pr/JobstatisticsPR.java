package com.buaa.comm.jobstatistics.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Jobstatistics;
import com.buaa.comm.jobstatistics.manager.JobstatisticsManager;

@Component("jobstatisticsPR")
public class JobstatisticsPR{

    @Resource
	private JobstatisticsManager jobstatisticsManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryJobstatistics(Page<Jobstatistics> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    jobstatisticsManager.queryJobstatistics(page,parameter,criteria);
	}
	
	@DataProvider
	public Collection<Jobstatistics> queryConcernedJob(){
	    return jobstatisticsManager.queryConcernedJob();
	}
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveJobstatistics(Map<String, Collection> dataItems) throws Exception {
	    jobstatisticsManager.saveJobstatistics(dataItems);
	 }
	
}
