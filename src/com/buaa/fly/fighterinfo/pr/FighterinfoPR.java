package com.buaa.fly.fighterinfo.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.fighterinfo.manager.FighterinfoManager;

@Component("fighterinfoPR")
public class FighterinfoPR {

	@Resource
	private FighterinfoManager fighterinfoManager;

	/**
	 * 查询信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Fighterinfo> queryFighterinfo(Map<String, Object> parameter) 
			throws Exception {
		return fighterinfoManager.queryFighterinfo(parameter);
	}

	/**
	 * 数据保存，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveFighterinfo(Map<String, Collection> dataItems)
			throws Exception {
		fighterinfoManager.saveFighterinfo(dataItems);
	}
	
	/**
	 * 根据机型查询飞机信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Fighterinfo> queryFighterinfobyFtype(Map<String, Object> parameter) {
		return fighterinfoManager.queryFighterinfobyFtype(parameter);
	}

}
