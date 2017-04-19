
package com.buaa.comm.meetingmanager.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Meetingmanager;
import com.buaa.comm.meetingmanager.dao.MeetingmanagerDao;

@Component("meetingmanagerManager")
public class MeetingmanagerManager {
	
	@Resource
	private MeetingmanagerDao meetingmanagerDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryMeetingmanager(Page<Meetingmanager> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    meetingmanagerDao.queryMeetingmanager(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveMeetingmanager(Map<String, Collection> dataItems) throws Exception {
	    Collection<Meetingmanager> details =(Collection<Meetingmanager>) dataItems.get("dsMeetingmanager");
		this.saveMeetingmanager(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveMeetingmanager(Collection<Meetingmanager> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Meetingmanager item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					meetingmanagerDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					meetingmanagerDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										meetingmanagerDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	@Expose
	public Boolean MeetingSTimeIsExists(Map<String, Object> parameter) {
		return meetingmanagerDao.MeetingSTimeIsExists(parameter);
	}   
	 
	
}
