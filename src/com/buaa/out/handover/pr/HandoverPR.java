package com.buaa.out.handover.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.out.domain.Handover;
import com.buaa.out.handover.manager.HandoverManager;

@Component("handoverPR")
public class HandoverPR{

    @Resource
	private HandoverManager handoverManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryHandover(Page<Handover> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    handoverManager.queryHandover(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveHandover(Map<String, Collection> dataItems) throws Exception {
	    handoverManager.saveHandover(dataItems);
	 }
	
}
