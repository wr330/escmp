package com.bstek.newstree.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bstek.newstree.domain.NewsComments;



public class NewsCommentsRowMapper implements RowMapper<NewsComments>{
	public NewsComments mapRow(ResultSet rs, int arg1) throws SQLException {
		NewsComments newsComments = new NewsComments();
		newsComments.setId(rs.getString("ID_"));
		newsComments.setContents(rs.getString("CONTENTS_"));
		newsComments.setUserName(rs.getString("USERNAME_"));
		newsComments.setDate(rs.getTimestamp("NRSDATE_"));
		newsComments.setNodeCode(rs.getString("NODECODE_"));
		return newsComments;
	}

}
