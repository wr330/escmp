
package com.buaa.fly.shifeirequestacc.manager;

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

import com.buaa.fly.domain.Shifeirequestacc;
import com.buaa.fly.shifeirequestacc.dao.ShifeirequestaccDao;
import com.buaa.fly.view.FileHelper;

@Component("shifeirequestaccManager")
public class ShifeirequestaccManager {
	
	@Resource
	private ShifeirequestaccDao shifeirequestaccDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryShifeirequestacc(Page<Shifeirequestacc> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    shifeirequestaccDao.queryShifeirequestacc(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveShifeirequestacc(Map<String, Collection> dataItems) throws Exception {
	    Collection<Shifeirequestacc> details =(Collection<Shifeirequestacc>) dataItems.get("dsShifeirequestacc");
		this.saveShifeirequestacc(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveShifeirequestacc(Collection<Shifeirequestacc> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Shifeirequestacc item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					String tempId = item.getId();
					fileManager(item);
					shifeirequestaccDao.saveData(item);
					FileHelper.changeFolderById("/Fly_Shifeirequestacc/" +tempId,"/Fly_Shifeirequestacc/" +item.getId());//替换以临时ID命名的文件夹
				} else if (state.equals(EntityState.MODIFIED)) {
					fileManager(item);
					shifeirequestaccDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					shifeirequestaccDao.deleteData(item);
					FileHelper.deleteFile("/Fly_Shifeirequestacc/" +item.getId());//删除相关文件
				} else if (state.equals(EntityState.NONE)) {
									}
							}
		}
	 }
	 //处理相关文件
	 private void fileManager(Shifeirequestacc item){
		String path = "/Fly_Shifeirequestacc/" + item.getId() + "/"+ item.getFilename();
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
	 public String downloadFile(int id,String fname) throws IOException{
		 String path = "/Fly_Shifeirequestacc/" + id + "/";
		 if(!FileHelper.existFile(path,fname)){
			 Shifeirequestacc shifeirequestacc = shifeirequestaccDao.queryById(id);
			 byte[] datablock=shifeirequestacc.getDatablock();
			 FileHelper.createFile(path,fname,datablock);
		 }
		 return fname;
	 }


}
