package com.buaa.fly.ftypes.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.ftypes.dao.FtypesDao;
import com.buaa.fly.ftypes.manager.FtypesManager;

@Component("ftypesPR")
public class FtypesPR {

	@Resource
	private FtypesManager ftypesManager;
	@Resource
	private FtypesDao ftypesDao;

	/**
	 * 查询信息
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Ftypes> queryFtypes(Map<String, Object> parameter) 
			throws Exception {
		return ftypesManager.queryFtypes(parameter);
	}

	/**
	 * 查询所有机型信息
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Ftypes> queryCategories(String parentCategoryId)
			throws Exception {
		return ftypesDao.query("from " + Ftypes.class.getName() + " where 1=1");
	}

	/**
	 * 查询顶级节点机型
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Ftypes> currentTree() throws Exception {
		return ftypesDao.query("from " + Ftypes.class.getName()
				+ " where 1 = 1");
	}

	/**
	 * 数据保存，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveFtypes(Map<String, Collection> dataItems) throws Exception {
		ftypesManager.saveFtypes(dataItems);
	}

}
