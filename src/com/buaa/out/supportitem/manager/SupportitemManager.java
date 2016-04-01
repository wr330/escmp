package com.buaa.out.supportitem.manager;

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
import com.buaa.out.domain.Supportitem;
import com.buaa.out.supportitem.dao.SupportitemDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("supportitemManager")
public class SupportitemManager {
	
	@Resource
	private SupportitemDao supportitemDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;
	
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param parameter
	* @throws Exception
	*/
	public void querySupportitem(Page<Supportitem> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportitemDao.querySupportitem(page,parameter,criteria);
	}
	
	/**                  
	* 根据参数查询保障条目信息    
	* @param map
	* @throws Exception
	*/
	public Collection<Supportitem> queryItem(Map<String, Object> parameter) throws Exception {
		return supportitemDao.queryItem(parameter);
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSupportitem(Map<String, Collection> dataItems) throws Exception {
	    Collection<Supportitem> details =(Collection<Supportitem>) dataItems.get("dsSupportitem");
		this.saveSupportitem(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveSupportitem(Collection<Supportitem> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Supportitem item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					supportitemDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对保障计划执行条目表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					supportitemDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对保障计划执行条目表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					supportitemDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对保障计划执行条目表删除选定记录");
				} else if (state.equals(EntityState.NONE)) {
						EntityState supportprogramState = EntityUtils.getState(item.getSupportprogram());		
						if(supportprogramState.equals(EntityState.MODIFIED)){
							supportitemDao.updateData(item);
						}			
				}
			}
		}
	 }
	
}
