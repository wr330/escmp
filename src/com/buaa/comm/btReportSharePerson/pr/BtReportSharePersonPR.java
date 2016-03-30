package com.buaa.comm.btReportSharePerson.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.comm.btReportSharePerson.manager.BtReportSharePersonManager;
import com.buaa.comm.domain.BtReportSharePerson;

@Component("btReportSharePersonPR")
public class BtReportSharePersonPR {
	
	@Resource
	private BtReportSharePersonManager btReportSharePersonManager;

	     
	   /**                  
		* 分页查询信息，带有criteria
		* @param page    
		* @param map
		* @throws Exception
		*/
		@DataProvider
		public void queryBtReportSharePerson(Page<BtReportSharePerson> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
			btReportSharePersonManager.queryBtReportSharePerson(page,parameter,criteria);
		}
		
		
		/**
		 * 数据保存，包括增删改
		 * @param dataItems
		 * @throws Exception
		 */
		 @SuppressWarnings("rawtypes")
		 @DataResolver
		 public void saveBtReportSharePerson(Map<String, Collection> dataItems) throws Exception {
		    btReportSharePersonManager.saveBtReportSharePerson(dataItems);
		 }
	
}
