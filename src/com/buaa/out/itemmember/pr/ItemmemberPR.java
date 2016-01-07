package com.buaa.out.itemmember.pr;



import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.out.domain.Itemmember;
import com.buaa.out.itemmember.manager.ItemmemberManager;



@Component("itemmemberPR")
public class ItemmemberPR{

    @Resource
	private ItemmemberManager itemmemberManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryItemmember(Page<Itemmember> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    itemmemberManager.queryItemmember(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveItemmember(Map<String, Collection> dataItems) throws Exception {
	    itemmemberManager.saveItemmember(dataItems);
	 }
	
}
