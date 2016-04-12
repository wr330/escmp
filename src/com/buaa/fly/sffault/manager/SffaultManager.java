package com.buaa.fly.sffault.manager;

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

import com.buaa.fly.domain.Sffault;
import com.buaa.fly.sffault.dao.SffaultDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("sffaultManager")
public class SffaultManager {

	@Resource
	private SffaultDao sffaultDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;

	/**
	 * 分页查询信息，带有criteria 将criteria转换为一个Map
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void querySffault(Page<Sffault> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		sffaultDao.querySffault(page, parameter, criteria);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveSffault(Map<String, Collection> dataItems) throws Exception {
		Collection<Sffault> details = (Collection<Sffault>) dataItems
				.get("dsSffault");
		this.saveSffault(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveSffault(Collection<Sffault> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Sffault item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					sffaultDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对问题追踪表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					sffaultDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对问题追踪表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					sffaultDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对问题追踪表删除选定记录");
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	}

}
