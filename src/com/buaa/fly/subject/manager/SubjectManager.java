package com.buaa.fly.subject.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Sffault;
import com.buaa.fly.domain.Subject;
import com.buaa.fly.subject.dao.SubjectDao;
import com.buaa.fly.tasklist.manager.TasklistManager;
import com.buaa.fly.outlineexecution.manager.OutlineexecutionManager;
import com.buaa.out.domain.Handover;

@Component("subjectManager")
public class SubjectManager {

	@Resource
	private SubjectDao subjectDao;

	/**
	 * @throws Exception
	 */
	public Collection<Subject> querySubject(Map<String, Object> parameter) throws Exception {
		return subjectDao.querySubject(parameter);
	}
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querygenericSubject(Page<Subject> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		subjectDao.querygenericSubject(page,parameter,criteria);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveSubject(Map<String, Collection> dataItems) throws Exception {
		Collection<Subject> details = (Collection<Subject>) dataItems
				.get("dataSetSubject");
		this.saveSubject(details);
	}
	/**
	 * 关联数据
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void copySubject(Map<String, Collection> dataItems) throws Exception {
		Collection<Subject> details = (Collection<Subject>) dataItems
				.get("dsSubject");
		this.copySubject(details);
	}
	/**
	 * 
	 * @param ftype
	 * @throws Exception
	 */
	public void deleteSubject(String ftype) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ftype", ftype);
		Collection<Subject> items = subjectDao.deleteSubject(map);
		this.deleteSubject(items);
	}
	/**
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void deleteSubject(Collection<Subject> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Subject item : details) {
				subjectDao.deleteData(item);
			}
		}
	}
	/**
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void copySubject(Collection<Subject> details) throws Exception {
		if (null != details && details.size() > 0) {
			ArrayList<Subject> subjects = (ArrayList<Subject>) details;
			Subject subject = (Subject) subjects.get(0);
			String ftype = subject.getFtype();
			this.deleteSubject(ftype);
			for (Subject item : details) {
				String name = item.getName();
				String parentnode = item.getParentnode();
				if(name.equals("通用试飞科目"))
					item.setName(item.getFtype());
				if(parentnode != null)
					if(parentnode.equals("通用试飞科目"))
						item.setParentnode(item.getFtype());
					subjectDao.copyData(item);
				
					
			}
		}
	}
	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveSubject(Collection<Subject> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Subject item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					subjectDao.saveData(item);
				} 
				if (state.equals(EntityState.MODIFIED)|| state.equals(EntityState.MOVED)) {
					subjectDao.updateData(item);
				} 
				if(item.getChildren()!=null){
					saveSubject(item.getChildren());
				}
				if (state.equals(EntityState.DELETED)) {
					subjectDao.deleteData(item);
				}
			}
		}
	}
	public int countChildren(Map<String, Object> parameter) {
		return subjectDao.countChildren(parameter);
	}
	 @Expose
		public String subjectIsExists(String oid,String name,String ftype) {
			return subjectDao.subjectIsExists(oid,name,ftype);
		} 
	
}
