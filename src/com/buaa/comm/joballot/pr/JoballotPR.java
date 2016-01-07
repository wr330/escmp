package com.buaa.comm.joballot.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Joballot;
import com.buaa.comm.joballot.manager.JoballotManager;

@Component("joballotPR")
public class JoballotPR{

    @Resource
	private JoballotManager joballotManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryJoballot(Page<Joballot> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    joballotManager.queryJoballot(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveJoballot(Map<String, Collection> dataItems) throws Exception {
	    joballotManager.saveJoballot(dataItems);
	 }
	
}
