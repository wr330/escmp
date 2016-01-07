
package com.buaa.fly.fighterxzh.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fighterxzh;
import com.buaa.fly.fighterxzh.dao.FighterxzhDao;

@Component("fighterxzhManager")
public class FighterxzhManager {
	
	@Resource
	private FighterxzhDao fighterxzhDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryFighterxzh(Page<Fighterxzh> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    fighterxzhDao.queryFighterxzh(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveFighterxzh(Map<String, Collection> dataItems) throws Exception {
	    Collection<Fighterxzh> details =(Collection<Fighterxzh>) dataItems.get("dsfighterxzh");
		this.saveFighterxzh(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveFighterxzh(Collection<Fighterxzh> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Fighterxzh item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					fighterxzhDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					fighterxzhDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										fighterxzhDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
