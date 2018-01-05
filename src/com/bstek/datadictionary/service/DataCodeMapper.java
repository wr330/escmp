package com.bstek.datadictionary.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bstek.datadictionary.domain.DataCode;


public class DataCodeMapper implements RowMapper<DataCode>{

	public DataCode mapRow(ResultSet rs, int arg1)
			throws SQLException {
		DataCode dataCode = new DataCode();
		dataCode.setDcId(rs.getString("DC_ID_"));
		dataCode.setDataName(rs.getString("DATA_NAME_"));
		dataCode.setDataCode(rs.getString("DATA_CODE_"));
		dataCode.setDataSimpleName(rs.getString("DATA_SIMPLE_NAME_"));
		dataCode.setDataOrderNo(rs.getInt("DATA_ORDER_NO_"));
		dataCode.setDataParentId(rs.getString("DATA_PARENT_ID_"));
		dataCode.setBakOne(rs.getString("BAK_ONE_"));
		dataCode.setBakTwo(rs.getString("BAK_TWO_"));
		dataCode.setCreateTime(rs.getTimestamp("CREATE_TIME_"));
		dataCode.setCreateUsername(rs.getString("CREATE_USERNAME_"));
		dataCode.setUpdateTime(rs.getTimestamp("UPDATE_TIME_"));
		dataCode.setUpdateUsername(rs.getString("UPDATE_USERNAME_"));
		
		return dataCode;
	}
}