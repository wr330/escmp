package com.buaa.sys.dictionary.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.sys.domain.Dictionary;
import com.buaa.sys.dictionary.manager.DictionaryManager;

@Component("dictionaryPR")
public class DictionaryPR{

    @Resource
	private DictionaryManager dictionaryManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public Collection<Dictionary> queryDictionary(Map<String, Object> parameter,Criteria criteria) throws Exception {
	    return dictionaryManager.queryDictionary(parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveDictionary(Map<String, Collection> dataItems) throws Exception {
	    dictionaryManager.saveDictionary(dataItems);
	 }
	
}
