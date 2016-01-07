package com.bstek.web.service;

import java.util.Collection;
import java.util.List;
//import com.bstek.bdf.pagination.Pagination;
import com.bstek.dorado.data.provider.Page;
import com.bstek.newstree.domain.NewsComments;
import com.bstek.newstree.domain.NewsTree;


public interface WebNewsService {

	public NewsTree getCurrentNodeByCode(String code)throws Exception;

	public Collection<NewsTree> queryCategorysByCode(String code)throws Exception;

	public List<NewsTree> getMainNewsList()throws Exception;
	
	public List<NewsTree> getClassicNewsList()throws Exception;

	public List<NewsTree> getMainNrsGuideList()throws Exception;

	public List<NewsTree> queryNewsByCurrentCode(int index, int i, String currentCode)throws Exception;
	
	public void queryNewsByCode(Page<NewsTree> page, String Code) throws Exception;

	public List<NewsTree> getMenuList(String rootId)throws Exception;

	public NewsTree getCurrentNewsById(String parentId)throws Exception;

	public Collection<NewsTree> getCategoryList(String parentId)throws Exception;
	
	public Collection<NewsTree> getRssNewsList(String count)throws Exception;

	public Collection<NewsComments> queryCommentsByCode(String childCode)throws Exception;
	
	public void saveComments(String content, String nodeCode)throws Exception ;

}
