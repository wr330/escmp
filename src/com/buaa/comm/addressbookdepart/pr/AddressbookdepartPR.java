package com.buaa.comm.addressbookdepart.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Addressbookdepart;
import com.buaa.comm.addressbookdepart.manager.AddressbookdepartManager;
import com.buaa.fly.domain.Subject;

@Component("addressbookdepartPR")
public class AddressbookdepartPR{

    @Resource
	private AddressbookdepartManager addressbookdepartManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public Collection<Addressbookdepart> queryAddressbookdepart(String parentnode) throws Exception {
	    return addressbookdepartManager.queryAddressbookdepart(parentnode);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveAddressbookdepart(Map<String, Collection> dataItems) throws Exception {
	    addressbookdepartManager.saveAddressbookdepart(dataItems);
	 }
	 @Expose
		public int countChildren(String parentnode) {
			return addressbookdepartManager.countChildren(parentnode);
		}
	
}
