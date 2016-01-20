package com.buaa.out.supportevaluation.manager;



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
import com.buaa.out.domain.Supportevaluation;
import com.buaa.out.supportevaluation.dao.SupportevaluationDao;
import com.buaa.sys.domain.UserOperationLog;
import com.buaa.sys.userOperationLog.dao.UserOperationLogDao;



@Component("supportevaluationManager")
public class SupportevaluationManager {
	
	@Resource
	private SupportevaluationDao supportevaluationDao;
	@Resource	
	private UserOperationLogDao userOperationLogDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querySupportevaluation(Page<Supportevaluation> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportevaluationDao.querySupportevaluation(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSupportevaluation(Map<String, Collection> dataItems) throws Exception {
	    Collection<Supportevaluation> details =(Collection<Supportevaluation>) dataItems.get("dsSupportevaluation");
		this.saveSupportevaluation(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveSupportevaluation(Collection<Supportevaluation> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Supportevaluation item : details) {
				EntityState state = EntityUtils.getState(item);
				UserOperationLog userOperationLog = new UserOperationLog();
				String un = ContextHolder.getLoginUserName();
				if (state.equals(EntityState.NEW)) {
					supportevaluationDao.saveData(item);
					
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					Date myDate = new Date();
					userOperationLog.setLogOperationType(0);
					userOperationLog.setOperationTime(myDate);
					userOperationLog.setOperationPerson(un);
					userOperationLog.setOperationContent("对保障评估表新增一条记录");
					userOperationLogDao.saveData(userOperationLog);
				} else if (state.equals(EntityState.MODIFIED)) {
					supportevaluationDao.updateData(item);
					
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					Date myDate = new Date();
					userOperationLog.setLogOperationType(1);
					userOperationLog.setOperationTime(myDate);
					userOperationLog.setOperationPerson(un);
					userOperationLog.setOperationContent("对保障计划表修改选定记录");
					userOperationLogDao.saveData(userOperationLog);
					
				} else if (state.equals(EntityState.DELETED)) {
					supportevaluationDao.deleteData(item);
					
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					Date myDate = new Date();
					userOperationLog.setLogOperationType(2);
					userOperationLog.setOperationTime(myDate);
					userOperationLog.setOperationPerson(un);
					userOperationLog.setOperationContent("对保障评估表删除选定记录");
					userOperationLogDao.saveData(userOperationLog);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
