package com.buaa.fly.flightrestrict.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Flightrestrict;
import com.buaa.fly.flightrestrict.manager.FlightrestrictManager;

@Component("flightrestrictPR")
public class FlightrestrictPR{

    @Resource
	private FlightrestrictManager flightrestrictManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryFlightrestrict(Page<Flightrestrict> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    flightrestrictManager.queryFlightrestrict(page,parameter,criteria);
	}
	@DataProvider
	public Collection<Flightrestrict> queryshfxzh(Map<String, Object> parameter) throws Exception {
	    return flightrestrictManager.queryshfxzh(parameter);
	}
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveFlightrestrict(Map<String, Collection> dataItems) throws Exception {
	    flightrestrictManager.saveFlightrestrict(dataItems);
	 }
	
}
