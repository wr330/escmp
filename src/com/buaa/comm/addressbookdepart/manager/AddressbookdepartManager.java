
package com.buaa.comm.addressbookdepart.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Addressbookdepart;
import com.buaa.comm.addressbookdepart.dao.AddressbookdepartDao;
import com.buaa.comm.addressbook.manager.AddressbookManager;
import com.buaa.fly.domain.Subject;

@Component("addressbookdepartManager")
public class AddressbookdepartManager {
	
	@Resource
	private AddressbookdepartDao addressbookdepartDao;
		@Resource
	private AddressbookManager addressbookManager;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public Collection<Addressbookdepart> queryAddressbookdepart(String parentnode) throws Exception {
	    return addressbookdepartDao.queryAddressbookdepart(parentnode);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveAddressbookdepart(Map<String, Collection> dataItems) throws Exception {
	    Collection<Addressbookdepart> details =(Collection<Addressbookdepart>) dataItems.get("dsAddressbookdepart");
		this.saveAddressbookdepart(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveAddressbookdepart(Collection<Addressbookdepart> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Addressbookdepart item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					addressbookdepartDao.saveData(item);
				} 
				if (state.equals(EntityState.MODIFIED)|| state.equals(EntityState.MOVED)) {
					addressbookdepartDao.updateData(item);
				} 
				if(item.getChildren()!=null){
					saveAddressbookdepart(item.getChildren());
				}
				if (state.equals(EntityState.DELETED)) {
					addressbookdepartDao.deleteData(item);
				}
			}
		}
	 }
	 
	 public int countChildren(String parentnode) {
			return addressbookdepartDao.countChildren(parentnode);
		}
	
}
