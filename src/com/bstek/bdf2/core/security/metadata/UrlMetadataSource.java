package com.bstek.bdf2.core.security.metadata;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.cache.ApplicationCache;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.exception.NoneLoginException;
import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.orm.jdbc.JdbcDao;
import com.bstek.bdf2.core.security.attribute.AttributeType;
import com.bstek.bdf2.core.security.attribute.SecurityConfigAttribute;
import com.bstek.bdf2.core.service.IRoleService;
import com.bstek.bdf2.core.service.IUrlService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

public class UrlMetadataSource extends JdbcDao
  implements FilterInvocationSecurityMetadataSource, InitializingBean
{
  public static final String BEAN_ID = "bdf2.urlMetadataSource";
  private IRoleService roleService;
  private IUrlService urlService;
  private ApplicationCache applicationCache;
  private boolean useConservativeAuthorityStrategy;
  private String urlMetadataCacheKey = "url_metadata_";
  private Map<String, ConfigAttribute> anonymousUrlMetadata = new HashMap();
  private AntPathMatcher matcher = new AntPathMatcher();
  
  private static List<String> allUrl = new ArrayList<String>();//菜单URL集合
  private void loadAllUrl(){
	  String sql = "select NAME_,URL_ from BDF2_URL";
	  List<Map<String, Object>> maps = this.getJdbcTemplate().queryForList(sql);
	  for(int i=0;i<maps.size();i++){
		  String value = (String) maps.get(i).get("URL_");
		  if(value==null || value.isEmpty())
			  value = (String) maps.get(i).get("NAME_");
		  if(!allUrl.contains(value))
		      allUrl.add(value);
	  }
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    String url = null;
    if ((object instanceof FilterInvocation)) {
      HttpServletRequest request = ((FilterInvocation)object).getRequest();
      url = getRequestPath(request);
    } else {
      url = (String)object;
    }
    Collection safeUrlAttributes = getAnonymousUrlAttributes(url);
    if (safeUrlAttributes != null) {
      return safeUrlAttributes;
    }
    IUser loginUser = ContextHolder.getLoginUser();
    if (loginUser == null) {
      throw new NoneLoginException("Please login first");
    }
    String orgCompanyId = loginUser.getCompanyId();
    Assert.hasText(orgCompanyId, "current login user[" + ContextHolder.getLoginUser().getUsername() + "] is not specified company ID");
    Map metaData = loadMetaData();
    Map map = null;
    String[] companyIds = orgCompanyId.split(",");
    for (String companyId : companyIds) {
      map = (Map)metaData.get(companyId);
      if (map != null) break;
    }
    if (map == null) {
      if ((!this.useConservativeAuthorityStrategy) || (loginUser.isAdministrator())) {
        return CollectionUtils.EMPTY_COLLECTION;
      }
      throw new AccessDeniedException("Access denied");
    }
    
    if (!map.containsKey(url)) {
      if(url.startsWith("/"))
    	  url = url.substring(1, url.length());
      if (!map.containsKey(url)) {
        if ((!this.useConservativeAuthorityStrategy) || (loginUser.isAdministrator())) {
            if(allUrl.isEmpty())
            	loadAllUrl();
        	if(!loginUser.isAdministrator() && allUrl.contains(url))//属于菜单管理的未分配的url，不予授权
        	   throw new AccessDeniedException("Access denied");
            return CollectionUtils.EMPTY_COLLECTION;
        }
        throw new AccessDeniedException("Access denied");
      }

      List attributes = (List)map.get(url);
      if (attributes.size() > 0) {
        return attributes;
      }
      if ((!this.useConservativeAuthorityStrategy) || (loginUser.isAdministrator())) {
        return attributes;
      }
      throw new AccessDeniedException("Access denied");
    }

    List attributes = (List)map.get(url);
    if (attributes.size() > 0) {
      return attributes;
    }
    if ((!this.useConservativeAuthorityStrategy) || (loginUser.isAdministrator())) {
      return attributes;
    }
    throw new AccessDeniedException("Access denied");
  }

  private Collection<ConfigAttribute> getAnonymousUrlAttributes(String url)
  {
    Collection attributes = null;
    for (String patternUrl : this.anonymousUrlMetadata.keySet()) {
      if (this.matcher.match(patternUrl, url)) {
        attributes = new ArrayList();
        attributes.add(this.anonymousUrlMetadata.get(patternUrl));
        break;
      }
    }
    return attributes;
  }

  public Collection<ConfigAttribute> getAllConfigAttributes()
  {
    return CollectionUtils.EMPTY_COLLECTION;
  }

  public boolean supports(Class<?> clazz) {
    return FilterInvocation.class.isAssignableFrom(clazz);
  }

  private Map<String, Map<String, List<ConfigAttribute>>> loadMetaData()
  {
    return (Map)this.applicationCache.getCacheObject(this.urlMetadataCacheKey);
  }

  public void initUrlMetaData() {
    Map metaData = new HashMap();
    for (Iterator i$ = this.roleService.loadAllRoles().iterator(); i$.hasNext(); ) {Role role = (Role)i$.next();
      role.setRoleMembers(this.roleService.loadRoleMemberByRoleId(role.getId()));
      role.setUrls(this.urlService.loadUrlsByRoleId(role.getId()));
      String companyId = role.getCompanyId();
      Map mapAttribute = (Map)metaData.get(companyId);
      if (mapAttribute == null) {
        mapAttribute = new HashMap();
        metaData.put(companyId, mapAttribute);
      }
      for (Url url : role.getUrls()) {
        String targetUrl = url.getUrl();
        if (StringUtils.isEmpty(targetUrl)) {
          targetUrl = url.getName();
        }
        if (!StringUtils.isEmpty(targetUrl))
        {
          targetUrl = processUrl(targetUrl);
          List attributes = (List)mapAttribute.get(targetUrl);
          if (attributes == null) {
            attributes = new ArrayList();
            mapAttribute.put(targetUrl, attributes);
          }
          buildConfigAttributes(role, attributes);
        }
      }
    }
    Role role;
    Map mapAttribute;
    this.applicationCache.putCacheObject(this.urlMetadataCacheKey, metaData);
  }

  private String getRequestPath(HttpServletRequest request) {
    String url = request.getServletPath();
    if (request.getPathInfo() != null) {
      url = url + request.getPathInfo();
    }
    return url;
  }

  private void buildConfigAttributes(Role role, List<ConfigAttribute> list) {
    for (RoleMember member : role.getRoleMembers()) {
      SecurityConfigAttribute attribute = null;
      if (member.getUser() != null) {
        attribute = new SecurityConfigAttribute(AttributeType.user, member.isGranted(), role.getCompanyId());
        attribute.setMember(member.getUser());
      }
      if (member.getDept() != null) {
        attribute = new SecurityConfigAttribute(AttributeType.dept, member.isGranted(), role.getCompanyId());
        attribute.setMember(member.getDept());
      }
      if (member.getPosition() != null) {
        attribute = new SecurityConfigAttribute(AttributeType.position, member.isGranted(), role.getCompanyId());
        attribute.setMember(member.getPosition());
      }
      if (member.getGroup() != null) {
        attribute = new SecurityConfigAttribute(AttributeType.group, member.isGranted(), role.getCompanyId());
        attribute.setMember(member.getGroup());
      }
      list.add(attribute);
    }
  }

  private String processUrl(String targetUrl) {
    targetUrl = targetUrl.trim();
    return targetUrl;
  }

  public void afterPropertiesSet() throws Exception {
    initUrlMetaData();
    Collection safeUrls = getApplicationContext().getBeansOfType(AnonymousUrl.class).values();
    buildSafeUrlConfigAttributes(safeUrls);
  }

  private void buildSafeUrlConfigAttributes(Collection<AnonymousUrl> safeUrls) {
    for (AnonymousUrl url : safeUrls) {
      String pattern = url.getUrlPattern();
      SecurityConfig attribute = new SecurityConfig("IS_AUTHENTICATED_ANONYMOUSLY");
      this.anonymousUrlMetadata.put(pattern, attribute);
    }
  }

  public void setRoleService(IRoleService roleService)
  {
    this.roleService = roleService;
  }
  public void setUrlService(IUrlService urlService) {
    this.urlService = urlService;
  }
  public void setApplicationCache(ApplicationCache applicationCache) {
    this.applicationCache = applicationCache;
  }

  public boolean isUseConservativeAuthorityStrategy() {
    return this.useConservativeAuthorityStrategy;
  }

  public void setUseConservativeAuthorityStrategy(boolean useConservativeAuthorityStrategy)
  {
    this.useConservativeAuthorityStrategy = useConservativeAuthorityStrategy;
  }
}