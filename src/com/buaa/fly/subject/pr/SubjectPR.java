package com.buaa.fly.subject.pr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Sffault;
import com.buaa.fly.domain.Subject;
import com.buaa.fly.subject.manager.SubjectManager;

@Component("subjectPR")
public class SubjectPR{

    @Resource
	private SubjectManager subjectManager;

     
   /**                  
	* @throws Exception
	*/
	@DataProvider
	public Collection<Subject> querySubject(Map<String, Object> parameter) throws Exception {
		return subjectManager.querySubject(parameter);   
	}
	 
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveSubject(Map<String, Collection> dataItems) throws Exception {
	    subjectManager.saveSubject(dataItems);
	 }

	/**
	 * 关联数据
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void copySubject(Map<String, Collection> dataItems) throws Exception {
		subjectManager.copySubject(dataItems);
	}
	
	 @Expose
	public int countChildren(Map<String, Object> parameter) {
		return subjectManager.countChildren(parameter);
	}
	 /**                  
		* 分页查询信息，带有criteria
		* @param page    
		* @param map
		* @throws Exception
		*/
	@DataProvider
	public void querygenericSubject(Page<Subject> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		subjectManager.querygenericSubject(page,parameter,criteria);
	}
}
