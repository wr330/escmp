package com.buaa.fly.fdata.manager;




import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.fly.domain.Fdata;
import com.buaa.fly.fdata.dao.DataDao;




@Component("dataManager")
public class DataManager {
	
	@Resource
	private DataDao dataDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryData(Page<Fdata> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    dataDao.queryData(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveData(Map<String, Collection> dataItems) throws Exception {
	    Collection<Fdata> details =(Collection<Fdata>) dataItems.get("dsData");
		this.saveData(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveData(Collection<Fdata> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Fdata item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					dataDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					dataDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										dataDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState tasklistState = EntityUtils.getState(item.getTaskList());
		
	if(tasklistState.equals(EntityState.MODIFIED)){
	dataDao.updateData(item);
	}
									}
			}
		}
	 }
	 
	 
	
}
