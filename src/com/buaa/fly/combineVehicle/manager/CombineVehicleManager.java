package com.buaa.fly.combineVehicle.manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.CombineVehicle;
import com.buaa.fly.combineVehicle.dao.CombineVehicleDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("combineVehicleManager")
public class CombineVehicleManager {

	@Resource
	private CombineVehicleDao combineVehicleDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;

	/**
	 * 分页查询信息，带有criteria 将criteria转换为一个Map
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryCombineVehicle(Page<CombineVehicle> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		combineVehicleDao.queryCombineVehicle(page, parameter, criteria);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveCombineVehicle(Map<String, Collection> dataItems)
			throws Exception {
		Collection<CombineVehicle> details = (Collection<CombineVehicle>) dataItems
				.get("dsCombineVehicle");
		this.saveCombineVehicle(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveCombineVehicle(Collection<CombineVehicle> details)
			throws Exception {
		if (null != details && details.size() > 0) {
			for (CombineVehicle item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					combineVehicleDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对试飞大纲结合架次表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					combineVehicleDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对试飞大纲结合架次表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					combineVehicleDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对试飞大纲结合架次表删除选定记录");
				} else if (state.equals(EntityState.NONE)) {
					EntityState outlineExecutionState = EntityUtils
							.getState(item.getOutlineExecution());

					if (outlineExecutionState.equals(EntityState.MODIFIED)) {
						combineVehicleDao.updateData(item);
					}
				}

			}
		}
	}
}
