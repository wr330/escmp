
package com.buaa.out.handover.manager;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Dailyacc;
import com.buaa.out.domain.Handover;
import com.buaa.out.handover.dao.HandoverDao;
import com.buaa.sys.domain.UserOperationLog;
import com.buaa.sys.userOperationLog.dao.UserOperationLogDao;
import com.common.FileHelper;

@Component("handoverManager")
public class HandoverManager {
	
	@Resource
	private HandoverDao handoverDao;
	@Resource	
	private UserOperationLogDao userOperationLogDao;
		
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
				UserOperationLog userOperationLog = new UserOperationLog();
				String un = ContextHolder.getLoginUserName();
				if (state.equals(EntityState.NEW)) {
					String tempId = item.getOid();
					fileManager(item);
					handoverDao.saveData(item);
					
					//对用户新增操作进行记录，在用户操作日志表中新增一条记录。
					Date myDate = new Date();
					userOperationLog.setLogOperationType(0);
					userOperationLog.setOperationTime(myDate);
					userOperationLog.setOperationPerson(un);
					userOperationLog.setOperationContent("对交接记录表新增一条记录");
					userOperationLogDao.saveData(userOperationLog);
					FileHelper.changeFolderById("/Out_Handover/" +tempId,"/Out_Handover/" +item.getOid());//替换以临时ID命名的文件夹
				} else if (state.equals(EntityState.MODIFIED)) {
					fileManager(item);
					handoverDao.updateData(item);
					
					//对用户修改操作进行记录，在用户操作日志表中新增一条记录。
					Date myDate = new Date();
					userOperationLog.setLogOperationType(1);
					userOperationLog.setOperationTime(myDate);
					userOperationLog.setOperationPerson(un);
					userOperationLog.setOperationContent("对交接记录表修改选定记录");
					userOperationLogDao.saveData(userOperationLog);
				} else if (state.equals(EntityState.DELETED)) {
					handoverDao.deleteData(item);
					FileHelper.deleteFile("/Out_Handover/" +item.getOid());//删除相关文件
					
					//对用户删除操作进行记录，在用户操作日志表中新增一条记录。
					Date myDate = new Date();
					userOperationLog.setLogOperationType(2);
					userOperationLog.setOperationTime(myDate);
					userOperationLog.setOperationPerson(un);
					userOperationLog.setOperationContent("对交接记录表删除选定记录");
					userOperationLogDao.saveData(userOperationLog);
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
				String path = "/Out_Handover/" + item.getOid() + "/"+ item.getEfile();
				FileHelper.fileToData(path);
				if(FileHelper.bytes != 0){
				    item.setBytes(FileHelper.bytes);
				    item.setDatablock(FileHelper.datablock);//文件存储到数据库中
				    FileHelper.bytes = 0;
				    FileHelper.datablock = null;
				}
		 }
		
		//下载文件
		 @Expose
		 public String downloadFile(String oid,String fname) throws IOException{
			 String path = "/Out_Handover/" + oid + "/";
			 if(!FileHelper.existFile(path,fname)){
				 Handover handover = handoverDao.queryById(oid);
				 byte[] datablock=handover.getDatablock();
				 FileHelper.createFile(path,fname,datablock);
			 }
		     return fname;
	    }
	 
	
}
