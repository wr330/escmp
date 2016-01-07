package com.bstek.viewmap.domain;

import java.io.Serializable;
import java.util.Date;

public class ViewMap implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mapId;
	private String mapCode;
	private String mapPath;
	private String nodeCode;
	private Date createDate;
	private Date updateDate;
	private String createUser;
	private String updateUser;
	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	public String getMapCode() {
		return mapCode;
	}
	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}
	public String getMapPath() {
		return mapPath;
	}
	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	

}
