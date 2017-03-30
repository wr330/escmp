package com.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.exception.NoneLoginException;
import com.bstek.bdf2.core.view.frame.main.register.message.FetchMessageCount;
import com.bstek.dorado.annotation.Expose;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.TaskService;
import com.buaa.comm.btReportSharePerson.dao.BtReportSharePersonDao;
import com.buaa.comm.btreport.dao.BtreportDao;
import com.buaa.comm.joballot.dao.JoballotDao;
import com.buaa.comm.jobstatistics.dao.JobstatisticsDao;
import com.buaa.out.handover.dao.HandoverDao;

@Component("todoDao")
public class TodoDao extends JdbcBaseDao {
	
	@Autowired
	@Qualifier(TaskService.BEAN_ID)
	private TaskService taskService;
	@Resource
	private FetchMessageCount fetchMessageCount;
	@Resource
	private JobstatisticsDao jobstatisticsDao;
	@Resource
	private JoballotDao joballotDao;
	@Resource
	private BtreportDao btreportDao;
	@Resource
	private BtReportSharePersonDao btReportSharePersonDao;
	@Resource
	private HandoverDao handoverDao;
	
	//获取待办及消息提醒
	@Expose
	public List<Map<String, Object>> getTodo() throws Exception{
		IUser user = ContextHolder.getLoginUser();
		if (user == null) {
			throw new NoneLoginException("Please login first.");
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;		
		int messageCount = fetchMessageCount.count();//获取未读消息数量
		if(messageCount>0){
			map = new HashMap<String, Object>();
			map.put("title", "站内消息有：<font color=red>"+messageCount+"</font>条消息未读");
			map.put("url", "view.SeeMessage.d");
			list.add(map);
		}
		TaskQuery ownerQuery1 = taskService.createTaskQuery();//获取新闻发布工作流待办任务
		ownerQuery1.assignee(user.getUsername());				
		TaskQuery ownerQuery2 = taskService.createTaskQuery();//获取新闻发布工作流可领取待办任务
		ownerQuery2.addParticipator(user.getUsername());
		ownerQuery2.addTaskState(TaskState.Ready);
		int newsCount = ownerQuery1.count()+ownerQuery2.count();
		if(newsCount>0){
			map = new HashMap<String, Object>();
			map.put("title", "新闻管理有：<font color=red>"+newsCount+"</font>个任务待处理");
			map.put("url", "com.buaa.comm.view.TodoTaskMaintain.d");
			list.add(map);
		}
		Integer jobCount = jobstatisticsDao.queryJob(user.getUsername());
		if(jobCount > 0){
			map = new HashMap<String, Object>();
			map.put("title", "工作项目分配有：<font color=red>" + jobCount + "</font>个任务待处理");
			map.put("url", "com.buaa.comm.view.JobProjectDistribute.d");
			list.add(map);
		}
		Integer joballotCount = joballotDao.queryJoballotCount(user.getUsername());
		if(joballotCount > 0){
			map = new HashMap<String, Object>();
			map.put("title", "工作项目执行有：<font color=red>" + joballotCount + "</font>个任务待处理");
			map.put("url", "com.buaa.comm.view.JobProjectExecution.d");
			list.add(map);
		}
		Integer btreportSCount = btreportDao.queryBtreport(user.getUsername());
		if(btreportSCount > 0){
			map = new HashMap<String, Object>();
			map.put("title", "出差报告审阅有：<font color=red>" + btreportSCount + "</font>个报告待审阅");
			map.put("url", "com.buaa.comm.view.BusinessTripReportCheckSC.d");
			list.add(map);
		}
		Integer btreportDCount = btreportDao.queryBtreportDH(user.getUsername());
		if(btreportDCount > 0){
			map = new HashMap<String, Object>();
			map.put("title", "出差报告审核有：<font color=red>" + btreportDCount + "</font>个报告待审核");
			map.put("url", "com.buaa.comm.view.BusinessTripReportCheckDH.d");
			list.add(map);
		}
		Integer btreportShareCount = btReportSharePersonDao.queryBtreportShare(user.getUsername());
		if(btreportShareCount > 0){
			map = new HashMap<String, Object>();
			map.put("title", "出差报告共享有：<font color=red>" + btreportShareCount + "</font>个报告待阅读");
			map.put("url", "com.buaa.comm.view.BusinessTripReportShare.d");
			list.add(map);
		}
		Integer handoverMakeCount = handoverDao.queryHandoverMakeSure(user.getUsername());
		if(handoverMakeCount > 0){
			map = new HashMap<String, Object>();
			map.put("title", "外场现场交接确认有：<font color=red>" + handoverMakeCount + "</font>个记录待确认");
			map.put("url", "com.buaa.out.view.OutchiefSure.d");
			list.add(map);
		}
		return list;
	}
}
