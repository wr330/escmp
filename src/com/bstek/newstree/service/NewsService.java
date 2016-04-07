package com.bstek.newstree.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

//import com.bstek.bdf.pagination.Pagination;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.newstree.domain.NewsTree;

public interface NewsService {
	public Collection<NewsTree> queryNews(String params) throws Exception;

	public void insertNews(NewsTree newsTree) throws Exception;

	public void modifyNews(NewsTree newsTree) throws Exception;

	public void deleteNews(String nodeId) throws Exception;

	public Page<NewsTree> queryNewsByCurrentCode(int pageIndex, int pageSize, String currentCode)
			throws Exception;

	public NewsTree getCurrentNewsById(String parentId) throws Exception;

	public Collection<NewsTree> getCategoryList(String parentId) throws Exception;

	public String getCurrentKey(NewsTree newsTree)throws Exception;

	public Collection<NewsTree> queryCategoryTree(String nodeId)throws Exception;

	public void queryPaginationNews(Page<NewsTree> page, String nodeId)throws Exception;

	public void queryPaginationNewsTree(Page<NewsTree> page, String nodeId)throws Exception;

	public List<Map<String, Object>> queryExistNodeCode(String validateData)throws Exception;

	public void updateNewsTreeParentId(String oldParent, String nodeId)throws Exception;
	
	public int getTaskIdBynewsId(String newsId) throws Exception;
	
	public void updateTask(String taskId, String description) throws Exception;
	
	public void deleteUFLO(String businessId) throws Exception;
	
	public void queryNews(Page<NewsTree> page,String username,Criteria criteria) throws Exception;
}
