package com.bstek.viewmap.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.orm.jdbc.JdbcDao;
//import com.bstek.bdf.pagination.JdbcDaoSupportExt;
//import com.bstek.bdf.pagination.Pagination;
import com.bstek.datadictionary.service.DataCodeMapper;
import com.bstek.dorado.data.provider.Page;
import com.bstek.viewmap.domain.ViewMap;

@Component
public class DefaultViewMapServiceImp extends JdbcDao implements ViewMapService {
	private static final int OPERATION_TYPE_NEW = 0;
	private static final int OPERATION_TYPE_UPDATE = 1;

	public Page<ViewMap> queryViewMap(int pageIndex, int pageSize) throws Exception {
		String sql = "select * from NRS_VIEW_MAP";
		String countSql = "select count(*) from NRS_VIEW_MAP";
		Page<ViewMap> page = new Page<ViewMap>(pageIndex, pageSize);
		try {
//			this.paginationQuery(sql, countSql, page, new ViewMapRowMapper());
			this.pagingQuery(page, sql.toString(), new ViewMapRowMapper());
		} catch (Exception e) {
		}
		return page;
	}

	public void queryViewMap(Page<ViewMap> page, Map<String, Object> parameter) throws Exception {

		String sql = "select top 100 percent * from NRS_VIEW_MAP order by UPDATE_DATE_ DESC ";
		String countSql = "select count(*) from NRS_VIEW_MAP ";
		Page<ViewMap> pagination = new Page<ViewMap>(page.getPageNo(), page.getPageSize());
		try {
//			this.paginationQuery(sql, countSql, pagination, new ViewMapRowMapper());
			this.pagingQuery(page, sql.toString(), countSql, new ViewMapRowMapper());
		} catch (Exception e) {
		}
//		return pagination;
	}

	public void insertViewMap(ViewMap viewMap) throws Exception {
		setSysIno(viewMap, OPERATION_TYPE_NEW);
		String sql = " INSERT INTO NRS_VIEW_MAP " + " ( " + " MAP_ID_, " + " MAP_CODE_, " + " MAP_PATH_, "
				+ " NODE_CODE_, " + " CREATE_DATE_, " + " UPDATE_DATE_, " + " CREATE_USER_, " + "UPDATE_USER_ " + " ) "
				+ " VALUES( " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ? " + " ) ";
		this.getJdbcTemplate().update(sql, viewMap.getMapId(), viewMap.getMapCode(), viewMap.getMapPath(),
				viewMap.getNodeCode(), viewMap.getCreateDate(), viewMap.getUpdateDate(), viewMap.getCreateUser(),
				viewMap.getUpdateUser());

	}

	public void updateViewMap(ViewMap viewMap) throws Exception {
		setSysIno(viewMap, OPERATION_TYPE_UPDATE);
		String sql = " UPDATE NRS_VIEW_MAP SET " + " MAP_CODE_ = ?, " + " MAP_PATH_ = ?, " + " NODE_CODE_ = ?, "
				+ " CREATE_DATE_ = ?, " + " UPDATE_DATE_ = ?, " + " CREATE_USER_ = ?, " + " UPDATE_USER_ = ? "
				+ " WHERE MAP_ID_ = ? ";
		this.getJdbcTemplate().update(sql, viewMap.getMapCode(), viewMap.getMapPath(), viewMap.getNodeCode(),
				viewMap.getCreateDate(), viewMap.getUpdateDate(), viewMap.getCreateUser(), viewMap.getUpdateUser(),
				viewMap.getMapId());
	}

	public void deleteViewMap(ViewMap viewMap) throws Exception {
		String sql = "delete from NRS_VIEW_MAP where MAP_ID_ = ?";
		this.getJdbcTemplate().update(sql, viewMap.getMapId());
	}

	private void setSysIno(ViewMap viewMap, int type) {
		String userName = ContextHolder.getLoginUserName();
		switch (type) {
		// 新增
		case OPERATION_TYPE_NEW:
			viewMap.setMapId(UUID.randomUUID().toString());
			viewMap.setCreateUser(userName);
			viewMap.setCreateDate(new Date());
			break;
		// 修改
		case OPERATION_TYPE_UPDATE:
			viewMap.setUpdateDate(new Date());
			viewMap.setUpdateUser(userName);
			break;
		}

	}

	public ViewMap queryViewMapByNodeCode(String code) throws Exception {
		String sql = "SELECT * FROM NRS_VIEW_MAP WHERE MAP_CODE_ = ? ";
		List<ViewMap> list = this.getJdbcTemplate().query(sql, new Object[] { code }, new ViewMapRowMapper());
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public List<Map<String, Object>> queryExistMapCode(String validateData) throws Exception {
		String sql = "select * from NRS_VIEW_MAP where MAP_CODE_ = ? ";
		return this.getJdbcTemplate().queryForList(sql,new Object[]{validateData});
	}

}
