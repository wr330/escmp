
package com.buaa.fly.outlineexecution.manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.combineVehicle.manager.CombineVehicleManager;
import com.buaa.fly.domain.Dailyacc;
import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.domain.Subject;
import com.buaa.fly.domain.Tasklist;

import com.buaa.fly.outlineexecution.dao.OutlineexecutionDao;

import com.buaa.fly.tasklist.dao.TasklistDao;
import com.common.FileHelper;

@Component("outlineexecutionManager")
public class OutlineexecutionManager {
	
	@Resource
	private OutlineexecutionDao outlineexecutionDao;
	@Resource
	private TasklistDao tasklistDao;
	@Resource
	private CombineVehicleManager combineVehicleManager;
	/**
	 * @throws Exception
	 */
	public Collection<Outlineexecution> queryOutlineexecution(Map<String, Object> parameter) throws Exception {
		return outlineexecutionDao.queryOutlineexecution(parameter);
	}

	
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryOutline(Page<Outlineexecution> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		outlineexecutionDao.queryOutline(page,parameter,criteria);
	}
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveOutlineexecution(Map<String, Collection> dataItems) throws Exception {
	    Collection<Outlineexecution> details =(Collection<Outlineexecution>) dataItems.get("dataSetOutlineexecution");
		this.saveOutlineexecution(details);
	 }
	 /**
		 * 数据保存，对多个数据集的操作，包括增删改
		 * @param dataItems
		 * @throws Exception
		 */
		 @SuppressWarnings({ "rawtypes", "unchecked" })
		 public void saveOutline(Map<String, Collection> dataItems) throws Exception {
		    Collection<Outlineexecution> details =(Collection<Outlineexecution>) dataItems.get("dsOutlineexecution");
			this.saveOutlineexecution(details);
		 }
		public int countChildren(Map<String, Object> parameter) {
			return outlineexecutionDao.countChildren(parameter);
		}
	 //下载文件
	 @Expose
	 public String downloadFile(String id,String fname) throws IOException{
		
		
			 Outlineexecution outlineexecution = outlineexecutionDao.queryById(id);
			 //byte[] datablock=outlineexecution.getDatablock();
			 String hql="from "+Tasklist.class.getName()+" a where a.subject like '%"+outlineexecution.getSubject()+"%'";
			 List<Tasklist> tasklist=tasklistDao.query(hql);
			 ExportOutline.generateTable(outlineexecution, tasklist);
			
		 
			 return fname+"大纲.doc";
    }
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveOutlineexecution(Collection<Outlineexecution> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Outlineexecution item : details) {
				EntityState state = EntityUtils.getState(item);	
				if (state.equals(EntityState.NEW)) {
					outlineexecutionDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					outlineexecutionDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										outlineexecutionDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState subjectState = EntityUtils.getState(item.getSubject());
						if(item.getChildren()!=null){
							saveOutlineexecution(item.getChildren());
						}
						if(subjectState.equals(EntityState.MODIFIED)){
							outlineexecutionDao.updateData(item);
						}
				}
				combineVehicleManager.saveCombineVehicle(item.getCombineVehicle());
			}
		}
	 }
	 /**
		 * 数据保存，对多个数据集的操作，包括增删改
		 * @param dataItems
		 * @throws Exception
		 */
		

		 public void statisticOutlineexecution(Collection<Outlineexecution> details) throws Exception {
				if (null != details && details.size() > 0) {
			    	for(Outlineexecution item : details) {
						if(item.getParentnode()==null){
							outlineexecutionDao.statisticMainSubject(item); 
						}else{
							outlineexecutionDao.statisticSubject(item); 
						}
					}
				}
			 }
	
}
