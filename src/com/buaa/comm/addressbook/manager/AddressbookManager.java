
package com.buaa.comm.addressbook.manager;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Addressbook;
import com.buaa.comm.addressbook.dao.AddressbookDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("addressbookManager")
public class AddressbookManager {
	
	@Resource
	private AddressbookDao addressbookDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;
		
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
				String un = ContextHolder.getLoginUserName();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					addressbookDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, "对通讯录表新增一条记录");
				} 
				else if (state.equals(EntityState.MODIFIED)) {
					addressbookDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, "对通讯录表修改一条记录");
				} 
				else if (state.equals(EntityState.DELETED)) {
					addressbookDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, "对通讯录表删除一条记录");
				} 
				else if (state.equals(EntityState.NONE)) {
					EntityState addressbookdepartState = EntityUtils.getState(item.getAddressbookdepart());
		
	if(addressbookdepartState.equals(EntityState.MODIFIED)){
	addressbookDao.updateData(item);
	}
									}
			}
		}
	 }
	 
	 
	
}
