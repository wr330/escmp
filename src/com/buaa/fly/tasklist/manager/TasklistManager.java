package com.buaa.fly.tasklist.manager;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Subject;
import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.sfstatistic.dao.SfstatisticDao;
import com.buaa.fly.sfstatistic.manager.SfstatisticManager;
import com.buaa.fly.tasklist.dao.TasklistDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;
import com.common.FileHelper;

@Component("tasklistManager")
public class TasklistManager {

	@Resource
	private TasklistDao tasklistDao;
	@Resource
	private SfstatisticDao sfstatisticDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;

	/**
	 * 分页查询信息，带有criteria 将criteria转换为一个Map
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryTasklist(Page<Tasklist> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		tasklistDao.queryTasklist(page, parameter, criteria);
	}

	/**
	 * 任务管理单查询
	 * 
	 * @param parameter
	 * @throws Exception
	 */ 
	public Collection<Tasklist> queryTaskOutline(String ftype,String subject)
			throws Exception {
		return tasklistDao.queryTaskOutline(ftype,subject);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveTasklist(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Tasklist> details = (Collection<Tasklist>) dataItems
				.get("dsTasklist");
		this.saveTasklist(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveTasklist(Collection<Tasklist> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Tasklist item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					//fileManager(item);把附件以二进制的形式保存到数据库中，暂时不用
					tasklistDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对试飞任务单新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					//fileManager(item);把附件以二进制的形式保存到数据库中，暂时不用
					tasklistDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对试飞任务单修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					if (sfstatisticDao.statisticIsExists(item.getTasknumber())
							.equals("该任务管理单关联飞行统计数据，不能删除！"))
						throw new Exception("该任务管理单关联飞行统计数据，不能删除！");
					tasklistDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对试飞任务单删除选定记录");
					FileHelper.deleteFile("/Fly_Tasklist/" + item.getOid());// 删除相关文件
				} else if (state.equals(EntityState.NONE)) {
					EntityState subjectState = EntityUtils.getState(item
							.getSubject());
					if (subjectState.equals(EntityState.MODIFIED)) {
						tasklistDao.updateData(item);
					}
				}
				
			}
		}
	}
	
	/**
	 * 这个方法用来判断在添加时任务单是否已经存在
	 * 
	 * @param tasknumber 用户输入的任务单号
	 *            
	 */
	@Expose
	public String tasklistIsExists(String oid,String tasknumber) {
		return tasklistDao.tasklistIsExists(oid,tasknumber);
	}

	// 处理相关文件
	private void fileManager(Tasklist item) {
		String path1 = "/Fly_Tasklist/" + item.getOid() + "/"
				+ item.getFilename();
		FileHelper.fileToData(path1);
		if (FileHelper.bytes != 0) {
			item.setBytes(FileHelper.bytes);
			item.setDatablock(FileHelper.datablock);// 文件存储到数据库中
			FileHelper.bytes = 0;
			FileHelper.datablock = null;
		}
	}

	/**
	 根据前台用户选择的飞行统计记录、所需关联任务单、原来关联的任务单，把所需关联的任务单关联到飞行统计记录
	 @param parameter Map<String, Object>类型,表示飞行统计记录、所需关联任务单、原来关联的任务单
	 @throws Exception 
	 *
	 */
	@Expose
	@SuppressWarnings("unchecked")
	public void saveTaskSfStatistic(Map<String, Object> parameter) throws Exception {		
		if (null != parameter && !parameter.isEmpty()) {
			Collection<Tasklist> pastTasklist = (Collection<Tasklist>) parameter.get("pastTasklist");
			//清空原来关联的任务单中飞行统计记录
			for(Tasklist pastTask : pastTasklist){
				pastTask.setSfstatistic(null);
				tasklistDao.updateData(pastTask);
			}
			
			Collection<Tasklist> tasklist = (Collection<Tasklist>) parameter.get("tasklist");
			Sfstatistic sfstatistic = (Sfstatistic) parameter.get("sfstatistic");
			//所选飞行统计记录关联任务单
			for(Tasklist task : tasklist){
				task.setSfstatistic(sfstatistic);
				tasklistDao.updateData(task);
			}
		}				
	}
	
}
