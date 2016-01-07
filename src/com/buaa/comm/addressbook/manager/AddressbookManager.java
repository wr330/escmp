
package com.buaa.comm.addressbook.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Addressbook;
import com.buaa.comm.addressbook.dao.AddressbookDao;

@Component("addressbookManager")
public class AddressbookManager {
	
	@Resource
	private AddressbookDao addressbookDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryAddressbook(Page<Addressbook> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    addressbookDao.queryAddressbook(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveAddressbook(Map<String, Collection> dataItems) throws Exception {
	    Collection<Addressbook> details =(Collection<Addressbook>) dataItems.get("dsAddressbook");
		this.saveAddressbook(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveAddressbook(Collection<Addressbook> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Addressbook item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					addressbookDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					addressbookDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										addressbookDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState addressbookdepartState = EntityUtils.getState(item.getAddressbookdepart());
		
	if(addressbookdepartState.equals(EntityState.MODIFIED)){
	addressbookDao.updateData(item);
	}
									}
			}
		}
	 }
	 
	 
	
}
