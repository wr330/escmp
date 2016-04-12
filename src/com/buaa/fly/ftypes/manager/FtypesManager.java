package com.buaa.fly.ftypes.manager;

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

import com.buaa.fly.domain.Fpici;
import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.domain.Root;
import com.buaa.fly.domain.Subject;
import com.buaa.fly.ftypes.dao.FtypesDao;
import com.buaa.fly.fpici.dao.FpiciDao;
import com.buaa.fly.fpici.manager.FpiciManager;
import com.buaa.fly.subject.dao.SubjectDao;
import com.buaa.fly.subject.manager.SubjectManager;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;

@Component("ftypesManager")
public class FtypesManager {

	@Resource
	private FtypesDao ftypesDao;
	@Resource
	private FpiciManager fpiciManager;
	@Resource
	private FpiciDao fpiciDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;

	/**
	 * 查询信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Ftypes> queryFtypes(Map<String, Object> parameter)
			throws Exception {
		return ftypesDao.queryFtypes(parameter);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveFtypes(Map<String, Collection> dataItems) throws Exception {
		Collection<Root> detail = (Collection<Root>) dataItems
				.get("dsExamples");
		for (Root item : detail) {
			Collection<Ftypes> details = item.getCategories();
			this.saveFtypes(details);
		}
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveFtypes(Collection<Ftypes> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Ftypes item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					if (ftypeIsExists(item.getFtypename()) != null)
						throw new Exception("此机型已存在！");
					ftypesDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对飞机机型表新增一条记录");
				} else if (state.equals(EntityState.MODIFIED)) {
					ftypesDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对飞机机型表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					Collection<Fpici> picis = fpiciDao
							.queryFighterinfobyType(item.getFtypename());
					if (picis != null)
						fpiciDao.deleteData(picis);
					ftypesDao.deleteData(item);
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对飞机机型表删除选定记录");
				} else if (state.equals(EntityState.NONE)) {
				
				}

				fpiciManager.saveFpici(item.getFpici());
			}
		}
	}
	
	/**
	 * 这个方法用来判断在添加时机型是否已经存在
	 * 
	 * @param ftype
	 *            用户输入的机型
	 */
	@Expose
	public String ftypeIsExists(String ftype) {
		return ftypesDao.ftypeIsExists(ftype);
	}

}
