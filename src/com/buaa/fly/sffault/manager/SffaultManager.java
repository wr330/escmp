
package com.buaa.fly.sffault.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Sffault;
import com.buaa.fly.sffault.dao.SffaultDao;

@Component("sffaultManager")
public class SffaultManager {
	
	@Resource
	private SffaultDao sffaultDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querySffault(Page<Sffault> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    sffaultDao.querySffault(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSffault(Map<String, Collection> dataItems) throws Exception {
	    Collection<Sffault> details =(Collection<Sffault>) dataItems.get("dsSffault");
		this.saveSffault(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveSffault(Collection<Sffault> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Sffault item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					sffaultDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					sffaultDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					sffaultDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
									}
							}
		}
	 }
	 
	 
	
}
