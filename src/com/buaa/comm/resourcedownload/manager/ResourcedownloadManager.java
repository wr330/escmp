
package com.buaa.comm.resourcedownload.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Resourcedownload;
import com.buaa.comm.resourcedownload.dao.ResourcedownloadDao;

@Component("resourcedownloadManager")
public class ResourcedownloadManager {
	
	@Resource
	private ResourcedownloadDao resourcedownloadDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryResourcedownload(Page<Resourcedownload> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    resourcedownloadDao.queryResourcedownload(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveResourcedownload(Map<String, Collection> dataItems) throws Exception {
	    Collection<Resourcedownload> details =(Collection<Resourcedownload>) dataItems.get("dsResourcedownload");
		this.saveResourcedownload(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveResourcedownload(Collection<Resourcedownload> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Resourcedownload item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					resourcedownloadDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					resourcedownloadDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										resourcedownloadDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
