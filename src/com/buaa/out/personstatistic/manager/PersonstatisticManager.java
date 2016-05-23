
package com.buaa.out.personstatistic.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.out.domain.Personstatistic;
import com.buaa.out.personstatistic.dao.PersonstatisticDao;

@Component("personstatisticManager")
public class PersonstatisticManager {
	
	@Resource
	private PersonstatisticDao personstatisticDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryPersonstatistic(Page<Personstatistic> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    personstatisticDao.queryPersonstatistic(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void savePersonstatistic(Map<String, Collection> dataItems) throws Exception {
	    Collection<Personstatistic> details =(Collection<Personstatistic>) dataItems.get("dsPersonstatistic");
		this.savePersonstatistic(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void savePersonstatistic(Collection<Personstatistic> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Personstatistic item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					personstatisticDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					personstatisticDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										personstatisticDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
