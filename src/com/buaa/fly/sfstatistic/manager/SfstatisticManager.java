
package com.buaa.fly.sfstatistic.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;


import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Tasklist;
import com.buaa.fly.sfstatistic.dao.SfstatisticDao;
import com.common.FileHelper;

@Component("sfstatisticManager")
public class SfstatisticManager {
	
	@Resource
	private SfstatisticDao sfstatisticDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querySfstatistic(Page<Sfstatistic> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    sfstatisticDao.querySfstatistic(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSfstatistic(Map<String, Collection> dataItems) throws Exception {
	    Collection<Sfstatistic> details =(Collection<Sfstatistic>) dataItems.get("dsSfstatistic");
		this.saveSfstatistic(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveSfstatistic(Collection<Sfstatistic> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Sfstatistic item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					String tempId = item.getId();
					fileManager(item);
					sfstatisticDao.saveData(item);
					FileHelper.changeFolderById("/Fly_Sfstatistic/" +tempId,"/Fly_Sfstatistic/" +item.getId());//替换以临时ID命名的文件夹
				} else if (state.equals(EntityState.MODIFIED)) {
					fileManager(item);
					sfstatisticDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					sfstatisticDao.deleteData(item);
					FileHelper.deleteFile("/Fly_Sfstatistic/" +item.getId());//删除相关文件
				} else if (state.equals(EntityState.NONE)) {
					EntityState tasklistState = EntityUtils.getState(item.getTaskNo());

					if (tasklistState.equals(EntityState.MODIFIED)) {
						sfstatisticDao.updateData(item);
					}
				}
							}
		}
		
	 }
	
	 //处理相关文件
	 private void fileManager(Sfstatistic item){
			String path1 = "/Fly_Sfstatistic/" + item.getId() + "/"+ item.getFilename1();
			FileHelper.fileToData(path1);
			if(FileHelper.bytes != 0){
			    item.setBytes1(FileHelper.bytes);
			    item.setDatablock1(FileHelper.datablock);//文件存储到数据库中
			    FileHelper.bytes = 0;
			    FileHelper.datablock = null;
			}
			String path2 = "/Fly_Sfstatistic/" + item.getId() + "/"+ item.getFilename2();
			FileHelper.fileToData(path2);
			if(FileHelper.bytes != 0){
			    item.setBytes2(FileHelper.bytes);
			    item.setDatablock2(FileHelper.datablock);//文件存储到数据库中
			    FileHelper.bytes = 0;
			    FileHelper.datablock = null;
			}
			String path3 = "/Fly_Sfstatistic/" + item.getId() + "/"+ item.getFilename3();
			FileHelper.fileToData(path3);
			if(FileHelper.bytes != 0){
			    item.setBytes3(FileHelper.bytes);
			    item.setDatablock3(FileHelper.datablock);//文件存储到数据库中
			    FileHelper.bytes = 0;
			    FileHelper.datablock = null;
			}
			String path4 = "/Fly_Sfstatistic/" + item.getId() + "/"+ item.getFilename4();
			FileHelper.fileToData(path4);
			if(FileHelper.bytes != 0){
			    item.setBytes4(FileHelper.bytes);
			    item.setDatablock4(FileHelper.datablock);//文件存储到数据库中
			    FileHelper.bytes = 0;
			    FileHelper.datablock = null;
			}
			String path5 = "/Fly_Sfstatistic/" + item.getId() + "/"+ item.getFilename5();
			FileHelper.fileToData(path5);
			if(FileHelper.bytes != 0){
			    item.setBytes5(FileHelper.bytes);
			    item.setDatablock5(FileHelper.datablock);//文件存储到数据库中
			    FileHelper.bytes = 0;
			    FileHelper.datablock = null;
			}
	 }
}
