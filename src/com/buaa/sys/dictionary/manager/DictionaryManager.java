package com.buaa.sys.dictionary.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.sys.domain.Dictionary;
import com.buaa.sys.dictionary.dao.DictionaryDao;

@Component("dictionaryManager")
public class DictionaryManager {

	@Resource
	private DictionaryDao dictionaryDao;

	/**
	 * 分页查询信息，带有criteria 将criteria转换为一个Map
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public Collection<Dictionary> queryDictionary(Map<String, Object> parameter, Criteria criteria) throws Exception {
		return dictionaryDao.queryDictionary(parameter, criteria);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveDictionary(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Dictionary> details = (Collection<Dictionary>) dataItems
				.get("dsDictionary");
		this.saveDictionary(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveDictionary(Collection<Dictionary> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Dictionary item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					dictionaryDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					dictionaryDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					dictionaryDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	}

	@Expose
	public String dictionaryIsExists(String oid,String name, String dictype) {
		return dictionaryDao.dictionaryIsExists(oid,name, dictype);
	}

}
