package com.buaa.fly.fdata.pr;


import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.fly.domain.Fdata;
import com.buaa.fly.fdata.manager.DataManager;




@Component("dataPR")
public class DataPR{

    @Resource
	private DataManager dataManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryData(Page<Fdata> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    dataManager.queryData(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveData(Map<String, Collection> dataItems) throws Exception {
	    dataManager.saveData(dataItems);
	 }
	
}
