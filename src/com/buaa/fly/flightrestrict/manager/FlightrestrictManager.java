package com.buaa.fly.flightrestrict.manager;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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

import com.buaa.fly.domain.Flightrestrict;
import com.buaa.fly.fighterxzh.manager.FighterxzhManager;
import com.buaa.fly.flightrestrict.dao.FlightrestrictDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;
import com.common.FileHelper;

@Component("flightrestrictManager")
public class FlightrestrictManager {

	@Resource
	private FlightrestrictDao flightrestrictDao;
	@Resource
	private FighterxzhManager fighterxzhManager;
	@Resource	
	private UserOperationLogManager userOperationLogManager;

	/**
	 * 分页查询信息，带有criteria 将criteria转换为一个Map
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryFlightrestrict(Page<Flightrestrict> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		flightrestrictDao.queryFlightrestrict(page, parameter, criteria);
	}
	
	/**
	 * 查询使用限制信息方法
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Flightrestrict> queryshfxzh(Map<String, Object> parameter)
			throws Exception {
		return flightrestrictDao.queryshfxzh(parameter);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveFlightrestrict(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Flightrestrict> details = (Collection<Flightrestrict>) dataItems
				.get("dsFlightrestrict");
		this.saveFlightrestrict(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveFlightrestrict(Collection<Flightrestrict> details)
			throws Exception {
		if (null != details && details.size() > 0) {
			for (Flightrestrict item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					fileManager(item);
					flightrestrictDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对飞机使用限制表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					fileManager(item);
					flightrestrictDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对飞机使用限制表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("id", item.getId());
					fighterxzhManager.deleteFighterxzh(parameter);
					flightrestrictDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对飞机使用限制表删除选定记录");
					FileHelper
							.deleteFile("/Fly_Flightrestrict/" + item.getId());// 删除相关文件
				} else if (state.equals(EntityState.NONE)) {
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("id", item.getId());
					fighterxzhManager.deleteFighterxzh(parameter);
				}
				fighterxzhManager.saveFighterxzh(item.getFighterxzh());
			}
		}
	}

	/**
	 * 添加校验，判断文件编号唯一
	 * 
	 * @param id
	 *            使用限制表id
	 * @param fno
	 *            文件编号
	 * 
	 */
	@Expose
	public String flightrestrictIsExists(String id, String fno) {
		return flightrestrictDao.flightrestrictIsExists(id, fno);
	}

	/**
	 * 处理相关文件
	 * 
	 * @param item
	 */
	private void fileManager(Flightrestrict item) {
		String path = "/Fly_Flightrestrict/" + item.getId() + "/"
				+ item.getFilename();
		FileHelper.fileToData(path);
		if (FileHelper.bytes != 0) {
			item.setBytes(FileHelper.bytes);
			item.setDatablock(FileHelper.datablock);// 文件存储到数据库中
			FileHelper.bytes = 0;
			FileHelper.datablock = null;
		}
	}
}
