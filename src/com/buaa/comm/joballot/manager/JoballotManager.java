
package com.buaa.comm.joballot.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Joballot;
import com.buaa.comm.joballot.dao.JoballotDao;

@Component("joballotManager")
public class JoballotManager {
	
	@Resource
	private JoballotDao joballotDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryJoballot(Page<Joballot> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    joballotDao.queryJoballot(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveJoballot(Map<String, Collection> dataItems) throws Exception {
	    Collection<Joballot> details =(Collection<Joballot>) dataItems.get("dsJoballot");
		this.saveJoballot(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveJoballot(Collection<Joballot> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Joballot item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					joballotDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					joballotDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					joballotDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState jobstatisticsState = EntityUtils.getState(item.getJobstatistics());
		
	if(jobstatisticsState.equals(EntityState.MODIFIED)){
	joballotDao.updateData(item);
	}
				}
							}
		}
	 }
	 
		@Expose
		public String personIsExists(String oid,String person,String jobstatistics) {
			return joballotDao.personIsExists(oid,person,jobstatistics);
		}
	
}
