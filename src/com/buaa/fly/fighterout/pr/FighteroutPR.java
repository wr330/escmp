package com.buaa.fly.fighterout.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fighterout;
import com.buaa.fly.fighterout.manager.FighteroutManager;

@Component("fighteroutPR")
public class FighteroutPR{

    @Resource
	private FighteroutManager fighteroutManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryFighterout(Page<Fighterout> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    fighteroutManager.queryFighterout(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveFighterout(Map<String, Collection> dataItems) throws Exception {
	    fighteroutManager.saveFighterout(dataItems);
	 }
	
}
