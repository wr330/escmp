
package com.buaa.comm.technicandcontract.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Technicandcontract;
import com.buaa.comm.technicandcontract.dao.TechnicandcontractDao;

@Component("technicandcontractManager")
public class TechnicandcontractManager {
	
	@Resource
	private TechnicandcontractDao technicandcontractDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryTechnicandcontract(Page<Technicandcontract> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    technicandcontractDao.queryTechnicandcontract(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveTechnicandcontract(Map<String, Collection> dataItems) throws Exception {
	    Collection<Technicandcontract> details =(Collection<Technicandcontract>) dataItems.get("dsTechnicandcontract");
		this.saveTechnicandcontract(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveTechnicandcontract(Collection<Technicandcontract> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Technicandcontract item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					technicandcontractDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					technicandcontractDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										technicandcontractDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
