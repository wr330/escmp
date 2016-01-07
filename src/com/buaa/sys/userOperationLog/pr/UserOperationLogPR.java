package com.buaa.sys.userOperationLog.pr;

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

import com.buaa.sys.domain.UserOperationLog;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("userOperationLogPR")
public class UserOperationLogPR {
	@Resource
	private UserOperationLogManager userOperationLogManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryUserOperationLog(Page<UserOperationLog> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		userOperationLogManager.queryUserOperationLog(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveUserOperationLog(Map<String, Collection> dataItems) throws Exception {
		 userOperationLogManager.saveUserOperationLog(dataItems);
	 }
}
