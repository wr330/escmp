
package com.buaa.out.technicaldocument.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.out.domain.Technicaldocument;
import com.buaa.out.technicaldocument.dao.TechnicaldocumentDao;

@Component("technicaldocumentManager")
public class TechnicaldocumentManager {
	
	@Resource
	private TechnicaldocumentDao technicaldocumentDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryTechnicaldocument(Page<Technicaldocument> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    technicaldocumentDao.queryTechnicaldocument(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveTechnicaldocument(Map<String, Collection> dataItems) throws Exception {
	    Collection<Technicaldocument> details =(Collection<Technicaldocument>) dataItems.get("dsTechnicaldocument");
		this.saveTechnicaldocument(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveTechnicaldocument(Collection<Technicaldocument> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Technicaldocument item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					technicaldocumentDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					technicaldocumentDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										technicaldocumentDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 @Expose
		public String technicaldocumentIsExists(String oid,String number) {
			return technicaldocumentDao.technicaldocumentIsExists(oid,number);
		}  
	
}
