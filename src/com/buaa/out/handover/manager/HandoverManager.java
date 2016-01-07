
package com.buaa.out.handover.manager;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Dailyacc;
import com.buaa.fly.view.FileHelper;
import com.buaa.out.domain.Handover;
import com.buaa.out.handover.dao.HandoverDao;

@Component("handoverManager")
public class HandoverManager {
	
	@Resource
	private HandoverDao handoverDao;
		
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
				if (state.equals(EntityState.NEW)) {
					String tempId = item.getOid();
					fileManager(item);
					handoverDao.saveData(item);
					FileHelper.changeFolderById("/Out_Handover/" +tempId,"/Out_Handover/" +item.getOid());//替换以临时ID命名的文件夹
									} else if (state.equals(EntityState.MODIFIED)) {
					fileManager(item);
					handoverDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										handoverDao.deleteData(item);
										FileHelper.deleteFile("/Out_Handover/" +item.getOid());//删除相关文件
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
