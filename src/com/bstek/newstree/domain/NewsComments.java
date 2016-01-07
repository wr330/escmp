package com.bstek.newstree.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * NewsComments entity. @author MyEclipse Persistence Tools
 */

public class NewsComments implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nodeCode;
	private String contents;
	private String userName;
	private Date date;

	// Constructors

	/** default constructor */
	public NewsComments() {
	}

	/** full constructor */
	public NewsComments(String id, String contents,
			String userName, Date date, String nodeCode) {
		this.nodeCode = nodeCode;
		this.contents = contents;
		this.userName = userName;
		this.date = date;
		this.id = id;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeCode() {
		return this.nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate2() {
		String date2 = date.toString();
		return date2.substring(0, 19);
	}
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}