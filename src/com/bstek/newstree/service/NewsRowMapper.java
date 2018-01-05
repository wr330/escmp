package com.bstek.newstree.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bstek.newstree.domain.NewsTree;

public class NewsRowMapper implements RowMapper<NewsTree> {

	public NewsTree mapRow(ResultSet rs, int arg1) throws SQLException {
		NewsTree newsTree = new NewsTree();
		newsTree.setNodeId(rs.getString("NODE_ID_"));
		newsTree.setNodeTitle(rs.getString("NODE_TITLE_"));
		newsTree.setNodeContent(rs.getString("NODE_CONTENT_"));
		newsTree.setParentId(rs.getString("PARENT_ID_"));
		newsTree.setCreateDate(rs.getDate("CREATE_DATE_"));
		newsTree.setUpdateDate(rs.getDate("UPDATE_DATE_"));
		newsTree.setCreateUser(rs.getString("CREATE_USER_"));
		newsTree.setUpdateUser(rs.getString("UPDATE_USER_"));
		newsTree.setStatu(rs.getString("STATU_"));
		newsTree.setIsleaf(rs.getString("ISLEAF_"));
		newsTree.setOrderDate(rs.getTimestamp("ORDER_DATE_"));
		newsTree.setIcon(rs.getString("ICON_"));
		newsTree.setNodeCode(rs.getString("NODE_CODE_"));
		return newsTree;
	}

}
