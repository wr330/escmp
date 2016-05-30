package com.buaa.out.personstatistic.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.out.domain.Personstatistic;
import com.buaa.out.personstatistic.manager.PersonstatisticManager;

@Component("personstatisticPR")
public class PersonstatisticPR{

    @Resource
	private PersonstatisticManager personstatisticManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public Collection<Personstatistic> queryPersonstatistic(Map<String, Object> parameter) throws Exception {
	     return personstatisticManager.queryPersonstatistic(parameter);
	}
	
}
