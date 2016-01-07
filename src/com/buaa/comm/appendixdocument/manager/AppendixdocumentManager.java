
package com.buaa.comm.appendixdocument.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Appendixdocument;
import com.buaa.comm.appendixdocument.dao.AppendixdocumentDao;

@Component("appendixdocumentManager")
public class AppendixdocumentManager {
	
	@Resource
	private AppendixdocumentDao appendixdocumentDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryAppendixdocument(Page<Appendixdocument> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    appendixdocumentDao.queryAppendixdocument(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveAppendixdocument(Map<String, Collection> dataItems) throws Exception {
	    Collection<Appendixdocument> details =(Collection<Appendixdocument>) dataItems.get("dsAppendixdocument");
		this.saveAppendixdocument(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveAppendixdocument(Collection<Appendixdocument> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Appendixdocument item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					appendixdocumentDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					appendixdocumentDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										appendixdocumentDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
						EntityState btreportState = EntityUtils.getState(item.getBtreport());
		
	if(btreportState.equals(EntityState.MODIFIED)){
	appendixdocumentDao.updateData(item);
	}
									}
			}
		}
	 }
	 
	//删除从表
	  public void deleteItems(String primaryOid) throws Exception{
		  appendixdocumentDao.deleteItems(primaryOid);
	  } 
	
}
