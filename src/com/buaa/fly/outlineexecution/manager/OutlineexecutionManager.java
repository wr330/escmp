package com.buaa.fly.outlineexecution.manager;

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

import com.buaa.fly.combineVehicle.dao.CombineVehicleDao;
import com.buaa.fly.combineVehicle.manager.CombineVehicleManager;
import com.buaa.fly.domain.CombineVehicle;
import com.buaa.fly.domain.Outlineexecution;

import com.buaa.fly.outlineexecution.dao.OutlineexecutionDao;
import com.buaa.fly.outlineexecution.dao.OutlineexecutionDaoforJDBC;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("outlineexecutionManager")
public class OutlineexecutionManager {

	@Resource
	private OutlineexecutionDao outlineexecutionDao;
	@Resource
	private OutlineexecutionDaoforJDBC outlineexecutionDaoforJDBC;
	@Resource
	private CombineVehicleManager combineVehicleManager;
	@Resource
	private CombineVehicleDao combineVehicleDao;
	@Resource
	private ExportOutline exportOutline;
	@Resource	
	private UserOperationLogManager userOperationLogManager;

	/**
	 * 大纲查询方法
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Outlineexecution> queryOutlineexecution(
			Map<String, Object> parameter) throws Exception {
		return outlineexecutionDao.queryOutlineexecution(parameter);
	}

	/**
	 * 大纲查询方法，该方法没有应用
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Outlineexecution> query(Map<String, Object> parameter)
			throws Exception {
		return outlineexecutionDao.query(parameter);
	}

	/**
	 * 大纲查询方法，该方法没有应用
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Outlineexecution> queryOutlineexecutionforJDBC(
			Map<String, Object> parameter) throws Exception {
		return outlineexecutionDaoforJDBC.query(parameter);
	}

	/**
	 * 分页查询信息，带有criteria 将criteria转换为一个Map
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryOutline(Page<Outlineexecution> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		outlineexecutionDao.queryOutline(page, parameter, criteria);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveOutlineexecution(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Outlineexecution> details = (Collection<Outlineexecution>) dataItems
				.get("dataSetOutlineexecution");
		this.saveOutlineexecution(details);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改，该方法没有应用
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveOutline(Map<String, Collection> dataItems) throws Exception {
		Collection<Outlineexecution> details = (Collection<Outlineexecution>) dataItems
				.get("dsOutlineexecution");
		this.saveOutlineexecution(details);
	}

	/**
	 * 查询大纲树是否有子节点，该方法没有应用
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public int countChildren(Map<String, Object> parameter) {
		return outlineexecutionDao.countChildren(parameter);
	}
	
	/**
	 * 下载文件
	 * 
	 * @param id
	 * @param fname
	 * @throws Exception
	 */
	@Expose
	public String downloadFile(String id, String fname) throws Exception {

		Collection<Outlineexecution> outlineexecution = outlineexecutionDao
				.queryOutlineforText(id,fname);
		if (null != outlineexecution && outlineexecution.size() > 0) {
			exportOutline.generateOutlineexecution(outlineexecution);
		}
		else
			throw new Exception("当前节点下没有试飞大纲！");
		return fname + "试飞大纲.doc";
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveOutlineexecution(Collection<Outlineexecution> details)
			throws Exception {
		if (null != details && details.size() > 0) {
			for (Outlineexecution item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					outlineexecutionDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对飞机试飞大纲表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					outlineexecutionDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对飞机试飞大纲表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					Collection<CombineVehicle> cv = combineVehicleDao.queryCVbyOutline(item.getOid());
					this.deleteCombineVehicle(cv);
					outlineexecutionDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对飞机试飞大纲表删除选定记录");
				} else if (state.equals(EntityState.NONE)) {
					EntityState subjectState = EntityUtils.getState(item
							.getSubject());
					if (item.getChildren() != null) {
						saveOutlineexecution(item.getChildren());
					}
					if (subjectState.equals(EntityState.MODIFIED)) {
						outlineexecutionDao.updateData(item);
					}
				}
				combineVehicleManager.saveCombineVehicle(item
						.getCombineVehicle());
			}
		}
	}

	/**
	 * 导出试飞大纲
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public void statisticOutlineexecution(Collection<Outlineexecution> details)
			throws Exception {
		if (null != details && details.size() > 0) {
			for (Outlineexecution item : details) {
				if (item.getParentnode() == null) {
					outlineexecutionDao.statisticMainSubject(item);
				} else {
					outlineexecutionDao.statisticSubject(item);
				}
			}
		}
	}
	/**
	 * 删除结合架次方法
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public void deleteCombineVehicle(Collection<CombineVehicle> details)
			throws Exception {
		if (null != details && details.size() > 0) {
			for (CombineVehicle item : details) {
				combineVehicleDao.deleteData(item);
			}
		}
	}
	
}
