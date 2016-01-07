
package com.buaa.out.servicepersonnel.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.out.domain.Servicepersonnel;
import com.buaa.out.servicepersonnel.dao.ServicepersonnelDao;

@Component("servicepersonnelManager")
public class ServicepersonnelManager {
	
	@Resource
	private ServicepersonnelDao servicepersonnelDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryServicepersonnel(Page<Servicepersonnel> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    servicepersonnelDao.queryServicepersonnel(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveServicepersonnel(Map<String, Collection> dataItems) throws Exception {
	    Collection<Servicepersonnel> details =(Collection<Servicepersonnel>) dataItems.get("dsServicepersonnel");
		this.saveServicepersonnel(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveServicepersonnel(Collection<Servicepersonnel> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Servicepersonnel item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					servicepersonnelDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					servicepersonnelDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										servicepersonnelDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
