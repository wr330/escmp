package com.buaa.comm.btreport.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.comm.domain.Btreport;
import com.buaa.comm.btreport.manager.BtreportManager;

@Component("btreportPR")
public class BtreportPR{

    @Resource
	private BtreportManager btreportManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryBtreport(Page<Btreport> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    btreportManager.queryBtreport(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveBtreport(Map<String, Collection> dataItems) throws Exception {
	    btreportManager.saveBtreport(dataItems);
	 }
	
}
