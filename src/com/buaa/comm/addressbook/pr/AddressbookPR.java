package com.buaa.comm.addressbook.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Addressbook;
import com.buaa.comm.addressbook.manager.AddressbookManager;

@Component("addressbookPR")
public class AddressbookPR{

    @Resource
	private AddressbookManager addressbookManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryAddressbook(Page<Addressbook> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    addressbookManager.queryAddressbook(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveAddressbook(Map<String, Collection> dataItems) throws Exception {
	    addressbookManager.saveAddressbook(dataItems);
	 }
	
}
