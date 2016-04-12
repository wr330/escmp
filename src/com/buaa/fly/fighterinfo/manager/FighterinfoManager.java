package com.buaa.fly.fighterinfo.manager;

import java.util.Collection;
import java.util.Date;
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

import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.fighterinfo.dao.FighterinfoDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("fighterinfoManager")
public class FighterinfoManager {

	@Resource
	private FighterinfoDao fighterinfoDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;

	/**
	 * 查询信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fighterinfo> queryFighterinfo(
			Map<String, Object> parameter) throws Exception {
		return fighterinfoDao.queryFighterinfo(parameter);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveFighterinfo(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Fighterinfo> details = (Collection<Fighterinfo>) dataItems
				.get("dsFighterinfo");
		this.saveFighterinfo(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveFighterinfo(Collection<Fighterinfo> details)
			throws Exception {
		if (null != details && details.size() > 0) {
			for (Fighterinfo item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					if (fighterinfoIsExists(item.getOutfactoryno()) != null)
						throw new Exception("此飞机已存在！");
					fighterinfoDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对飞机单机信息表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					fighterinfoDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对飞机单机信息表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					fighterinfoDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对飞机单机信息表删除选定记录");
				} else if (state.equals(EntityState.NONE)) {
					EntityState fpiciState = EntityUtils.getState(item
							.getPiciid());
					if (fpiciState.equals(EntityState.MODIFIED)) {
						fighterinfoDao.updateData(item);
					}
				}
			}
		}
	}
	
	/**
	 * 这个方法用来判断在添加时，该飞机编号是否已经存在
	 * 
	 * @param fighterinfo
	 *            用户输入的飞机编号
	 */
	@Expose
	public String fighterinfoIsExists(String fighterinfo) {
		return fighterinfoDao.fighterinfoIsExists(fighterinfo);
	}
	
	/**
	 * 根据机型查询飞机信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fighterinfo> queryFighterinfobyFtype(Map<String, Object> parameter) {
		return fighterinfoDao.queryFighterinfobyFtype(parameter);
	}
}
