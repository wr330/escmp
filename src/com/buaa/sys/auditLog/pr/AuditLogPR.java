package com.buaa.sys.auditLog.pr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.sys.domain.AuditLog;
import com.buaa.sys.auditLog.manager.AuditLogManager;

@Component("auditLogPR")
public class AuditLogPR {

	@Resource
	private AuditLogManager auditLogManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryAuditLog(Page<AuditLog> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		auditLogManager.queryAuditLog(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveAuditLog(Map<String, Collection> dataItems) throws Exception {
		 auditLogManager.saveAuditLog(dataItems);
	 }
	
}
