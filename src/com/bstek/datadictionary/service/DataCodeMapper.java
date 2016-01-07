package com.bstek.datadictionary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bstek.datadictionary.domain.DataCode;


public class DataCodeMapper implements RowMapper<DataCode>{

	public DataCode mapRow(ResultSet rs, int arg1)
			throws SQLException {
		DataCode dataCode = new DataCode();
		dataCode.setDcId(rs.getString("dc_id"));
		dataCode.setDataName(rs.getString("data_name"));
		dataCode.setDataCode(rs.getString("data_code"));
		dataCode.setDataSimpleName(rs.getString("data_simple_name"));
		dataCode.setDataOrderNo(rs.getInt("data_order_no"));
		dataCode.setDataParentId(rs.getString("data_parent_id"));
		dataCode.setBakOne(rs.getString("bak_one"));
		dataCode.setBakTwo(rs.getString("bak_two"));
		dataCode.setCreateTime(rs.getTimestamp("create_time"));
		dataCode.setCreateUsername(rs.getString("create_username"));
		dataCode.setUpdateTime(rs.getTimestamp("update_time"));
		dataCode.setUpdateUsername(rs.getString("update_username"));
		
		return dataCode;
	}
}