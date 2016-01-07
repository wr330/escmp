package com.buaa.out.supportevaluation.manager;



import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.out.domain.Supportevaluation;
import com.buaa.out.supportevaluation.dao.SupportevaluationDao;



@Component("supportevaluationManager")
public class SupportevaluationManager {
	
	@Resource
	private SupportevaluationDao supportevaluationDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querySupportevaluation(Page<Supportevaluation> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportevaluationDao.querySupportevaluation(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSupportevaluation(Map<String, Collection> dataItems) throws Exception {
	    Collection<Supportevaluation> details =(Collection<Supportevaluation>) dataItems.get("dsSupportevaluation");
		this.saveSupportevaluation(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveSupportevaluation(Collection<Supportevaluation> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Supportevaluation item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					supportevaluationDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					supportevaluationDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										supportevaluationDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
