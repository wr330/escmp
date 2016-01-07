package com.bstek.newstree.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bstek.newstree.domain.NewsTree;

public class NewsRowMapper implements RowMapper<NewsTree> {

	public NewsTree mapRow(ResultSet rs, int arg1) throws SQLException {
		NewsTree newsTree = new NewsTree();
		newsTree.setNodeId(rs.getString("node_id"));
		newsTree.setNodeTitle(rs.getString("node_title"));
		newsTree.setNodeContent(rs.getString("node_content"));
		newsTree.setParentId(rs.getString("parent_id"));
		newsTree.setCreateDate(rs.getDate("create_date"));
		newsTree.setUpdateDate(rs.getDate("update_date"));
		newsTree.setCreateUser(rs.getString("create_user"));
		newsTree.setUpdateUser(rs.getString("update_user"));
		newsTree.setStatu(rs.getString("statu"));
		newsTree.setIsleaf(rs.getString("isleaf"));
		newsTree.setOrderDate(rs.getTimestamp("order_date"));
		newsTree.setIcon(rs.getString("icon"));
		newsTree.setNodeCode(rs.getString("node_code"));
		return newsTree;
	}

}
