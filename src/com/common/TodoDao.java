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
import com.bstek.dorado.annotation.Expose;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.TaskService;
import com.buaa.comm.btReportSharePerson.dao.BtReportSharePersonDao;
import com.buaa.comm.btreport.dao.BtreportDao;
import com.buaa.comm.joballot.dao.JoballotDao;
import com.buaa.comm.jobstatistics.dao.JobstatisticsDao;

@Component("todoDao")
public class TodoDao extends JdbcBaseDao {
	
	@Autowired
	@Qualifier(TaskService.BEAN_ID)
	private TaskService taskService;
	@Resource
	private JobstatisticsDao jobstatisticsDao;
	@Resource
	private JoballotDao joballotDao;
	@Resource
	private BtreportDao btreportDao;
	@Resource
	private BtReportSharePersonDao btReportSharePersonDao;
	
	//获取待办及消息提醒
	@Expose
	public List<Map<String, Object>> getTodo() throws Exception{
		IUser user = ContextHolder.getLoginUser();
		if (user == null) {
			throw new NoneLoginException("Please login first.");
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;				
		TaskQuery ownerQuery = taskService.createTaskQuery();//获取新闻发布工作流待办任务
		ownerQuery.assignee(user.getUsername());
		if(ownerQuery.count()>0){
			map = new HashMap<String, Object>();
			map.put("title", "新闻管理有：<font color=red>"+ownerQuery.count()+"</font>个任务待处理");
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
			map.put("title", "出差报告审阅有：<font color=red>" + btreportDCount + "</font>个报告待审阅");
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
		return list;
	}
}
