package com.buaa.out.supportevaluation.pr;



import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.out.domain.Supportevaluation;
import com.buaa.out.supportevaluation.manager.SupportevaluationManager;



@Component("supportevaluationPR")
public class SupportevaluationPR{

    @Resource
	private SupportevaluationManager supportevaluationManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void querySupportevaluation(Page<Supportevaluation> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportevaluationManager.querySupportevaluation(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveSupportevaluation(Map<String, Collection> dataItems) throws Exception {
	    supportevaluationManager.saveSupportevaluation(dataItems);
	 }
	
}
