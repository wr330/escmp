package com.bstek.viewmap.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bstek.viewmap.domain.ViewMap;

public class ViewMapRowMapper implements RowMapper<ViewMap> {

	public ViewMap mapRow(ResultSet rs, int arg1) throws SQLException {
		ViewMap viewMap = new ViewMap();
		viewMap.setMapId(rs.getString("MAP_ID_"));
		viewMap.setMapCode(rs.getString("MAP_CODE_"));
		viewMap.setMapPath(rs.getString("MAP_PATH_"));
		viewMap.setNodeCode(rs.getString("NODE_CODE_"));
		viewMap.setCreateUser(rs.getString("CREATE_DATE_"));
		viewMap.setUpdateUser(rs.getString("UPDATE_DATE_"));
		viewMap.setCreateDate(rs.getDate("CREATE_DATE_"));
		viewMap.setUpdateDate(rs.getDate("UPDATE_USER_"));
		return viewMap;
	}

}
