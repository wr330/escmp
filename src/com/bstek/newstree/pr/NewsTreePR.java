package com.bstek.newstree.pr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.bstek.bdf2.core.context.ContextHolder;
//import com.bstek.bdf.pagination.Pagination;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.entity.FilterType;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.newstree.common.NrsConst;
import com.bstek.newstree.domain.NewsTree;
import com.bstek.newstree.service.NewsService;
import com.bstek.uflo.console.view.ProcessMaintain;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
/**
 * This NewsTreePR is a business object of business logic layer , manage 
 * business object handle and transaction handle
 *  
 * @author <a href="mailto:jude.li@bstek.com">Jude Li</a>
 *
 */
@Component
public class NewsTreePR {
	private NewsService newsService;
	/**
	 * 根据parent_id筛选符合的数据为后台界面树控件提供数据查询的递归调用
	 * @param nodeId　传入的parent_id用于筛选
	 * @return　collection 根据条件筛选出来的结果集
	 * @throws Exception
	 */
	@DataProvider
	public Collection<NewsTree> findNewsTree(String nodeId) throws Exception {
		return newsService.queryNews(nodeId);
	}
    /**
     * 根据parent_id且是分类筛选符合的数据为后台界面树控件提供数据查询的递归调用
     * @param nodeId　传入的parent_id用于筛选
     * @return　collection 根据条件筛选出来的结果集
     * @throws Exception
     */
	@DataProvider
	public Collection<NewsTree> findCategoryTree(String nodeId) throws Exception {
		return newsService.queryCategoryTree(nodeId);
	}
	/**
	 * 根据parent_id和后台分页控件传入的page对象对新闻进行分页显示，用于后台新闻管理中
	 * datatree与datagrid联动效果,从datatree中的当前节点获取parent_id和从datagrid中
	 * 的分页控件获取page对象通过智能方法适配传入该函数中调用
	 * @param page　分页对象通过datapilot控件邦定传入
	 * @param nodeId　当前节点id通过dataType中的动态EL表达式获取
	 * @throws Exception
	 */
	@DataProvider
	public void findPaginationNews(Page<NewsTree> page, String nodeId) throws Exception {
//		Page<NewsTree> pagination = 
		newsService.queryPaginationNews(page, nodeId);
//		page.setEntities(pagination.getEntities());
//		page.setEntityCount(pagination.getEntityCount());
	}
	/**
	 * 根据parent_id和后台dataType和dataset控件传入的page对象对datatree下的数据进行分页
	 * 递归调用
	 * @param page　dataType和dataset控件中设置pageSize则会封装一个page对象传入该函数
	 * @param parameter　当前节点id通过dataType中的动态EL表达式获取
	 * @throws Exception
	 */
	@DataProvider
	public void findPaginationNewsTree(Page<NewsTree> page,String parameter)throws Exception{
//		Page<NewsTree> pagination = 
		newsService.queryPaginationNewsTree(page, parameter);
//		page.setEntities(pagination.getEntities());
//		page.setEntityCount(pagination.getEntityCount());
	}
	/**
	 * datatree节点分页通过点击节点上的上页，下页时触发的ajax请求的后台的服务方法
	 * 方法的具体参数由ajaxAction控件中parameter值中的json数据的参数名称一一对应
	 * 传入。返回当前节点下的具体页号的数据集合
	 * @param nodeId　datatree的当前节点对象的属性nodeId值
	 * @param index　　ajaxAction传入的需要分页查询的页号
	 * @param pageSize　ajaxAction传入的需要分页查询的每页的尺寸大小
	 * @return　collection 表中parent_id为nodeId分页大小为pageSize页号为index的数据集合
	 * @throws Exception
	 */
	@Expose
	public Collection<NewsTree> findPaginationNewsTreeAjax(String nodeId,int index,int pageSize)throws Exception{
		Page<NewsTree> page =new Page<NewsTree>(pageSize, index);
//		Page<NewsTree> pagination = 
		newsService.queryPaginationNewsTree(page, nodeId);
		return page.getEntities();

	}
	/**
	 * datatree渲染节点时显示总页数向后台发出的ajax请求的后台服务方法
	 * 方法的具体参数由ajaxAction控件中parameter值中的json数据的参数名称一一对应传入。
	 * 返回当前节点下的总页数。
	 * @param nodeId　datatree的当前节点对象的属性nodeId值
	 * @param pageSize　ajaxAction传入的需要分页查询的每页的尺寸大小
	 * @return　count 该节点下分页显示总共须要多少页
	 * @throws Exception
	 */
    @Expose
    public int getNodeCountByParentId(String nodeId,int pageSize)throws Exception{
    	Page<NewsTree> page =new Page<NewsTree>(pageSize, 1);
//		Page<NewsTree> pagination = 
		newsService.queryPaginationNewsTree(page, nodeId);  	
    	return (page.getEntityCount()+pageSize-1)/pageSize;
    }
    /**
     * updateAction进行数据保存时后台的服务方法。传入的参数为updateAction下的updateItem中
     * 所邦定的dataSet对应的dataPath数据集合，然后根据这些数据的状态进行对应的
     * 持久化操作。
     * @param newsCollection　updateItem所邦定的dataSet对应的dataPath的数据集合
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	@DataResolver
	public void saveAllNews(Collection<NewsTree> newsCollection) throws Exception {
		Iterator<NewsTree> iter = EntityUtils.getIterator(newsCollection, FilterType.ALL);
		while (iter.hasNext()) {
			NewsTree newsTree = iter.next();
			EntityState entityState = EntityUtils.getState(newsTree);
			if (entityState.equals(EntityState.NEW)) {
				newsService.insertNews(newsTree);
				flushOscache(newsTree);
			} else if (entityState.equals(EntityState.MODIFIED)) {
				newsService.modifyNews(newsTree);
				flushOscache(newsTree);
			} else if (entityState.equals(EntityState.DELETED)) {
		        this.newsService.deleteNews(newsTree.getNodeId());
			    if(!newsTree.getStatu().equals("save"))
				    newsService.deleteUFLO(newsTree.getNodeId());//删除相应流程实例
				flushOscache(newsTree);
			}

		}
	}
	/**
	 * datatree控件进行拖拽时用于从根节点递归遍历整个树节点，根据节点的状态进行对应的持久化操作
	 * 递归调用的方法的初始newsTreeCol集合由updateAction邦定的dataSet对应的dataPath的数据集合
	 * @param newsTreeCol　进行递归调用的当前进行持久化操作的集合
	 * @param parentId　用于递归调用的上一级ID
	 * @return 
	 * @throws Exception
	 */
	 @DataResolver
	    public String doRebuildNewsTree(Collection<NewsTree> newsTreeCol, String parentId)
	      throws Exception
	    {
	      Iterator  iter = EntityUtils.getIterator(newsTreeCol, FilterType.ALL);
	      while (iter.hasNext()) {
	    	  NewsTree mr = (NewsTree)iter.next();
	        EntityState entityState = EntityUtils.getState(mr);      
	        if ((entityState.equals(EntityState.NEW)) && (StringUtils.hasText(mr.getNodeId()))) {
	          String oldParent = mr.getNodeId();
	          this.newsService.insertNews(mr);
	          this.newsService.updateNewsTreeParentId(oldParent, mr.getNodeId());
	          flushOscache(mr);
	        } else if (entityState.equals(EntityState.DELETED)) {
	          this.newsService.deleteNews(mr.getNodeId());
	          flushOscache(mr);
	        } else if (entityState.equals(EntityState.MODIFIED)) {
	          this.newsService.modifyNews(mr);
	          flushOscache(mr);
	        } else if (entityState.equals(EntityState.MOVED)) {
	          this.newsService.modifyNews(mr);
	          flushOscache(mr);
	        }
	       ArrayList<NewsTree> subCol =  EntityUtils.getValue(mr, "subNews");
	        if (subCol != null) {
	          if (subCol != null) {
	            parentId = mr.getNodeId();
	          }
	          doRebuildNewsTree(subCol, parentId);
	        }
	      }
	      return "";
	  }
    /**
     * datatree进行删除节点时通过ajax更新数据。nodeId为删除节点主键值，通过
     * ajaxAction的parameter传入
     * @param nodeId　要删除的节点的主键值
     * @throws Exception
     */
	@Expose
	public void deleteNewsById(String nodeId) throws Exception {
		NewsTree newsTree = newsService.getCurrentNewsById(nodeId);
		newsService.deleteNews(nodeId);
		flushOscache(newsTree);

	}
	/**
	 * ajaxValidator校验的服务方法参数通过ajaxAction的beforeExecute
	 * 获取当前实体的id和编辑器中输入的内容为参数传入。
	 * @param parameter　ajaxValidator需要校验的信息
	 * @return　返回空表示校验通过，返回一个字符串表示校验不通过时的信息
	 * @throws Exception
	 */
	@Expose
	public String checkNodeCode(HashMap<String, Object>  parameter)throws Exception{
		String entityId = (String) parameter.get("id");
		String validateData = (String) parameter.get("validateData");
		List<Map<String, Object>> resultsBiz = newsService.queryExistNodeCode(validateData);
		if(resultsBiz.size()>0){
			if(StringUtils.hasText(entityId)== false || entityId.equals(resultsBiz.get(0).get("node_id").toString()) == false)
			return validateData+"已经存在";
			else
			return null;
		}else
			return  null;
	}
	/**
	 * 当有新闻变动时根据该新闻的key值刷新oscache缓存,该函数在后台界面对
	 * 新闻进行增，删，改时调用。传入的参数为进行更新的新闻对象，根据该对象
	 * 的相关属性判断是否是新闻和得到所对应的key值
	 * @param newsTree　需要刷新缓存的新闻
	 * @throws Exception
	 */
	public void flushOscache(NewsTree newsTree) throws Exception {
	    String contextName = ContextHolder.getApplicationContext().getServletContext().getContextPath();
//		String currentKey = newsService.getCurrentKey(newsTree);
//		String parentKey = currentKey.substring(0, currentKey.length() - (newsTree.getNodeCode().length() + 1));
		if(newsTree.getIsleaf().equals("true")){
		 flushOscache( "/"+newsTree.getNodeCode()+ NrsConst.OSCACEH_KEY_SUFFIX);
		 flushOscachePattern(contextName + NrsConst.OSCACHE_FILTER_MAPPING +"/index" + NrsConst.OSCACEH_KEY_SUFFIX);
		}
	}
    /**
     * 刷新oscache中以某个字符串结尾的key值的缓存
     * @param urlStr　需要匹配刷新的key值
     */
	public void flushOscache(String urlStr) {
		ServletContext currentContext = ContextHolder.getApplicationContext().getServletContext();
		ServletCacheAdministrator admin = ServletCacheAdministrator.getInstance(currentContext);
		Cache cache = admin.getAppScopeCache(currentContext);
		cache.flushPatternEndWith(urlStr);
	}
	/**
	 * 刷新oscache中以某个字符串开始的key值的缓存
	 * @param urlStr　需要匹配刷新的key值
	 */
	@SuppressWarnings("deprecation")
	public void flushOscachePattern(String urlStr) {
		ServletContext currentContext = ContextHolder.getApplicationContext().getServletContext();
		ServletCacheAdministrator admin = ServletCacheAdministrator.getInstance(currentContext);
		Cache cache = admin.getAppScopeCache(currentContext);
		cache.flushPattern(urlStr);

	}
	/**
	 * 分页查询函数，根据parent_id和页号及分页的尺寸大小进行后台数据库查询返回
	 * 封装结果集的pagination对象。
	 * @param pageIndex　用于筛选的页号
	 * @param pageSize　用于分页的的尺寸大小
	 * @param parentId　用于筛选条目的parent_id值
	 * @return　pagination 筛选后的结果集分页对象
	 * @throws Exception
	 */
	public Page<NewsTree> queryNewsByParentId(int pageIndex, int pageSize, String parentId) throws Exception{ 
		return newsService.queryNewsByCurrentCode(pageIndex, pageSize, parentId);
	}

	/**
	 * 根据主键查询实体对象
	 * @param parentId　实体对象的主键id值
	 * @return　newsTree 查询得到的实体对象
	 * @throws Exception
	 */
	public NewsTree getCurrentNewsById(String parentId) throws Exception {
		return newsService.getCurrentNewsById(parentId);
	}

	/**
	 * 根据parent_id筛选类型为分类的实对对象集合
	 * @param parentId　用于筛选条目的parent_id值
	 * @return　collection 筛选后的结果集
	 * @throws Exception
	 */
	public Collection<NewsTree> findCategoryListByParentId(String parentId) throws Exception {
		return newsService.getCategoryList(parentId);
	}

	public NewsService getNewsService() {
		return newsService;
	}
	/**
	 * 自动注入NewsService接口的实现为bean id为defaultNewsServiceImp的实现
	 * @param newsService 由spring容器负责实例化该对象
	 */
	@Resource(name = "defaultNewsServiceImp")
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
    /**
     * 刷新oscache中所有的缓存用于后台手动刷新缓存
     */
	@Expose
	public void flushAllOscache(){
		ServletContext currentContext = ContextHolder.getApplicationContext().getServletContext();
		ServletCacheAdministrator admin = ServletCacheAdministrator.getInstance(currentContext);
		admin.flushAll();
	}
	@Expose
	public Boolean canDelete(){
		return ContextHolder.getLoginUser().isAdministrator();
	}
	//获取部内动态和新闻报道下的新闻
	@DataProvider
	public void queryNews(Page<NewsTree> page,String username,Criteria criteria)throws Exception{
		newsService.queryNews(page, username,criteria);
	}
}
