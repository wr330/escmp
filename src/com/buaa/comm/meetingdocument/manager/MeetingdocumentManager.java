
package com.buaa.comm.meetingdocument.manager;

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

import com.buaa.comm.domain.Meetingdocument;
import com.buaa.comm.meetingdocument.dao.MeetingdocumentDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("meetingdocumentManager")
public class MeetingdocumentManager {
	
	@Resource
	private MeetingdocumentDao meetingdocumentDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;	
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryMeetingdocument(Page<Meetingdocument> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    meetingdocumentDao.queryMeetingdocument(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveMeetingdocument(Map<String, Collection> dataItems) throws Exception {
	    Collection<Meetingdocument> details =(Collection<Meetingdocument>) dataItems.get("dsMeetingdocument");
		this.saveMeetingdocument(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveMeetingdocument(Collection<Meetingdocument> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Meetingdocument item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					meetingdocumentDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对会议材料表新增一条记录");
									} else if (state.equals(EntityState.MODIFIED)) {
					meetingdocumentDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对会议材料表修改一条记录");
									} else if (state.equals(EntityState.DELETED)) {
					meetingdocumentDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对会议材料表删除一条记录");
				} else if (state.equals(EntityState.NONE)) {
														}
	
			}
		}
	 }
	 
	 
	
}
