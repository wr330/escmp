package com.bstek.viewmap.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bstek.viewmap.domain.ViewMap;

public class ViewMapRowMapper implements RowMapper<ViewMap> {

	public ViewMap mapRow(ResultSet rs, int arg1) throws SQLException {
		ViewMap viewMap = new ViewMap();
		viewMap.setMapId(rs.getString("map_id"));
		viewMap.setMapCode(rs.getString("map_code"));
		viewMap.setMapPath(rs.getString("map_path"));
		viewMap.setNodeCode(rs.getString("node_code"));
		viewMap.setCreateUser(rs.getString("create_user"));
		viewMap.setUpdateUser(rs.getString("update_user"));
		viewMap.setCreateDate(rs.getDate("create_date"));
		viewMap.setUpdateDate(rs.getDate("update_date"));
		return viewMap;
	}

}
