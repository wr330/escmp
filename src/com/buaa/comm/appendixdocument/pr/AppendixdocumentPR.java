package com.buaa.comm.appendixdocument.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Appendixdocument;
import com.buaa.comm.appendixdocument.manager.AppendixdocumentManager;

@Component("appendixdocumentPR")
public class AppendixdocumentPR{

    @Resource
	private AppendixdocumentManager appendixdocumentManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryAppendixdocument(Page<Appendixdocument> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    appendixdocumentManager.queryAppendixdocument(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveAppendixdocument(Map<String, Collection> dataItems) throws Exception {
	    appendixdocumentManager.saveAppendixdocument(dataItems);
	 }
	
}
