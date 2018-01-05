package com.bstek.datadictionary.service;
import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
//import com.bstek.bdf.pagination.JdbcDaoSupportExt;
//import com.bstek.bdf.pagination.Pagination;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.bdf2.core.orm.jdbc.JdbcDao;
import com.bstek.datadictionary.domain.DataCode;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.web.DoradoContext;
@Component
public class DefaultDataCodeServiceImp extends JdbcDao implements DataCodeService {
	/**
	 * 数据字典查询
	 * 
	 * @param parameters
	 * @param page
	 * @return
	 */
	public void findDataCode(Map<String, Object> parameters, Page<DataCode> page) {
		String parentId = (String) DoradoContext.getCurrent().getAttribute(
				DoradoContext.VIEW, "parentId");
		StringBuffer sql = new StringBuffer(
				" select top 100 percent * from NRS_DATA_CODE where 1=1 ");
		StringBuffer sqlCount = new StringBuffer(
				" select count(*) from NRS_DATA_CODE where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (parameters != null) {
		
		}

		if (StringUtils.hasText(parentId)) {
			sql.append(" and DATA_PARENT_ID_ = ? ");
			sqlCount.append(" and DATA_PARENT_ID_ = ? ");
			params.add(parentId);
		}

		sql.append(" order by DATA_ORDER_NO_ asc ");

//		Page<DataCode> pagination = new Page<DataCode>(
//				page.getPageNo(), page.getPageSize());

//		this.paginationQuery(sql.toString(), sqlCount.toString(),
//				params.toArray(), pagination, new DataCodeMapper());
		this.pagingQuery(page, sql.toString(), params.toArray(), sqlCount.toString(), new DataCodeMapper());

//		return pagination;
	}

	/**
	 * 根据父节点代码查询数据字典
	 * 
	 * @param parentCode
	 * @return
	 */
	public Collection<DataCode> findDataCodeByParentCode(String code) {
		Collection<DataCode> c = new ArrayList<DataCode>();
		if (StringUtils.hasText(code)) {
			String sql = "select top 100 percent d2.* from NRS_DATA_CODE d1, NRS_DATA_CODE d2 where " +
					"d1.DATA_CODE_ = ? and d1.DC_ID_ = d2.DATA_PARENT_ID_ order by DATA_ORDER_NO_ asc";
			c = this.getJdbcTemplate().query(sql, new DataCodeMapper(),
					new Object[] { code });
		}
		return c;
	}

	/**
	 * 新增数据字典
	 * 
	 * @param dataCode
	 */
	public void insertDataCode(DataCode dataCode) {
		dataCode.setCreateTime(new Date());
		dataCode.setCreateUsername(ContextHolder.getLoginUserName());
		String parentId = (String) DoradoContext.getCurrent().getAttribute(
				DoradoContext.VIEW, "parentId");
		dataCode.setDataParentId(parentId);
		
		String sql = "insert into NRS_DATA_CODE (DC_ID_, DATA_NAME_, DATA_CODE_, "
				+ "DATA_SIMPLE_NAME_, DATA_ORDER_NO_, DATA_PARENT_ID_, BAK_ONE_, "
				+ "BAK_TWO_, CREATE_TIME_, CREATE_USERNAME_) values (?,?,?,?,?,?,?,?,?,?)";
		Object args[] = new Object[] { new VMID().toString(),
				dataCode.getDataName(), dataCode.getDataCode(),
				dataCode.getDataSimpleName(), dataCode.getDataOrderNo(),
				dataCode.getDataParentId(), dataCode.getBakOne(),
				dataCode.getBakTwo(), dataCode.getCreateTime(),
				dataCode.getCreateUsername() };
		this.getJdbcTemplate().update(sql, args);

	}

	/**
	 * 修改数据字典
	 * 
	 * @param dataCode
	 */
	public void updateDataCode(DataCode dataCode) {
		dataCode.setUpdateTime(new Date());
		dataCode.setUpdateUsername(ContextHolder.getLoginUserName());
		String sql = "update NRS_DATA_CODE set DATA_NAME_=?, DATA_CODE_=?, "
				+ "DATA_SIMPLE_NAME_=?, DATA_ORDER_NO_=?, DATA_PARENT_ID_=?, BAK_ONE_=?, "
				+ "BAK_TWO_=?, UPDATE_TIME_=?, UPDATE_USERNAME_=? where DC_ID_=?";
		Object args[] = new Object[] { dataCode.getDataName(),
				dataCode.getDataCode(), dataCode.getDataSimpleName(),
				dataCode.getDataOrderNo(), dataCode.getDataParentId(),
				dataCode.getBakOne(), dataCode.getBakTwo(),
				dataCode.getUpdateTime(), dataCode.getUpdateUsername(),
				dataCode.getDcId() };
		this.getJdbcTemplate().update(sql, args);
	}

	/**
	 * 删除数据字典
	 * 
	 * @param id
	 */
	public void deleteDataCodeById(String id) {
		String sql = "delete from NRS_DATA_CODE where DC_ID_=?";
		this.getJdbcTemplate().update(sql, new Object[] { id });
	}

}
