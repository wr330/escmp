package com.buaa.fly.fighterxzh.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fighterxzh;
import com.buaa.fly.fighterxzh.manager.FighterxzhManager;

@Component("fighterxzhPR")
public class FighterxzhPR {

	@Resource
	private FighterxzhManager fighterxzhManager;

	/**
	 * 分页查询信息，带有criteria
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryFighterxzh(Page<Fighterxzh> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		fighterxzhManager.queryFighterxzh(page, parameter, criteria);
	}

	/**
	 * 数据保存，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveFighterxzh(Map<String, Collection> dataItems)
			throws Exception {
		fighterxzhManager.saveFighterxzh(dataItems);
	}

	/**
	 * 删除该使用限制下所有的飞机关联信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@Expose
	public void deleteFighterxzh(Map<String, Object> parameter)
			throws Exception {
		fighterxzhManager.deleteFighterxzh(parameter);
	}
}
