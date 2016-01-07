
package com.buaa.fly.ftypes.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.ftypes.dao.FtypesDao;
import com.buaa.fly.fpici.manager.FpiciManager;

@Component("ftypesManager")
public class FtypesManager {
	
	@Resource
	private FtypesDao ftypesDao;
		@Resource
	private FpiciManager fpiciManager;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryFtypes(Page<Ftypes> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    ftypesDao.queryFtypes(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveFtypes(Map<String, Collection> dataItems) throws Exception {
	    Collection<Ftypes> details =(Collection<Ftypes>) dataItems.get("dsCurrentTree");
		this.saveFtypes(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveFtypes(Collection<Ftypes> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Ftypes item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					item.setFighterid(11);
					ftypesDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					ftypesDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					ftypesDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
					if(item.getCategories()!=null){
						this.saveFtypes(item.getCategories());
					}
									}
				
					fpiciManager.saveFpici(item.getFpici());
			}
		}
	 }
	 
	 
	
}
