package com.buaa.out.supportprogram.manager;




import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.out.domain.Supportitem;
import com.buaa.out.domain.Supportprogram;
import com.buaa.out.supportitem.manager.SupportitemManager;
import com.buaa.out.supportprogram.dao.SupportprogramDao;


@Component("supportprogramManager")
public class SupportprogramManager {
	
	@Resource
	private SupportprogramDao supportprogramDao;
		@Resource
	private SupportitemManager supportitemManager;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querySupportprogram(Page<Supportprogram> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    supportprogramDao.querySupportprogram(page,parameter,criteria);
	}
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public Collection<Supportitem> queryRenyuan(Map<String, Object> parameter) throws Exception {
	    return supportprogramDao.queryRenyuan(parameter);
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSupportprogram(Map<String, Collection> dataItems) throws Exception {
	    Collection<Supportprogram> details =(Collection<Supportprogram>) dataItems.get("dsCurrentTree");
		this.saveSupportprogram(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveSupportprogram(Collection<Supportprogram> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Supportprogram item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					supportprogramDao.saveData(item);
					supportitemManager.saveSupportitem(item.getSupportitem());
				} else if (state.equals(EntityState.MODIFIED)) {
					supportprogramDao.updateData(item);
					supportitemManager.saveSupportitem(item.getSupportitem());
				} else if (state.equals(EntityState.DELETED)) {
					supportitemManager.saveSupportitem(item.getSupportitem());
					supportprogramDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
					if(item.getChildren()!=null){
						this.saveSupportprogram(item.getChildren());
					}
					supportitemManager.saveSupportitem(item.getSupportitem());
				}
			}
		}
	 }
	 
	 
	
}
