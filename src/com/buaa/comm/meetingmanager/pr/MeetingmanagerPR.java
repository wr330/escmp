package com.buaa.comm.meetingmanager.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Meetingmanager;
import com.buaa.comm.meetingmanager.manager.MeetingmanagerManager;

@Component("meetingmanagerPR")
public class MeetingmanagerPR{

    @Resource
	private MeetingmanagerManager meetingmanagerManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryMeetingmanager(Page<Meetingmanager> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    meetingmanagerManager.queryMeetingmanager(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveMeetingmanager(Map<String, Collection> dataItems) throws Exception {
	    meetingmanagerManager.saveMeetingmanager(dataItems);
	 }
	
}
