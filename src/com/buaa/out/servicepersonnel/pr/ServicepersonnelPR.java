package com.buaa.out.servicepersonnel.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.out.domain.Servicepersonnel;
import com.buaa.out.servicepersonnel.manager.ServicepersonnelManager;

@Component("servicepersonnelPR")
public class ServicepersonnelPR{

    @Resource
	private ServicepersonnelManager servicepersonnelManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryServicepersonnel(Page<Servicepersonnel> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    servicepersonnelManager.queryServicepersonnel(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveServicepersonnel(Map<String, Collection> dataItems) throws Exception {
	    servicepersonnelManager.saveServicepersonnel(dataItems);
	 }
	
}
