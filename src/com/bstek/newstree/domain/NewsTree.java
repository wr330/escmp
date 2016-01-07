package com.bstek.newstree.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * This is a pojo class.</br>
 * It's use for a news or category object.
 * @author <a href="mailto:jude.li@bstek.com">Jude Li</a>
 *
 */
public class NewsTree implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nodeId;
	private String nodeTitle;
	private String nodeContent;
	private String parentId;
	private Date createDate;
	private Date updateDate;
	private String createUser;
	private String updateUser;
	private String statu;
	private String isleaf;
	private Date orderDate;
	private String icon;
	private String nodeCode;


	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeTitle() {
		return nodeTitle;
	}
	//界面显示过长，将截取 zx
	public String getNodeTitle2() {
		if(nodeTitle.length()<38)
			return nodeTitle;
		char[] q = nodeTitle.toCharArray();
		double t=0;
		String temp = "";
		for(int i=0;i<q.length;i++){
			if((int)q[i] >= 0x4E00 && (int)q[i] <= 0x9FA5)
				t += 1.5526;
			else
				t += 1;
			if(t > 59)
				return temp + "...";
			temp += q[i];
		}
		return temp;
	}
	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}

	public String getNodeContent() {
		return nodeContent;
	}
	//获取第一张图片链接 zx
	public String getNodeContent2() {
		if(nodeContent == null)
			return null;
		int start = nodeContent.indexOf("src=");
		if(start ==-1)
			return null;
		String imgUrl = nodeContent.substring(start+5) ;
		int end = imgUrl.indexOf("\"");
		if(start ==-1)
			return null;
		imgUrl = imgUrl.substring(0,end );
		return imgUrl;
	}
	public void setNodeContent(String nodeContent) {
		this.nodeContent = nodeContent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	
	public String getOrderDate2() {
		String date = orderDate.toString();
		return date.substring(0, 10);
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

}
