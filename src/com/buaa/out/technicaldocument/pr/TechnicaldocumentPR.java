package com.buaa.out.technicaldocument.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.out.domain.Technicaldocument;
import com.buaa.out.technicaldocument.manager.TechnicaldocumentManager;

@Component("technicaldocumentPR")
public class TechnicaldocumentPR{

    @Resource
	private TechnicaldocumentManager technicaldocumentManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryTechnicaldocument(Page<Technicaldocument> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    technicaldocumentManager.queryTechnicaldocument(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveTechnicaldocument(Map<String, Collection> dataItems) throws Exception {
	    technicaldocumentManager.saveTechnicaldocument(dataItems);
	 }
	
}
