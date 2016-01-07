package com.buaa.sys.auditLog.manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.sys.domain.AuditLog;
import com.buaa.sys.domain.UserOperationLog;
import com.buaa.sys.userOperationLog.dao.UserOperationLogDao;
import com.buaa.sys.auditLog.dao.AuditLogDao;


@Component("auditLogManager")
public class AuditLogManager {

	@Resource
	private AuditLogDao auditLogDao;
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryAuditLog(Page<AuditLog> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		auditLogDao.queryAuditLog(page,parameter,criteria);
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveAuditLog(Map<String, Collection> dataItems) throws Exception {
	    Collection<AuditLog> details =(Collection<AuditLog>) dataItems.get("dsAuditLog");
		this.saveAuditLog(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveAuditLog(Collection<AuditLog> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(AuditLog item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					auditLogDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					auditLogDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
					auditLogDao.deleteData(item);
									} else if (state.equals(EntityState.NONE)) {
										
				}

			}
		}
	 }
	
}
