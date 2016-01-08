package com.buaa.fly.fpici.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fpici;
import com.buaa.fly.fpici.dao.FpiciDao;
import com.buaa.fly.fighterinfo.manager.FighterinfoManager;

@Component("fpiciManager")
public class FpiciManager {

	@Resource
	private FpiciDao fpiciDao;
	@Resource
	private FighterinfoManager fighterinfoManager;

	/**
	 * 分页查询信息，带有criteria 将criteria转换为一个Map
	 * 
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryFpici(Page<Fpici> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		fpiciDao.queryFpici(page, parameter, criteria);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveFpici(Map<String, Collection> dataItems) throws Exception {
		Collection<Fpici> details = (Collection<Fpici>) dataItems
				.get("dsFpici");
		this.saveFpici(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveFpici(Collection<Fpici> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Fpici item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					fpiciDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					fpiciDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					fpiciDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
					EntityState ftypesState = EntityUtils.getState(item
							.getFtypes());

					if (ftypesState.equals(EntityState.MODIFIED)) {
						fpiciDao.updateData(item);
					}
				}
				fighterinfoManager.saveFighterinfo(item.getFighterinfo());
			}
		}
	}

}
