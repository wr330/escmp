package com.buaa.fly.fpici.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fpici;
import com.buaa.fly.fpici.manager.FpiciManager;

@Component("fpiciPR")
public class FpiciPR{

    @Resource
	private FpiciManager fpiciManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryFpici(Page<Fpici> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    fpiciManager.queryFpici(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveFpici(Map<String, Collection> dataItems) throws Exception {
	    fpiciManager.saveFpici(dataItems);
	 }
	
}
