
package com.buaa.fly.ftypes.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fpici;
import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.domain.Root;
import com.buaa.fly.ftypes.dao.FtypesDao;
import com.buaa.fly.fpici.dao.FpiciDao;
import com.buaa.fly.fpici.manager.FpiciManager;

@Component("ftypesManager")
public class FtypesManager {
	
	@Resource
	private FtypesDao ftypesDao;
		@Resource
	private FpiciManager fpiciManager;
		@Resource	
	private	FpiciDao fpiciDao;
		
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
		 Collection<Root> detail =(Collection<Root>) dataItems.get("dsExamples");
		 for(Root item : detail) {
			 Collection<Ftypes> details=item.getCategories();
			 this.saveFtypes(details);
		 }    
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
					ftypesDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					ftypesDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					Collection<Fpici> picis= fpiciDao.queryFighterinfobyType(item.getFtypename());
					if(picis!=null)
						fpiciDao.deleteData(picis);
					ftypesDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
					/*if(item.getCategories()!=null){
						this.saveFtypes(item.getCategories());
					}*/
									}
				
					fpiciManager.saveFpici(item.getFpici());
			}
		}
	 }
	 
	 
	
}
