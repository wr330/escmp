package com.buaa.fly.sffault.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Sffault;
import com.buaa.fly.sffault.manager.SffaultManager;

@Component("sffaultPR")
public class SffaultPR{

    @Resource
	private SffaultManager sffaultManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void querySffault(Page<Sffault> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    sffaultManager.querySffault(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveSffault(Map<String, Collection> dataItems) throws Exception {
	    sffaultManager.saveSffault(dataItems);
	 }
	
}
