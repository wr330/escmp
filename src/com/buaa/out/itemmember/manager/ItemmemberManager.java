package com.buaa.out.itemmember.manager;



import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.out.domain.Itemmember;
import com.buaa.out.itemmember.dao.ItemmemberDao;




@Component("itemmemberManager")
public class ItemmemberManager {
	
	@Resource
	private ItemmemberDao itemmemberDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryItemmember(Page<Itemmember> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    itemmemberDao.queryItemmember(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveItemmember(Map<String, Collection> dataItems) throws Exception {
	    Collection<Itemmember> details =(Collection<Itemmember>) dataItems.get("dsItemmember");
		this.saveItemmember(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveItemmember(Collection<Itemmember> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Itemmember item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					itemmemberDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					itemmemberDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										itemmemberDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState handoverState = EntityUtils.getState(item.getHandover());
		EntityState supportitemState = EntityUtils.getState(item.getSupportitem());
		
	if(handoverState.equals(EntityState.MODIFIED)||supportitemState.equals(EntityState.MODIFIED)){
	itemmemberDao.updateData(item);
	}
									}
			}
		}
	 }
	 
	 
	
}
