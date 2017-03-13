package com.buaa.comm.btReportSharePerson.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.view.user.UserQueryDao;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.buaa.comm.btReportSharePerson.dao.BtReportSharePersonDao;
import com.buaa.comm.domain.BtReportSharePerson;
import com.buaa.comm.domain.Btreport;

@Component("btReportSharePersonManager")
public class BtReportSharePersonManager {

	@Resource
	private BtReportSharePersonDao btReportSharePersonDao;
	@Resource
	private UserQueryDao userQueryDao;
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
	 
	/**
	  * 通过前台传来的“被选中科室”、“被选中人员”与“所共享的出差报告信息”参数，用于处理出差报告共享的方法。
	  * 
	  * @param parameter
	  * @throws Exception
	  */
	 @Expose
	 public void BtReportShare(Map<String, Object> parameter) throws Exception{
		 Btreport btreport = (Btreport)parameter.get("btreport");
		 ArrayList<String> selectDepartment = (ArrayList<String>)parameter.get("selectDepart");
		 String wPerDepartment = btreport.getwPerDepartment();
		 selectDepartment.add(wPerDepartment);
		//利用Set类型来对字符数组去除重复元素
		Set<String> set = new TreeSet<String>();   
		 int len = selectDepartment.size();   
		 for(int i=0;i<len;i++){     
			 set.add(selectDepartment.get(i));//将所有字符串添加到Set   
		 }
		 selectDepartment = new ArrayList<String>(set);//Set转换为ArrayList
		 //设置parameter,对用户信息进行搜索
		 Map<String, Object> userpara = new HashMap<String, Object>();
		 userpara.put("persons", parameter.get("selectPerson"));
		 userpara.put("departments", selectDepartment);
		 Collection<DefaultUser> users = userQueryDao.queryUser(userpara);
		 
		 BtReportSharePerson sharePerson = new BtReportSharePerson();
		 for (DefaultUser item : users) {
			 sharePerson.setBtreport(btreport);
			 sharePerson.setUserName(item.getUsername());
			 sharePerson.setPersonName( item.getCname());
			 sharePerson.setPersonDepartment(item.getDepartment());
			 btReportSharePersonDao.saveData(sharePerson);
		 }
	 }
}
