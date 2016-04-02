
package com.buaa.comm.appendixdocument.manager;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Appendixdocument;
import com.buaa.comm.appendixdocument.dao.AppendixdocumentDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("appendixdocumentManager")
public class AppendixdocumentManager {
	
	@Resource
	private AppendixdocumentDao appendixdocumentDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;	
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryAppendixdocument(Page<Appendixdocument> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    appendixdocumentDao.queryAppendixdocument(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveAppendixdocument(Map<String, Collection> dataItems) throws Exception {
	    Collection<Appendixdocument> details =(Collection<Appendixdocument>) dataItems.get("dsAppendixdocument");
		this.saveAppendixdocument(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveAppendixdocument(Collection<Appendixdocument> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Appendixdocument item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					appendixdocumentDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对通讯录表新增一条记录");
									} else if (state.equals(EntityState.MODIFIED)) {
					appendixdocumentDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对通讯录表新增一条记录");
									} else if (state.equals(EntityState.DELETED)) {
					appendixdocumentDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中删除一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对保障计划表新增一条记录");
				} else if (state.equals(EntityState.NONE)) {
						EntityState btreportState = EntityUtils.getState(item.getBtreport());
		
	if(btreportState.equals(EntityState.MODIFIED)){
	appendixdocumentDao.updateData(item);
	}
									}
			}
		}
	 }
	 
	//删除从表
	  public void deleteItems(String primaryOid) throws Exception{
		  appendixdocumentDao.deleteItems(primaryOid);
	  } 
	
}
