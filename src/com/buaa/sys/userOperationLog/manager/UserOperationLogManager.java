package com.buaa.sys.userOperationLog.manager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.sys.domain.UserOperationLog;
import com.buaa.sys.userOperationLog.dao.UserOperationLogDao;


@Component("userOperationLogManager")
public class UserOperationLogManager {
	@Resource
	private UserOperationLogDao userOperationLogDao;
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryUserOperationLog(Page<UserOperationLog> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		userOperationLogDao.queryUserOperationLog(page,parameter,criteria);
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveUserOperationLog(Map<String, Collection> dataItems) throws Exception {
	    Collection<UserOperationLog> details =(Collection<UserOperationLog>) dataItems.get("dsUserOperationLog");
		this.saveUserOperationLog(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveUserOperationLog(Collection<UserOperationLog> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(UserOperationLog item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					userOperationLogDao.saveData(item);
					} else if (state.equals(EntityState.MODIFIED)) {
					userOperationLogDao.updateData(item);
					} else if (state.equals(EntityState.DELETED)) {
					userOperationLogDao.deleteData(item);
					} else if (state.equals(EntityState.NONE)) {			
				}
			}
		}
	 }
	 public void recordUserOperationLog(int operationType,Date operationTime,String operationPerson,String operationPersonName,String operationContent) throws Exception{
		 	UserOperationLog userOperationLog = new UserOperationLog();
		 	userOperationLog.setLogOperationType(operationType);
			userOperationLog.setOperationTime(operationTime);
			userOperationLog.setOperationPerson(operationPerson);
			userOperationLog.setOperationPersonName(operationPersonName);
			userOperationLog.setOperationContent(operationContent);
			userOperationLogDao.saveData(userOperationLog);  
	 }
}
