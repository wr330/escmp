
package com.buaa.fly.fighterout.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fighterout;
import com.buaa.fly.fighterout.dao.FighteroutDao;

@Component("fighteroutManager")
public class FighteroutManager {
	
	@Resource
	private FighteroutDao fighteroutDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryFighterout(Page<Fighterout> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    fighteroutDao.queryFighterout(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveFighterout(Map<String, Collection> dataItems) throws Exception {
	    Collection<Fighterout> details =(Collection<Fighterout>) dataItems.get("dsFighterout");
		this.saveFighterout(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveFighterout(Collection<Fighterout> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Fighterout item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					fighteroutDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					fighteroutDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										fighteroutDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
