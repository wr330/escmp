package com.buaa.out.supportprogram.manager;

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
import com.buaa.out.domain.Supportprogram;
import com.buaa.out.handover.manager.HandoverManager;
import com.buaa.out.supportitem.manager.SupportitemManager;
import com.buaa.out.supportprogram.dao.SupportprogramDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;


@Component("supportprogramManager")
public class SupportprogramManager {
	
	@Resource
	private SupportprogramDao supportprogramDao;
	@Resource
	private SupportitemManager supportitemManager;
	@Resource	
	private HandoverManager handoverManager;
	@Resource	
	private UserOperationLogManager userOperationLogManager;
	
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querySupportprogram(Page<Supportprogram> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportprogramDao.querySupportprogram(page,parameter,criteria);
	}
	
	/**                  
	* 分页查询信息($此方法太繁琐，已经停止使用了，改到SupportitemDao中进行统一查询$)
	* @throws Exception
	*/
	/*public Collection<Supportitem> queryRenyuan(Map<String, Object> parameter) throws Exception {
	    return supportprogramDao.queryRenyuan(parameter);
	}*/
	
	/**                  
	* 搜索计划开始时间到结束时间涉及本年度的保障计划
	* @param parameter    
	* @throws Exception
	*/
	public Collection<Supportprogram> queryProgram(Map<String, Object> parameter) throws Exception {
	    return supportprogramDao.queryProgram(parameter);
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSupportprogram(Map<String, Collection> dataItems) throws Exception {
	    Collection<Supportprogram> details =(Collection<Supportprogram>) dataItems.get("dsSupportprogram");
		this.saveSupportprogram(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveSupportprogram(Collection<Supportprogram> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Supportprogram item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					supportprogramDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对保障计划表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					supportprogramDao.updateData(item);
				 	//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对保障计划表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					supportprogramDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对保障计划表删除选定记录");
				} else if (state.equals(EntityState.NONE)) {
				}
				supportitemManager.saveSupportitem(item.getSupportitem());
				handoverManager.saveHandover(item.getHandover());
			}
		}
	 }
	 
	 
	
}
