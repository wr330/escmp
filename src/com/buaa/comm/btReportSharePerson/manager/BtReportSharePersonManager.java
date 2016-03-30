package com.buaa.comm.btReportSharePerson.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.comm.btReportSharePerson.dao.BtReportSharePersonDao;
import com.buaa.comm.domain.BtReportSharePerson;

@Component("btReportSharePersonManager")
public class BtReportSharePersonManager {

	@Resource
	private BtReportSharePersonDao btReportSharePersonDao;
	/**                  
	* 分页查询信息，带有criteria将criteria转换为一个Map
	* @param page    
	* @param parameter
	* @throws Exception
	*/
	public void queryBtReportSharePerson(Page<BtReportSharePerson> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		btReportSharePersonDao.queryBtReportSharePerson(page,parameter,criteria);
	}
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveBtReportSharePerson(Map<String, Collection> dataItems) throws Exception {
	    Collection<BtReportSharePerson> details =(Collection<BtReportSharePerson>) dataItems.get("dsBtReportSharePerson");
		this.saveBtReportSharePerson(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveBtReportSharePerson(Collection<BtReportSharePerson> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(BtReportSharePerson item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					btReportSharePersonDao.saveData(item);
				}
				else if (state.equals(EntityState.MODIFIED)) {
					btReportSharePersonDao.updateData(item);
				} 
				else if (state.equals(EntityState.DELETED)) {
					btReportSharePersonDao.deleteData(item);
				} 
				else if (state.equals(EntityState.NONE)) {		
				}
				
			}
		}
	 }
}
