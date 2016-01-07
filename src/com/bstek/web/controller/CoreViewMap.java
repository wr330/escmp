package com.bstek.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//import com.bstek.bdf.pagination.Pagination;
//import com.bstek.bdf.pagination.Pagination;
import com.bstek.datadictionary.domain.DataCode;
import com.bstek.datadictionary.service.DataCodeService;
import com.bstek.dorado.data.provider.Page;
import com.bstek.newstree.common.NrsConst;
import com.bstek.newstree.domain.NewsComments;
import com.bstek.newstree.domain.NewsTree;
import com.bstek.viewmap.domain.ViewMap;
import com.bstek.viewmap.service.ViewMapService;
import com.bstek.web.rss.NewsRssView;
import com.bstek.web.service.WebNewsService;
import com.google.common.collect.Maps;

/**
 * This class is a controller of spring mvc, it's a map of url and view
 * template. 
 * @author <a href="mailto:jude.li@bstek.com">Jude Li</a>
 *
 */
@Controller
public class CoreViewMap {
	private WebNewsService newsService;
	private ViewMapService viewMapService;
	private DataCodeService dataCodeService;
	/**
	 * 请求为rss的新闻列表
	 * @param count 得到新闻条目的数量
	 * @param model 数据模型
	 * @return 视图
	 * @throws Exception 
	 */
	@RequestMapping("/news/rss")
	public ModelAndView handleRss(
	@RequestParam(required = false, value = "count") String count,
	Model model,HttpServletRequest request) throws Exception {
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String basePath = base+ "/" + NrsConst.VELOCITY_ROOT;
	Map<String, Object> map = Maps.newHashMap();
	map.put("base", base);
	map.put("basePath", basePath);
	map.put("newsList", newsService.getRssNewsList(count));
	model.addAttribute("time", new Date().getTime());
	return new ModelAndView(NewsRssView.VIEW_NAME, map);
	}
	/**
	 * 处理请求中访问的url相对于当前项目下spring mvc 中的核心servlet  org.springframework.web.servlet.DispatcherServlet</br>
	 * 所映身的路径的的所有层级为第一级的路径  当  spring mvc 　配置为</br>
	 *    <servlet>
     *      <servlet-name>test</servlet-name>
     *      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     *      <load-on-startup>1</load-on-startup>
     *    </servlet>
     *    <servlet-mapping>
     *       <servlet-name>test</servlet-name>
     *       <url-pattern>/home/*</url-pattern>
     *    </servlet-mapping>
	 * 则当web project 的context root 为nrs所匹配的映射模式为　http:IP地址或域名:端口号/nrs/home/一层编码　 例如：http://192.168.1.2:8080/nrs/home/company
	 * @param code　路径编码对应数据库中该记录的编码及查询该编码对应的视图名
	 * @param model　模型对象用于存放视图中用于展现的数据。
	 * @param request　请求对象用于获取请求的参数。
	 * @return　view   返回一个velocity模板文件名用于页面展现。
	 * @throws Exception
	 */
	@RequestMapping(value = {"/{code}"}, method = RequestMethod.GET)
	public String findIndex(@PathVariable("code") String code, Model model, HttpServletRequest request)
			throws Exception {
		if(code.equals(NrsConst.ONLINE_PREVIEW_CODE)){
			String htmlStr = request.getParameter("htmlStr");
			model.addAttribute("dynamicsource", htmlStr);
			return  NrsConst.ONLINE_PREVIEW_EMPTY;
		}
		commonAction(model, request);
		ViewMap viewMap = viewMapService.queryViewMapByNodeCode(code);
		NewsTree parentNode = newsService.getCurrentNodeByCode(code);
		model.addAttribute("parentNode", parentNode);
		model.addAttribute("title",parentNode.getNodeTitle());
		if(code.equals("news1") || code.equals("news2"))
			code = "index";
		Collection<NewsTree> categorys = newsService.queryCategorysByCode(code);
		model.addAttribute("categorys", categorys);
		model.addAttribute("newsService",newsService);
		model.addAttribute("parentPath", code);
		Collection<DataCode> defaultForwardCol =  dataCodeService.findDataCodeByParentCode(NrsConst.DEFAULT_FORWARD);
	
		for (DataCode dataCode : defaultForwardCol) {
			if(code.equals(NrsConst.INDEX_CODE)){
				List<NewsTree> mainNewsList = newsService.getMainNewsList();
				List<NewsTree> classicNewsList = newsService.getClassicNewsList();
				List<NewsTree> mainNrsGuideList = newsService.getMainNrsGuideList();
				model.addAttribute("mainNewsList", mainNewsList);
				model.addAttribute("classicNewsList", classicNewsList);
				model.addAttribute("mainNrsGuideList", mainNrsGuideList);
			}
			if(code.equals(dataCode.getDataCode())){
				return dataCode.getDataSimpleName();
			}
		}
		return  getSkinsTemplate(NrsConst.DEFAULT_SKINS)+viewMap.getMapPath();
	}

	/**
	 * 处理请求中访问的url相对于当前项目下spring mvc 中的核心servlet  org.springframework.web.servlet.DispatcherServlet</br>
	 * 所映身的路径的的所有层级为第二级的路径  当  spring mvc 　配置为</br>
	 *    <servlet>
     *      <servlet-name>test</servlet-name>
     *      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     *      <load-on-startup>1</load-on-startup>
     *    </servlet>
     *    <servlet-mapping>
     *       <servlet-name>test</servlet-name>
     *       <url-pattern>/home/*</url-pattern>
     *    </servlet-mapping>
	 * 则当web project 的context root 为nrs所匹配的映射模式为　http:IP地址或域名:端口号/nrs/home/一层编码/二层编码　 例如：http://192.168.1.2:8080/nrs/home/company/sign_news
	 * @param parentCode　层级为第一层的编码用于查询对应的视图
	 * @param currentCode　层级为第二层的编码用于查询数据为表为该编码的数据或其对应的内嵌视图模板名
	 * @param model　　模型对象用于存放视图中用于展现的数据。
	 * @param request　请求对象用于获取请求的参数。
	 * @return　view  返回一个velocity模板文件名用于页面展现。
	 * @throws Exception
	 */
	@RequestMapping(value = "/{parentCode}/{currentCode}", method = RequestMethod.GET)
	public String findCompany(@PathVariable("parentCode") String parentCode,
			@PathVariable("currentCode") String currentCode, Model model, HttpServletRequest request) throws Exception {
		commonAction(model, request);
		NewsTree currentNode = newsService.getCurrentNodeByCode(currentCode);
		model.addAttribute("currentNode", currentNode);
		model.addAttribute("title",currentNode.getNodeTitle());
		NewsTree parentNode = newsService.getCurrentNodeByCode(parentCode);
		model.addAttribute("parentNode", parentNode);
		ViewMap viewMap = viewMapService.queryViewMapByNodeCode(parentCode);
		Collection<NewsTree> categorys = newsService.queryCategorysByCode(parentCode);
		model.addAttribute("categorys", categorys);
		model.addAttribute("newsService",newsService);
		model.addAttribute("parentPath", parentCode);
		if (currentNode.getIsleaf().equals("true")) {
			model.addAttribute("isList", false);
			ViewMap nestViewMap = viewMapService.queryViewMapByNodeCode(currentCode);
			if(null != nestViewMap && nestViewMap.getMapPath() != null){
			model.addAttribute("includeView", nestViewMap.getMapPath() + ".vm");
			}
			else{
				model.addAttribute("includeView", NrsConst.DEFAULT_CONTENT_TEMPLATE + ".vm");
			}
		} else {
			model.addAttribute("isList", true);
			paginationAction(model, request, currentCode);

		}
		return getSkinsTemplate(NrsConst.DEFAULT_SKINS)+viewMap.getMapPath();
	}

	/**
	 * 处理请求中访问的url相对于当前项目下spring mvc 中的核心servlet  org.springframework.web.servlet.DispatcherServlet</br>
	 * 所映身的路径的的所有层级为第三级的路径  当  spring mvc 　配置为</br>
	 *    <servlet>
     *      <servlet-name>test</servlet-name>
     *      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     *      <load-on-startup>1</load-on-startup>
     *    </servlet>
     *    <servlet-mapping>
     *       <servlet-name>test</servlet-name>
     *       <url-pattern>/home/*</url-pattern>
     *    </servlet-mapping>
	 * 则当web project 的context root 为nrs所匹配的映射模式为　http:IP地址或域名:端口号/nrs/home/一层编码/二层编码/三层编码　 例如：http://192.168.1.2:8080/nrs/home/company/sign_news/20126100345
	 * @param parentCode　层级为第一层的编码用于查询对应的视图
	 * @param currentCode　层级为第二层的编码用于面页中判断当前所在的二层位置
	 * @param childCode　层级为第三层的编码用于查询对应数据库中的内容作为视图展现
	 * @param model　模型对象用于存放视图中用于展现的数据
	 * @param request　请求对象用于获取请求的参数
	 * @return　view  返回一个velocity模板文件名用于页面展现
	 * @throws Exception
	 */
	@RequestMapping(value = "/{parentCode}/{currentCode}/{childCode}", method = RequestMethod.GET)
	public String findNext(@PathVariable("parentCode") String parentCode,
			@PathVariable("currentCode") String currentCode, @PathVariable("childCode") String childCode, Model model,
			HttpServletRequest request) throws Exception {
		commonAction(model, request);
		NewsTree currentNode = newsService.getCurrentNodeByCode(currentCode);
		NewsTree childNode = newsService.getCurrentNodeByCode(childCode);
		model.addAttribute("currentNode", currentNode);
		model.addAttribute("childNode", childNode);
		model.addAttribute("title",childNode.getNodeTitle());
		NewsTree parentNode = newsService.getCurrentNodeByCode(parentCode);
		model.addAttribute("parentNode", parentNode);
		ViewMap viewMap = viewMapService.queryViewMapByNodeCode(parentCode);
		Collection<NewsTree> categorys = newsService.queryCategorysByCode(parentCode);
		model.addAttribute("categorys", categorys);
		model.addAttribute("newsService",newsService);
		model.addAttribute("parentPath", parentCode);
		Collection<NewsComments> comments = newsService.queryCommentsByCode(childCode);
		model.addAttribute("commentsList", comments);
		model.addAttribute("commentsListCount", comments.size());
		model.addAttribute("childCode", childCode);
		if (childNode.getIsleaf().equals("true")) {
			model.addAttribute("isList", false);
		} else {
			model.addAttribute("isList", true);
			paginationAction(model, request, childCode);

		}

		return getSkinsTemplate(NrsConst.DEFAULT_SKINS)+viewMap.getMapPath();
	}

	/**
	 * 分页函数，处理请求中url中的页码及其所对应的类别编码从数据库查询出该页的数据集合</br>
	 * 把集合存放到模型对象model中
	 * @param model　模型对象用于存放分页查询的结果集及总页数。
	 * @param request　请求对像用于取出该对象中传过来的页码参数。
	 * @param currentCode　分类编码用于分页查询时所对应的是哪个分类下的新闻列表。
	 * @throws Exception
	 */
	private void paginationAction(Model model, HttpServletRequest request, String currentCode) throws Exception {
		int index = Integer.parseInt(StringUtils.hasText(request.getParameter("page")) ? request.getParameter("page")
				: "1");
//		Page<NewsTree> page =new Page<NewsTree>(2, index);
//		newsService.queryNewsByCode(page, currentCode);
		List<NewsTree> p = newsService.queryNewsByCurrentCode(index, 20, currentCode);
		Page<NewsTree> page = new Page<NewsTree>(10, index);
		page.setEntities(p);
		page.setEntityCount(p.size());
		model.addAttribute("pagination", page);
		int pageSize = 0;
		if (p.size() % 20 != 0) {
			pageSize = (p.size() / 20) + 1;
		} else {
			pageSize = p.size() / 20;
		}
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("newsList", getResult(p, index));
//		model.addAttribute("pagination", page);
//		int pageSize = 0;
//		if (page.getEntityCount() % 2 != 0) {
//			pageSize = (page.getEntityCount() / 2) + 1;
//		} else {
//			pageSize = page.getEntityCount() / 2;
//		}
//		model.addAttribute("pageSize", pageSize);
//		model.addAttribute("newsList", page.getEntities());
	}

	
	private Collection<NewsTree> getResult(List<NewsTree> page, int index)
	{
		List<NewsTree> result = new ArrayList<NewsTree>();	
		for(int i=20;i>=1;i--)
		{
		if(page.size() > 20*index-i)
		{
			NewsTree temp= page.get(20*index-i);
			result.add(temp);
		}
		}
		return result;		
	}
	/**
	 * 为模型对象model添加一些公共的数据如页面中使用绝对路径时须要求出的当前环境下的上下文路径
	 * @param model　模型对象model存放基本路径及资源路径等等
	 * @param request　请求对象用于获取当环境下的服务器的布署信息
	 */
	private void commonAction(Model model, HttpServletRequest request)throws Exception {
		String path = request.getContextPath();
		String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		String resourcesPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/" + NrsConst.RESOURCES_ROOT;
		String basePath = base+ "/" + NrsConst.VELOCITY_ROOT;
		model.addAttribute("base", base);
		model.addAttribute("basePath", basePath);
		model.addAttribute("resourcesPath", resourcesPath);
		List<NewsTree> menuList = newsService.getMenuList(NrsConst.ROOT_ID);
		model.addAttribute("menuList", menuList);
	}

	/**
	 * 前台页面用于分页查询新闻的函数
	 * @param pageIndex　用于查询数据的页号或第几页
	 * @param pageSize　用于分页时每页的大小或每页几条数据
	 * @param currentCode　分类编码用于分页查询时所对应的是哪个分类下的新闻列表
	 * @return　pagination 分页查询返回的封装了结果的分页对象
	 * @throws Exception
	 */
	public List<NewsTree> queryNewsByCurrentCode(int pageIndex, int pageSize, String currentCode)
			throws Exception {
		return newsService.queryNewsByCurrentCode(pageIndex, pageSize, currentCode);
	}

	/**
	 *  根据id筛选相应的数据
	 * @param parentId　用于筛选的id
	 * @return　newsTree 返回查询的结果对象
	 * @throws Exception
	 */
	public NewsTree getCurrentNewsById(String parentId) throws Exception {
		return newsService.getCurrentNewsById(parentId);
	}

	/**
	 * 根据字段为parent_id且类型为分类筛选相应的结果集
	 * @param parentId　用于筛选的id
	 * @return collection 返回查询的结果集
	 * @throws Exception
	 */
	public Collection<NewsTree> findCategoryListByParentId(String parentId) throws Exception {
		return newsService.getCategoryList(parentId);
	}

	public WebNewsService getNewsService() {
		return newsService;
	}
    /**
     *  自动注入NewsService接口的实现为bean id为defaultNewsServiceImp的实现
     * @param newsService　由spring容器负责实例化该对象
     */
	@Resource(name = "defaultWebNewsServiceImp")
	public void setNewsService(WebNewsService newsService) {
		this.newsService = newsService;
	}

	public ViewMapService getViewMapService() {
		return viewMapService;
	}
	/**
	 * 自动注入ViewMapService接口的实现为bean id为defaultViewMapServiceImp的实现
	 * @param viewMapService 由spring容器负责实例化该对象
	 */
	@Resource(name = "defaultViewMapServiceImp")
	public void setViewMapService(ViewMapService viewMapService) {
		this.viewMapService = viewMapService;
	}

	public DataCodeService getDataCodeService() {
		return dataCodeService;
	}
	/**
	 * 自动注入DataCodeService接口的实现为bean id为defaultDataCodeService的实现
	 * @param dataCodeServicee 由spring容器负责实例化该对象
	 */
    @Resource(name = "defaultDataCodeServiceImp" )
	public void setDataCodeService(DataCodeService dataCodeService) {
		this.dataCodeService = dataCodeService;
	}
    /**
     * 根据数据字典里设置的默认皮肤查找该数据
     * @return string 皮肤文件的前缀
     */
    private String getSkinsTemplate(String skinCode)throws Exception{
    	Collection<DataCode> defaultForwardCol =  dataCodeService.findDataCodeByParentCode(NrsConst.PAGE_SKINS_CODE);
    	String returnSkins = "";
    	for (DataCode dataCode : defaultForwardCol) {
			if(skinCode.equals(dataCode.getDataCode())){
				returnSkins = dataCode.getDataSimpleName();
				break;
			}
		}
       if(StringUtils.hasText(returnSkins)){
    	   return returnSkins;
       }else{
    	   throw new Exception("没有找到皮肤，请检查数据字典中皮肤设置");
       }
    	
    }

}
