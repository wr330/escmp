package com.buaa.fly.sfstatistic.manager;

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

import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.sfstatistic.dao.SfstatisticDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;
import com.common.FileHelper;

@Component("sfstatisticManager")
public class SfstatisticManager {

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
	public void querySfstatistic(Page<Sfstatistic> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		sfstatisticDao.querySfstatistic(page, parameter, criteria);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveSfstatistic(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Sfstatistic> details = (Collection<Sfstatistic>) dataItems
				.get("dsSfstatistic");
		this.saveSfstatistic(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveSfstatistic(Collection<Sfstatistic> details)
			throws Exception {
		if (null != details && details.size() > 0) {
			for (Sfstatistic item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					fileManager(item);
					sfstatisticDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对飞行统计表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					fileManager(item);
					sfstatisticDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对飞行统计表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					sfstatisticDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对飞行统计表删除选定记录");
					FileHelper.deleteFile("/Fly_Sfstatistic/" + item.getId());// 删除相关文件
				} else if (state.equals(EntityState.NONE)) {
					EntityState tasklistState = EntityUtils.getState(item
							.getTaskNo());

					if (tasklistState.equals(EntityState.MODIFIED)) {
						sfstatisticDao.updateData(item);
					}
				}
			}
		}

	}
	
	/**
	 * 根据试飞科目查询架次数
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public Collection<Map<String, Object>> queryJiacinum(Map<String, Object> parameter) throws Exception {
		return sfstatisticDao.queryJiacinum(parameter);
	}

	
	/**
	 * 处理相关文件
	 * 
	 * @param item
	 */
	private void fileManager(Sfstatistic item) {
		String path1 = "/Fly_Sfstatistic/" + item.getId() + "/"
				+ item.getFilename1();
		FileHelper.fileToData(path1);
		if (FileHelper.bytes != 0) {
			item.setBytes1(FileHelper.bytes);
			item.setDatablock1(FileHelper.datablock);// 文件存储到数据库中
			FileHelper.bytes = 0;
			FileHelper.datablock = null;
		}
		String path2 = "/Fly_Sfstatistic/" + item.getId() + "/"
				+ item.getFilename2();
		FileHelper.fileToData(path2);
		if (FileHelper.bytes != 0) {
			item.setBytes2(FileHelper.bytes);
			item.setDatablock2(FileHelper.datablock);// 文件存储到数据库中
			FileHelper.bytes = 0;
			FileHelper.datablock = null;
		}
		String path3 = "/Fly_Sfstatistic/" + item.getId() + "/"
				+ item.getFilename3();
		FileHelper.fileToData(path3);
		if (FileHelper.bytes != 0) {
			item.setBytes3(FileHelper.bytes);
			item.setDatablock3(FileHelper.datablock);// 文件存储到数据库中
			FileHelper.bytes = 0;
			FileHelper.datablock = null;
		}
		String path4 = "/Fly_Sfstatistic/" + item.getId() + "/"
				+ item.getFilename4();
		FileHelper.fileToData(path4);
		if (FileHelper.bytes != 0) {
			item.setBytes4(FileHelper.bytes);
			item.setDatablock4(FileHelper.datablock);// 文件存储到数据库中
			FileHelper.bytes = 0;
			FileHelper.datablock = null;
		}
		String path5 = "/Fly_Sfstatistic/" + item.getId() + "/"
				+ item.getFilename5();
		FileHelper.fileToData(path5);
		if (FileHelper.bytes != 0) {
			item.setBytes5(FileHelper.bytes);
			item.setDatablock5(FileHelper.datablock);// 文件存储到数据库中
			FileHelper.bytes = 0;
			FileHelper.datablock = null;
		}
	}
}
