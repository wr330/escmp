package com.buaa.fly.fpici.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fpici;
import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.fpici.manager.FpiciManager;

@Component("fpiciPR")
public class FpiciPR {

	@Resource
	private FpiciManager fpiciManager;

	/**
	 * 查询信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Fpici> queryFpici(Map<String, Object> parameter) 
			throws Exception {
		return fpiciManager.queryFpici(parameter);
	}

	/**
	 * 数据保存，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveFpici(Map<String, Collection> dataItems) throws Exception {
		fpiciManager.saveFpici(dataItems);
	}

}
