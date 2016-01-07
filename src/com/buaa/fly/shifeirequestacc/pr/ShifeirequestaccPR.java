package com.buaa.fly.shifeirequestacc.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Shifeirequestacc;
import com.buaa.fly.shifeirequestacc.manager.ShifeirequestaccManager;

@Component("shifeirequestaccPR")
public class ShifeirequestaccPR{

    @Resource
	private ShifeirequestaccManager shifeirequestaccManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryShifeirequestacc(Page<Shifeirequestacc> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    shifeirequestaccManager.queryShifeirequestacc(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveShifeirequestacc(Map<String, Collection> dataItems) throws Exception {
	    shifeirequestaccManager.saveShifeirequestacc(dataItems);
	 }
	
}
