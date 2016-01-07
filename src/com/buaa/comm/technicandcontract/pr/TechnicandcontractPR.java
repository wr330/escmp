package com.buaa.comm.technicandcontract.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Technicandcontract;
import com.buaa.comm.technicandcontract.manager.TechnicandcontractManager;

@Component("technicandcontractPR")
public class TechnicandcontractPR{

    @Resource
	private TechnicandcontractManager technicandcontractManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryTechnicandcontract(Page<Technicandcontract> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    technicandcontractManager.queryTechnicandcontract(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveTechnicandcontract(Map<String, Collection> dataItems) throws Exception {
	    technicandcontractManager.saveTechnicandcontract(dataItems);
	 }
	
}
