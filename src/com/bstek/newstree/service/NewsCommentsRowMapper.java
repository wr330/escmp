package com.bstek.newstree.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bstek.newstree.domain.NewsComments;



public class NewsCommentsRowMapper implements RowMapper<NewsComments>{
	public NewsComments mapRow(ResultSet rs, int arg1) throws SQLException {
		NewsComments newsComments = new NewsComments();
		newsComments.setId(rs.getString("Id"));
		newsComments.setContents(rs.getString("Contents"));
		newsComments.setUserName(rs.getString("UserName"));
		newsComments.setDate(rs.getTimestamp("Date"));
		newsComments.setNodeCode(rs.getString("nodeCode"));
		return newsComments;
	}

}
