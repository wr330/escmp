package com.buaa.fly.subject.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Subject;
import com.buaa.fly.outlineexecution.manager.OutlineexecutionManager;
import com.buaa.fly.subject.dao.SubjectDao;

@Component("subjectManager")
public class SubjectManager {

	@Resource
	private SubjectDao subjectDao;
	@Resource
	private OutlineexecutionManager outlineexecutionManager;
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
				String oid=item.getOid();
				String newid=UUID.randomUUID().toString();
				//String parentnode = item.getParentnode();
				Collection<Subject> tmp=subjectDao.queryChildren(oid);
				item.setOid(newid);
				/*if(name.equals("通用试飞科目"))
					item.setName(item.getFtype());
				List<Subject> lst=item.getChildren();
	
				if(parentnode != null)
					if(parentnode.equals("通用试飞科目"))
						item.setParentnode(item.getFtype());
					//subjectDao.copyData(item);
				
					*/
			}
		}
	}
	
	/**
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void copyAll(Map<String, Object> parameter) throws Exception {
			String ftype=(String) parameter.get("ftype");
			this.deleteSubject(ftype);
			Subject item=subjectDao.queryCommonSubject();
			String oid=item.getOid();
			String newid=UUID.randomUUID().toString();
			item.setFtype(ftype);
			item.setOid(newid);
			subjectDao.copyData(item);
			this.recurcive(oid, newid, ftype);
	}
	public void recurcive(String oid,String newid,String ftype) throws Exception {	
			Collection<Subject> tmp=subjectDao.queryChildren(oid);
			if (null != tmp && tmp.size() > 0){
				for (Subject sub : tmp) {
					sub.setParentnode(newid);
					sub.setFtype(ftype);
					String id=sub.getOid();
					String nid=UUID.randomUUID().toString();
					sub.setOid(nid);
					subjectDao.copyData(sub);
					this.recurcive(id, nid, ftype);
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
				if (state.equals(EntityState.DELETED)) {
					subjectDao.deleteData(item);
				}
				if (state.equals(EntityState.NONE)) {
					 outlineexecutionManager.saveOutlineexecution(item.getOutlineexecution());
				 }
				if(item.getChildren()!=null){
					 saveSubject(item.getChildren());
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
	 @Expose
		public String subjectIsOld(String oid,String pid) {
			return subjectDao.subjectIsOld(oid,pid);
		} 
}
