package com.bstek.web.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.newstree.domain.NewsTree;
import com.bstek.web.service.WebNewsService;
/**
 * This filter is used for denying news list using oscache
 * @author <a href="mailto:jude.li@bstek.com">Jude Li</a>
 *
 */
public class NrsFilter implements Filter {
	private WebNewsService newsService = null;

	public WebNewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(WebNewsService newsService) {
		this.newsService = newsService;
	}

	public void destroy() {
		
	}

	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain filterChain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) paramServletRequest;
	    newsService = ContextHolder.getBean("defaultWebNewsServiceImp");
	    String pathInfo = httpRequest.getPathInfo();
	    String[] codeArray = pathInfo.split("/");
	    String nodeCode = codeArray[codeArray.length-1];
		try {
			NewsTree result = newsService.getCurrentNodeByCode(nodeCode);
            if(null != result && result.getIsleaf().equals("false")){
            	paramServletRequest.setAttribute("__oscache_filtered__CacheFilter", Boolean.TRUE);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		filterChain.doFilter(paramServletRequest, paramServletResponse);
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
