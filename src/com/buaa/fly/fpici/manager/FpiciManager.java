package com.buaa.fly.fpici.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.fly.domain.Fpici;
import com.buaa.fly.domain.Ftypes;
import com.buaa.fly.fpici.dao.FpiciDao;
import com.buaa.fly.fighterinfo.manager.FighterinfoManager;

@Component("fpiciManager")
public class FpiciManager {

	@Resource
	private FpiciDao fpiciDao;
	@Resource
	private FighterinfoManager fighterinfoManager;

	/**
	 * 查询信息
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Fpici> queryFpici(Map<String, Object> parameter) 
			throws Exception {
		return fpiciDao.queryFpici(parameter);
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
					if (fpiciIsExists(item.getfTypeName().getFtypename(), item.getPiciName()) != null)
						throw new Exception("此批次已存在！");
					fpiciDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					fpiciDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					fpiciDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
					EntityState ftypesState = EntityUtils.getState(item
							.getfTypeName());

					if (ftypesState.equals(EntityState.MODIFIED)) {
						fpiciDao.updateData(item);
					}
				}
				fighterinfoManager.saveFighterinfo(item.getFighterinfo());
			}
		}
	}
	
	/**
	 * 这个方法用来判断在添加时，该机型中该批次是否已经存在
	 * 
	 * @param ftype
	 * @param fpici
	 *            用户输入的批次
	 */
	@Expose
	public String fpiciIsExists(String ftype, String fpici) {
		return fpiciDao.fpiciIsExists(ftype,fpici);
	}

}
