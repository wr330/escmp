package com.buaa.fly.combineVehicle.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.CombineVehicle;
import com.buaa.fly.combineVehicle.dao.CombineVehicleDao;
import com.buaa.fly.combineVehicle.manager.CombineVehicleManager;

@Component("combineVehiclePR")
public class CombineVehiclePR {

	@Resource
	private CombineVehicleManager combineVehicleManager;

	/**
	 * 分页查询信息，带有criteria
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryCombineVehicle(Page<CombineVehicle> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		combineVehicleManager.queryCombineVehicle(page, parameter, criteria);
	}

	/**
	 * 数据保存，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveCombineVehicle(Map<String, Collection> dataItems)
			throws Exception {
		combineVehicleManager.saveCombineVehicle(dataItems);
	}

}