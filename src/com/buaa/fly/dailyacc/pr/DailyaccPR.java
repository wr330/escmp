package com.buaa.fly.dailyacc.pr;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Dailyacc;
import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.domain.Sfstatistic;
import com.buaa.fly.dailyacc.manager.DailyaccManager;
import com.buaa.fly.dailyacc.manager.ExportDailyPaper;

@Component("dailyaccPR")
public class DailyaccPR {

	@Resource
	private DailyaccManager dailyaccManager;
	@Resource
	private ExportDailyPaper exportDailyPaper;

	/**
	 * 分页查询信息，带有criteria
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryDailyacc(Page<Dailyacc> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		dailyaccManager.queryDailyacc(page, parameter, criteria);
	}

	/**
	 * 数据保存，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveDailyacc(Map<String, Collection> dataItems)
			throws Exception {
		dailyaccManager.saveDailyacc(dataItems);
	}

	/**
	 * 生成模板
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public String save(Map<String, Collection> dataItems,Map<String, Object> parameter) throws Exception {
		Collection<Sfstatistic> details1 = (Collection<Sfstatistic>) dataItems.get("sta");
		Date time = (Date) parameter.get("time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String dateStr = sdf.format(time);
		exportDailyPaper.wordTemplate(details1,dateStr);
		return dateStr;
	}

}
