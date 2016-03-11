package com.buaa.fly.fighterinfo.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fighterinfo;
import com.buaa.fly.fighterinfo.dao.FighterinfoDao;

@Component("fighterinfoManager")
public class FighterinfoManager {

	@Resource
	private FighterinfoDao fighterinfoDao;

	/**
	 * 查询信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fighterinfo> queryFighterinfo(
			Map<String, Object> parameter) throws Exception {
		return fighterinfoDao.queryFighterinfo(parameter);
	}

	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * 
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveFighterinfo(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Fighterinfo> details = (Collection<Fighterinfo>) dataItems
				.get("dsFighterinfo");
		this.saveFighterinfo(details);
	}

	/**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	public void saveFighterinfo(Collection<Fighterinfo> details)
			throws Exception {
		if (null != details && details.size() > 0) {
			for (Fighterinfo item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					if (fighterinfoIsExists(item.getOutfactoryno())
							.equals("此飞机已存在！"))
						throw new Exception("此飞机已存在！");
					fighterinfoDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					fighterinfoDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					fighterinfoDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
					EntityState fpiciState = EntityUtils.getState(item
							.getPiciid());
					if (fpiciState.equals(EntityState.MODIFIED)) {
						fighterinfoDao.updateData(item);
					}
				}
			}
		}
	}
	
	/**
	 * 这个方法用来判断在添加时，该飞机编号是否已经存在
	 * 
	 * @param fighterinfo
	 *            用户输入的飞机编号
	 */
	@Expose
	public String fighterinfoIsExists(String fighterinfo) {
		return fighterinfoDao.fighterinfoIsExists(fighterinfo);
	}
	
	/**
	 * 根据机型查询飞机信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fighterinfo> queryFighterinfobyFtype(Map<String, Object> parameter) {
		return fighterinfoDao.queryFighterinfobyFtype(parameter);
	}
}
