package com.buaa.out.supportitem.manager;



import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.out.domain.Supportitem;
import com.buaa.out.supportitem.dao.SupportitemDao;




@Component("supportitemManager")
public class SupportitemManager {
	
	@Resource
	private SupportitemDao supportitemDao;
	
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querySupportitem(Page<Supportitem> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportitemDao.querySupportitem(page,parameter,criteria);
	}
	public Collection<Supportitem> queryRenyuan(Map<String, Object> parameter) throws Exception {
	    return supportitemDao.queryRenyuan(parameter);
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSupportitem(Map<String, Collection> dataItems) throws Exception {
	    Collection<Supportitem> details =(Collection<Supportitem>) dataItems.get("dsSupportitem");
		this.saveSupportitem(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveSupportitem(Collection<Supportitem> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Supportitem item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					supportitemDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					supportitemDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					supportitemDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState supportprogramState = EntityUtils.getState(item.getSupportprogram());		
						if(supportprogramState.equals(EntityState.MODIFIED)){
							supportitemDao.updateData(item);
						}			
				}
			}
		}
	 }
	 
	 
	
}
