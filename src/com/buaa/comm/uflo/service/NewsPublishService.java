package com.buaa.comm.uflo.service;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.newstree.domain.NewsTree;
import com.bstek.newstree.service.NewsService;
import com.bstek.uflo.client.service.ProcessClient;
import com.bstek.uflo.client.service.TaskClient;
import com.bstek.uflo.model.HistoryTask;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.query.HistoryTaskQuery;
import com.bstek.uflo.service.HistoryService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskOpinion;


@Component("newsPublishService")
public class NewsPublishService  extends HibernateDao {
	
	@Autowired
	@Qualifier(ProcessClient.BEAN_ID)
	private ProcessClient processClient;
	public static String manager;
	private NewsService newsService;
	/**
	 * 自动注入NewsService接口的实现为bean id为defaultNewsServiceImp的实现
	 * @param newsService 由spring容器负责实例化该对象
	 */
	@Resource(name = "defaultNewsServiceImp")
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
	@Expose
	@DataResolver
	//启动发布申请流程
	public void applyPublish(String newsId,String userName) throws Exception{
		NewsTree currentNews = newsService.getCurrentNewsById(newsId);
		if((currentNews.getStatu()).equals("save")){
			manager = userName;
			StartProcessInfo info = new StartProcessInfo();
			info.setPromoter(ContextHolder.getLoginUserName());
			info.setBusinessId(newsId);
			info.setCompleteStartTask(true);
			processClient.startProcessById(835001, info);
			//int taskId = newsService.getTaskIdBynewsId(newsId);
			//taskClient.changeTaskAssignee(Long.valueOf(taskId), userName);
			newsService.updateTask(newsId, currentNews.getNodeTitle());
			currentNews.setStatu("managerApproving");
			newsService.modifyNews(currentNews);
		}
	}
	//选择审核人
	@DataProvider
	public Collection<DefaultUser> loadUsers(){
		Session session = this.getSessionFactory().openSession();
		try{
			ApproverService approverService = new ApproverService();
			Collection<DefaultUser> manager = approverService.getManager(ContextHolder.getLoginUserName(),session);
			return manager;
		}finally{
			session.close();
		}
	}
	@Autowired
	@Qualifier(TaskClient.BEAN_ID)
	private TaskClient taskClient;
	@Expose
	@DataResolver
	//流程审批处理
	public Boolean approvePublish(String taskId,String opinion,String newsId,String result,String title,String content) throws Exception{
		if(result.equals("通过")){
			Session session = this.getSessionFactory().openSession();
			try{
				Task task = (Task)session.get(Task.class, Long.parseLong(taskId));
				if(task.getTaskName().equals("审核")){
					ApproverService approverService = new ApproverService();
					Collection<String> minister = approverService.getMinister(session);
					if( minister== null || minister.isEmpty())
						return false;
				}
			}finally{
				session.close();
			}
		}
		TaskOpinion taskOpinion = new TaskOpinion(opinion);
		taskClient.start(Long.valueOf(taskId));
		taskClient.complete(Long.valueOf(taskId),result,taskOpinion);
		NewsTree currentNews = newsService.getCurrentNewsById(newsId);
		newsService.updateTask(newsId, title);
		currentNews.setNodeTitle(title);
		currentNews.setNodeContent(content);
		if(result.equals("驳回"))
			currentNews.setStatu("reject");
		else{
			if(currentNews.getStatu().equals("managerApproving"))
				currentNews.setStatu("ministerApproving");
			else if(currentNews.getStatu().equals("ministerApproving")){
				currentNews.setStatu("publish");
				currentNews.setOrderDate(new Date());
			}
		}
		newsService.modifyNews(currentNews);
		return true;
	}
	//流程审批加载数据
	@DataProvider
	public NewsTree loadNews(String businessId) throws Exception{
		if(StringUtils.isEmpty(businessId)){
			return null;
		}
		else{
			NewsTree currentNews = newsService.getCurrentNewsById(businessId);
		    return currentNews;
		}

	}
	@Expose
	@DataResolver
	//驳回后重新提交审批
	public void applyPublish(String taskId,String newsId,String title,String content,String userName) throws Exception{
		manager = userName;
		taskClient.start(Long.valueOf(taskId));
		//taskClient.saveTaskAppointor(Long.valueOf(taskId), userName, "主任审批");
		taskClient.complete(Long.valueOf(taskId));
		NewsTree currentNews = newsService.getCurrentNewsById(newsId);
		newsService.updateTask(newsId, title);
		currentNews.setNodeTitle(title);
		currentNews.setNodeContent(content);
		currentNews.setStatu("managerApproving");
		currentNews.setUpdateDate(new Date());
		newsService.modifyNews(currentNews);
	}
	@Autowired
	@Qualifier(HistoryService.BEAN_ID)
	private HistoryService historyService;
	//查看审批历史
	@DataProvider
	public Collection<HistoryTask> loadHistoryTask(String taskId){
		Task task = taskClient.getTask(Long.valueOf(taskId));
		HistoryTaskQuery query = historyService.createHistoryTaskQuery();
		query.rootProcessInstanceId(task.getRootProcessInstanceId());
		query.addOrderDesc("endDate");
		return query.list();
	}
	//查看流程图时获取任务ID
	@Expose
	@DataProvider
	public int getTaskId(String businessId) throws Exception{
		return newsService.getTaskIdBynewsId(businessId);
	}
}
