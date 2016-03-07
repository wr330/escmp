package com.buaa.fly.sfstatistic.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.sfstatistic.manager.SfstatisticManager;

@Component("sfstatisticPR")
public class SfstatisticPR {

	@Resource
	private SfstatisticManager sfstatisticManager;

	/**
	 * 分页查询信息，带有criteria
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void querySfstatistic(Page<Sfstatistic> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		sfstatisticManager.querySfstatistic(page, parameter, criteria);
	}

	/**
	 * 数据保存，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveSfstatistic(Map<String, Collection> dataItems)
			throws Exception {
		sfstatisticManager.saveSfstatistic(dataItems);
	}

	/**
	 * 根据试飞科目查询架次数
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Map<String, Object>> queryJiacinum(
			Map<String, Object> parameter) throws Exception {
		return sfstatisticManager.queryJiacinum(parameter);
	}
}
