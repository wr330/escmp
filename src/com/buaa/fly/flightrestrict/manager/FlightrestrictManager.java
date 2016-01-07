
package com.buaa.fly.flightrestrict.manager;

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

import com.buaa.fly.domain.Flightrestrict;
import com.buaa.fly.flightrestrict.dao.FlightrestrictDao;
import com.buaa.fly.view.FileHelper;

@Component("flightrestrictManager")
public class FlightrestrictManager {
	
	@Resource
	private FlightrestrictDao flightrestrictDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryFlightrestrict(Page<Flightrestrict> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    flightrestrictDao.queryFlightrestrict(page,parameter,criteria);
	}
	public Collection<Flightrestrict> queryshfxzh(Map<String, Object> parameter) throws Exception {
	     return flightrestrictDao.queryshfxzh(parameter);
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveFlightrestrict(Map<String, Collection> dataItems) throws Exception {
	    Collection<Flightrestrict> details =(Collection<Flightrestrict>) dataItems.get("dsFlightrestrict");
		this.saveFlightrestrict(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveFlightrestrict(Collection<Flightrestrict> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Flightrestrict item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					int tempId = item.getId();
					fileManager(item);
					flightrestrictDao.saveData(item);
					FileHelper.changeFolderById("/Fly_Flightrestrict/" +tempId,"/Fly_Flightrestrict/" +item.getId());//替换以临时ID命名的文件夹
				} else if (state.equals(EntityState.MODIFIED)) {
					fileManager(item);
					flightrestrictDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					flightrestrictDao.deleteData(item);
					FileHelper.deleteFile("/Fly_Flightrestrict/" +item.getId());//删除相关文件
				} else if (state.equals(EntityState.NONE)) {
									}
							}
		}
	 }
	 //处理相关文件
	 private void fileManager(Flightrestrict item){
			String path = "/Fly_Flightrestrict/" + item.getId() + "/"+ item.getFname();
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
		 String path = "/Fly_Flightrestrict/" + id + "/";
		 if(!FileHelper.existFile(path,fname)){
			 Flightrestrict flightrestrict = flightrestrictDao.queryById(id);
			 byte[] datablock=flightrestrict.getDatablock();
			 FileHelper.createFile(path,fname,datablock);
		 }
	     return fname;
    }
	
}
