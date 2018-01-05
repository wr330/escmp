package com.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.AccessDeniedException;

import com.bstek.bdf2.core.CoreHibernateDao;
import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.cache.ApplicationCache;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.exception.NoneLoginException;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.security.SecurityUtils;
import com.bstek.bdf2.core.security.UserAuthentication;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;

public class MenuDao extends CoreHibernateDao implements InitializingBean{
	public static final String BEAN_ID="menuDao";
	public static final String URL_FOR_NAVI_CACHE_KEY="url_for_navi_cache_key_";
	
	private ApplicationCache applicationCache;
	
	@DataProvider
	@SuppressWarnings("unchecked")
	public Collection<Url> loadMeunUrls(String module) {
		IUser user = ContextHolder.getLoginUser();
		if (user == null) {
			throw new NoneLoginException("Please login first");
		}
		List<Url> cacheUrls=(List<Url>)this.applicationCache.getCacheObject(URL_FOR_NAVI_CACHE_KEY);
		List<Url> resultUrls=new ArrayList<Url>();
		for(Url url : cacheUrls){
			if(module.indexOf(url.getName()) != -1)
				resultUrls.add(url);
		}
		UserAuthentication authentication = new UserAuthentication(user);
		Collection<Url> result = new ArrayList<Url>();
		authorityCheck(resultUrls,authentication,result);
		return result;
	}
	
	private void authorityCheck(Collection<Url> urls,UserAuthentication authentication,Collection<Url> result){
		for (Url url : urls) {
			String targetUrl = url.getUrl();
			List<Url> children=url.getChildren();
			int childrenCount = 0;
			if(children!=null){
				childrenCount=children.size();
			}
			if (childrenCount==0 && StringUtils.isEmpty(targetUrl)) {
				continue;
			}
			if(StringUtils.isEmpty(targetUrl)){
				targetUrl = url.getName();				
			}
			try {
				SecurityUtils.checkUrl(authentication, targetUrl);
				Url newUrl=buildNewUrl(url);
				result.add(newUrl);
				if(children!=null){
					List<Url> childrenUrls=new ArrayList<Url>();
					newUrl.setChildren(childrenUrls);
					authorityCheck(children,authentication,childrenUrls);				
				}
			} catch (AccessDeniedException ex) {}
		}
	}
	//自定义我的办公桌
	@SuppressWarnings({ "unchecked", "deprecation" })
	@DataProvider
	public Collection<Url> loadMyMeunUrls() {
		IUser user = ContextHolder.getLoginUser();
		if (user == null) {
			throw new NoneLoginException("Please login first");
		}
		List<Url> cacheUrls=(List<Url>)this.applicationCache.getCacheObject(URL_FOR_NAVI_CACHE_KEY);
		UserAuthentication authentication = new UserAuthentication(user);
		Collection<Url> result = new ArrayList<Url>();
		authorityCheck2(cacheUrls,authentication,result);//获取有权限的菜单集合
		Session session = this.getSessionFactory().openSession();
		List<String> urls = null;
		try {
			String sql = "select URL_ as Url from SYS_MYDESK where USERNAME_='"+user.getUsername()+"'";
			Query query = session.createSQLQuery(sql).addScalar("Url",Hibernate.STRING);// 设置返回值类型，不然会报错
			urls = query.list();//获取已设置的办公桌URL
	    } finally {
		    session.flush();
		    session.close();
		}
		for(Url url:result){//已设置的菜单进行标记
			if(urls.isEmpty() || !urls.contains(url.getUrl()))
				url.setForNavigation(false);
		}
		return result;
	}
	
	private void authorityCheck2(Collection<Url> urls,UserAuthentication authentication,Collection<Url> result){
		for (Url url : urls) {
			String targetUrl = url.getUrl();
			List<Url> children=url.getChildren();
			int childrenCount = 0;
			if(children!=null){
				childrenCount=children.size();
			}
			if (childrenCount==0 && StringUtils.isEmpty(targetUrl)) {
				continue;
			}
			if(StringUtils.isEmpty(targetUrl)){
				targetUrl = url.getName();				
			}
			try {
				SecurityUtils.checkUrl(authentication, targetUrl);
				if(!StringUtils.isEmpty(url.getUrl())){
				    Url newUrl=buildNewUrl(url);
				    result.add(newUrl);
				}
				if(children!=null){
					authorityCheck2(children,authentication,result);				
				}
			} catch (AccessDeniedException ex) {}
		}
	}
	//获取我的办公桌
	@SuppressWarnings("unchecked")
	@Expose
	public List<Map<String, Object>> getMyDesk() throws Exception{
		IUser user = ContextHolder.getLoginUser();
		if (user == null) {
			throw new NoneLoginException("Please login first.");
		}
		Session session = this.getSessionFactory().openSession();
		try {
		    String sql = "select URL_ as url,NAME_ as name,'>images/home/MyDesk.png' image from SYS_MYDESK where USERNAME_='"+user.getUsername()+"' order by SEQUENCE_";
		    Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		    List<Map<String, Object>> list = query.list();
		    return list;
	    } finally {
		    session.flush();
		    session.close();
		}
	}
	//保存我的办公桌
	@Expose
	public void setMyDesk(Collection<Url> urls) throws Exception{
		IUser user = ContextHolder.getLoginUser();
		if (user == null) {
			throw new NoneLoginException("Please login first");
		}
		Session session = this.getSessionFactory().openSession();
		try {
			String sql = "delete from SYS_MYDESK where USERNAME_='"+user.getUsername()+"'";
            session.createSQLQuery(sql).executeUpdate();
            int i=1;
            for(Url url:urls ){
            	String oid = UUID.randomUUID().toString();
            	String sql0 = "insert into SYS_MYDESK('OID_','USERNAME_','URL_','NAME_','SEQUENCE_') values (''{0}'',''{1}'',''{2}'',''{3}'',{4})";
            	String sql1 = MessageFormat.format(sql0,oid,user.getUsername(),url.getUrl(),url.getName(),i++);
            	session.createSQLQuery(sql1).executeUpdate();
            }
	    } finally {
		    session.flush();
		    session.close();
		}
	}
	public ApplicationCache getApplicationCache() {
		return applicationCache;
	}

	public void setApplicationCache(ApplicationCache applicationCache) {
		this.applicationCache = applicationCache;
	}

	public void afterPropertiesSet() throws Exception {
		cacheNavigatorUrls();
	}
	
	public void cacheNavigatorUrls(){
		Session session=this.getSessionFactory().openSession();
		try{
			List<Url> urls = this.loadUrls(null,session);
			this.applicationCache.putCacheObject(URL_FOR_NAVI_CACHE_KEY, urls);			
		}finally{
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Url> loadUrls(String parentId,Session session){
		String hql="from "+Url.class.getName()+" u where u.forNavigation=:forNavigation";
		List<Url> urls = null;
		if (StringUtils.isNotEmpty(parentId)) {
			hql += " and u.parentId = :parentId order by u.order asc";
			urls = session.createQuery(hql).setBoolean("forNavigation", true).setString("parentId", parentId).list();
		} else {
			hql += " and u.parentId is null order by u.order asc";
			urls = session.createQuery(hql).setBoolean("forNavigation", true).list();
		}
		for(Url url:urls){
			url.setChildren(this.loadUrls(url.getId(),session));
		}
		return urls;
	}
	
	private Url buildNewUrl(Url oldUrl){
		Url url=new Url();
		url.setId(oldUrl.getId());
		url.setName(oldUrl.getName());
		url.setDesc(oldUrl.getDesc());
		url.setUrl(oldUrl.getUrl());
		url.setIcon(oldUrl.getIcon());
		url.setParentId(oldUrl.getParentId());
		url.setCompanyId(oldUrl.getCompanyId());
		return url;
	}
}