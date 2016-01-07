package com.bstek.newstree.common;
/**
 * some constants of NRS project 
 * @author <a href="mailto:jude.li@bstek.com">Jude Li</a>
 *
 */
public class NrsConst {
	// 数据库中树型 数据结构静态ID
	public final static String ROOT_ID = "0";
	public final static String INDEX_CODE = "index";
	//默认跳转数据字典在数据库中的编码
	public static final String DEFAULT_FORWARD = "default_forward";
	// 默认的内容使用模板
	public static final String DEFAULT_CONTENT_TEMPLATE = "product_content";
	// MVC VELOCITY 用于过滤的根路径
	public final static String VELOCITY_ROOT = "home/";
	// OSCACHE FILTER MAPPING
	public final static String OSCACHE_FILTER_MAPPING ="/home";
	// OSCACHE KEY SUFFIX
	public final static String OSCACEH_KEY_SUFFIX ="_GET_";
	
	// MVC VELOCIYT 用于定义resource的上层路径
	public final static String RESOURCES_ROOT = "client/";
	public final static String RESOURCES_ROOT_EDITOR = "/client";
	// 在线预览识别编码
	public final static String ONLINE_PREVIEW_CODE ="online_preview";
	// 在线预览空模板
	public final static String ONLINE_PREVIEW_EMPTY ="emptytemplate";
	// 页面皮肤编码
	public final static String PAGE_SKINS_CODE = "page_skins";
	// 默认皮肤模板编码
	public final static String DEFAULT_SKINS = "default_skin_template";
	
	

}
