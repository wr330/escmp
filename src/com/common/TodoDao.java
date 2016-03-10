package com.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.exception.NoneLoginException;
import com.bstek.dorado.annotation.Expose;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.TaskService;

@Component("todoDao")
public class TodoDao extends JdbcBaseDao {
	
	@Autowired
	@Qualifier(TaskService.BEAN_ID)
	private TaskService taskService;
	
	//获取待办及消息提醒
	@Expose
	public List<Map<String, Object>> getTodo(){
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
		return list;
	}
}
