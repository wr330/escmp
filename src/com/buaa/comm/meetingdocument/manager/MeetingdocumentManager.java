
package com.buaa.comm.meetingdocument.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Meetingdocument;
import com.buaa.comm.meetingdocument.dao.MeetingdocumentDao;

@Component("meetingdocumentManager")
public class MeetingdocumentManager {
	
	@Resource
	private MeetingdocumentDao meetingdocumentDao;
		
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
				if (state.equals(EntityState.NEW)) {
					meetingdocumentDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					meetingdocumentDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										meetingdocumentDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
