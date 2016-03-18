
package com.buaa.comm.jobstatistics.manager;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Jobconcern;
import com.buaa.comm.domain.Jobstatistics;
import com.buaa.comm.jobstatistics.dao.JobstatisticsDao;
import com.buaa.comm.joballot.manager.JoballotManager;
import com.buaa.comm.jobconcern.manager.JobconcernManager;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("jobstatisticsManager")
public class JobstatisticsManager {
	
	@Resource
	private JobstatisticsDao jobstatisticsDao;
		@Resource
	private JoballotManager joballotManager;
		@Resource
	private JobconcernManager jobconcernManager;
		@Resource	
		private UserOperationLogManager userOperationLogManager;	
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryJobstatistics(Page<Jobstatistics> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    jobstatisticsDao.queryJobstatistics(page,parameter,criteria);
        if(null != parameter && !parameter.isEmpty()){
        	Boolean setting = (Boolean)parameter.get("setting");
        	if(setting)
		        for(Jobconcern jobconcern:jobconcernManager.getJobConcernByUser()){
			        for(Jobstatistics job:page.getEntities()){
				        if(jobconcern.getJobstatistics().getOid().equals(job.getOid())){
					        job.setIsfocus(true);
				        	break;
				        }
			        }
		        }
        }
	}
	//首页统计展现读取数据
	public Collection<Jobstatistics> queryConcernedJob(){
	    return jobstatisticsDao.queryConcernedJob();
	}
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveJobstatistics(Map<String, Collection> dataItems) throws Exception {
	    Collection<Jobstatistics> details =(Collection<Jobstatistics>) dataItems.get("dsJobstatistics");
		this.saveJobstatistics(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveJobstatistics(Collection<Jobstatistics> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Jobstatistics item : details) {
				EntityState state = EntityUtils.getState(item);
				String un = ContextHolder.getLoginUserName();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					jobstatisticsDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, "对工作项目管理表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					jobstatisticsDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, "对工作项目管理表修改一条记录");
				} else if (state.equals(EntityState.DELETED)) {
					jobstatisticsDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, "对工作项目管理表删除一条记录");
				} else if (state.equals(EntityState.NONE)) {
									}
					joballotManager.saveJoballot(item.getJoballot());
					jobconcernManager.saveJobconcern(item.getJobconcern());
			}
		}
	 }
	 
	 
	
}
