
package com.buaa.out.handover.manager;

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

import com.buaa.out.domain.Handover;
import com.buaa.out.handover.dao.HandoverDao;
import com.buaa.sys.userOperationLog.manager.UserOperationLogManager;
import com.common.FileHelper;

@Component("handoverManager")
public class HandoverManager {
	
	@Resource
	private HandoverDao handoverDao;
	@Resource	
	private UserOperationLogManager userOperationLogManager;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryHandover(Page<Handover> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    handoverDao.queryHandover(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveHandover(Map<String, Collection> dataItems) throws Exception {
	    Collection<Handover> details =(Collection<Handover>) dataItems.get("dsHandover");
		this.saveHandover(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveHandover(Collection<Handover> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Handover item : details) {
				EntityState state = EntityUtils.getState(item);
				IUser loginUser = ContextHolder.getLoginUser();
				String ucn = loginUser.getCname();
				String un = loginUser.getUsername();
				Date myDate = new Date();
				if (state.equals(EntityState.NEW)) {
					String tempId = item.getOid();
					//fileManager(item);把附件以二进制的形式保存到数据库中，暂时不用
					handoverDao.saveData(item);
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(0, myDate, un, ucn,"对现场交接记录表新增一条记录");
					FileHelper.changeFolderById("/Out_Handover/" +tempId,"/Out_Handover/" +item.getOid());//替换以临时ID命名的文件夹
					
				} else if (state.equals(EntityState.MODIFIED)) {
					//fileManager(item);把附件以二进制的形式保存到数据库中，暂时不用
					handoverDao.updateData(item);
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(1, myDate, un, ucn,"对现场交接记录表修改选定记录");
				} else if (state.equals(EntityState.DELETED)) {
					handoverDao.deleteData(item);
					FileHelper.deleteFile("/Out_Handover/" +item.getOid());//删除相关文件
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					userOperationLogManager.recordUserOperationLog(2, myDate, un, ucn,"对现场交接记录表删除选定记录");
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	 }
		@Expose
		public String handoverIsExists(String oid,String number) {
			return handoverDao.handoverIsExists(oid,number);
		}
		 //处理相关文件
		 private void fileManager(Handover item){
				if (item.getFilename()==null || item.getFilename().isEmpty()) {
					item.setDatablock(null);
					item.setBytes(null);
				} else {
					//附件名字存储在efile字段中，因此path采用item.getEfile()
					String path = "/Out_Handover/" + item.getOid() + "/"  + item.getEfile();
					FileHelper.fileToData(path);
					if (FileHelper.bytes != 0) {
						item.setBytes(FileHelper.bytes);
						item.setDatablock(FileHelper.datablock);// 文件存储到数据库中
						FileHelper.bytes = 0;
						FileHelper.datablock = null;
					}
				}
			 }
	 
}
