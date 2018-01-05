package com.bstek.newstree.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.orm.jdbc.JdbcDao;
//import com.bstek.bdf.pagination.JdbcDaoSupportExt;
//import com.bstek.bdf.pagination.Pagination;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.newstree.domain.NewsTree;
/**
 * This class is a dao object of database access layer , manage 
 * database access and execute sql。
 * @author <a href="mailto:jude.li@bstek.com">Jude Li</a>
 *
 */
@Component
public class DefaultNewsServiceImp extends JdbcDao implements NewsService {
	private final static int OPERATION_TYPE_NEW = 1;
	private final static int OPERATION_TYPE_UPDATE = 2;
	 /**
     * 根据parent_id筛选符合条件的数据条目如果传入的条件为null则查询parent_id为空的
     * 所有条目。
     * @param params　传入的parent_id用于筛选
     * @return　collection 根据条件筛选出来的结果集
     */
	public Collection<NewsTree> queryNews(String params) {
		String sql = "";
		if (params == null) {
			sql = "select top 100 percent * from NRS_NEWS_TREE where PARENT_ID_ is null";
		} else {
			sql = "select * from NRS_NEWS_TREE where PARENT_ID_ = '" + params + "'";
		}

		sql += " ORDER BY ORDER_DATE_ DESC";
		return this.getJdbcTemplate().query(sql, new NewsRowMapper());
	}
	 /**
     * 根据传入的实体对象，往数据库插入一条数据
     * @param newsTree　用于插入数据库的实体对象
     */
	public void insertNews(NewsTree newsTree) throws Exception {
		setSysInfo(newsTree, OPERATION_TYPE_NEW);
		String sql = " INSERT INTO NRS_NEWS_TREE( " + " NODE_ID_, " + " NODE_TITLE_, " + " NODE_CONTENT_, "
				+ " PARENT_ID_, " + " CREATE_DATE_, " + " UPDATE_DATE_, " + " CREATE_USER_, " + " UPDATE_USER_, "
				+ " STATU_, " + " ISLEAF_, " + " ORDER_DATE_, " + " ICON_, " + " NODE_CODE_ ) " + " VALUES( " + " ?, "
				+ " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, " + " ?, "
				+ " ?" + ") ";
		this.getJdbcTemplate().update(sql, newsTree.getNodeId(), newsTree.getNodeTitle(), newsTree.getNodeContent(),
				newsTree.getParentId(), newsTree.getCreateDate(), newsTree.getUpdateDate(), newsTree.getCreateUser(),
				newsTree.getUpdateUser(), newsTree.getStatu(), newsTree.getIsleaf(), newsTree.getOrderDate(),
				newsTree.getIcon(), newsTree.getNodeCode());

	}
	 /**
     * 根据传入的实体对象，按照该实体对象的nodeId进行修改数据库对应的条目
     * @param newsTree　用于修改数据库的实体对象
     */
	public void modifyNews(NewsTree newsTree) throws Exception {
		setSysInfo(newsTree, OPERATION_TYPE_UPDATE);
		String sql = " UPDATE NRS_NEWS_TREE SET  " + " NODE_TITLE_ = ?, " + " NODE_CONTENT_ = ?, " + " PARENT_ID_ = ?, "
				+ " CREATE_DATE_ = ?, " + " UPDATE_DATE_ = ?, " + " CREATE_USER_ = ?, " + " UPDATE_USER_ = ?, "
				+ " STATU_ = ?, " + " ISLEAF_ = ?, " + " ORDER_DATE_ = ?, " + " ICON_ = ?, " + " NODE_CODE_ = ? "
				+ " WHERE node_id = ? ";
		this.getJdbcTemplate().update(sql, newsTree.getNodeTitle(), newsTree.getNodeContent(), newsTree.getParentId(),
				newsTree.getCreateDate(), newsTree.getUpdateDate(), newsTree.getCreateUser(), newsTree.getUpdateUser(),
				newsTree.getStatu(), newsTree.getIsleaf(), newsTree.getOrderDate(), newsTree.getIcon(),
				newsTree.getNodeCode(), newsTree.getNodeId());

	}
	 /**
     * 新增与修改时用于设置实体对象的创建者，创建时间，修改者，修改时间，及新增时
     * 主键的生成。
     * @param newsTree　用于设置这些属性的实体对象
     */
	private void setSysInfo(NewsTree newsTree, int type) {
		String userName = ContextHolder.getLoginUserName();
		switch (type) {
		// 新增
		case OPERATION_TYPE_NEW:
			newsTree.setNodeId(UUID.randomUUID().toString());
			newsTree.setCreateUser(userName);
			newsTree.setCreateDate(new Date());
			break;
		// 修改
		case OPERATION_TYPE_UPDATE:
			newsTree.setUpdateUser(userName);
			newsTree.setUpdateDate(new Date());
			break;
		}

	}
	 /**
     * 根据数据库中的主键进行删除一条数据
     * @param nodeId　用于删除的条目的id
     */
	public void deleteNews(String nodeId) throws Exception {
		String sql = "DELETE FROM  NRS_NEWS_TREE WHERE NODE_ID_ = '" + nodeId + "'";
		this.getJdbcTemplate().update(sql);

	}
	 /**
     * 根据parent_id对应数据库条目的node_code分页筛选符合条件的数据
     * 返回封装该结果集的分页对象
     * @param pageIndex　用于分页查询的页号
     * @param pageSize　用于分页查询的每页大小
     * @param currentCode　用于 筛选的上一级编码
     * @return pagination 返回封装成pagination对象的结果集
     */
	public Page<NewsTree> queryNewsByCurrentCode(int pageIndex, int pageSize, String currentCode)
			throws Exception {
		String sql = " SELECT top 100 percent " + " NODE_ID_, " + " NODE_TITLE_, " + " NODE_CONTENT_, " + " PARENT_ID_, "
				+ " CREATE_DATE_, " + " UPDATE_DATE_, " + " CREATE_USER_, " + " UPDATE_USER_, " + " STATU_, " + " ISLEAF_, "
				+ " ORDER_DATE_, " + " ICON_, " + " NODE_CODE_ " + " FROM " + " NRS_NEWS_TREE WHERE  statu = 'publish' ";
		String countSql = "SELECT count(*) FROM NRS_NEWS_TREE WHERE  STATU_ = 'publish' ";
		StringBuffer conditionSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.hasText(currentCode)) {
			conditionSql.append(" and PARENT_ID_ in (select NODE_ID_ from NRS_NEWS_TREE where NODE_CODE_ = ? )");
			params.add(currentCode);
		}
		String orderStr = " ORDER BY ORDER_DATE_ DESC";
		Page<NewsTree> page = new Page<NewsTree>(pageSize, pageIndex);
		try {
//			this.paginationQuery(sql + conditionSql.toString() + orderStr, countSql + conditionSql.toString(),
//					params.toArray(), page, new NewsRowMapper());
			this.pagingQuery(page, sql + conditionSql.toString() + orderStr, params.toArray(), new NewsRowMapper());
		} catch (Exception e) {
		}
		return page;
//		List<NewsTree> temp = this.getJdbcTemplate().query(sql + conditionSql.toString() + orderStr, new Object[] { currentCode }, new NewsRowMapper());
//		return temp;
	}
	 /**
     * 根据node_id筛选数据库中相应的唯一数据
     * @param parentId　用于筛选的node_id
     * @return newsTree 筛选返回的结果实体对象
     */
	public NewsTree getCurrentNewsById(String parentId) throws Exception {
		String sql = " SELECT " + " node_id_, " + " node_title_, " + " node_content_, " + " parent_id_, "
				+ " create_date_, " + " update_date_, " + " create_user_, " + " update_user_, " + " statu_, " + " isleaf_, "
				+ " order_date_, " + " icon_, " + " node_code_ " + " FROM " + " NRS_NEWS_TREE WHERE node_id_ = '"
				+ parentId + "'";
		return this.getJdbcTemplate().query(sql, new NewsRowMapper()).get(0);
	}
	 /**
     * 根据parent_id筛选数据库中该字段的类型为分类的结果集
     * @param parentId　用于筛选的parent_id
     * @return collection 筛选返回的结果集合
     */
	public Collection<NewsTree> getCategoryList(String parentId) throws Exception {
		if (null == parentId)
			throw new RuntimeException("父ID不能为空");
		String sql = "select top 100 percent * from NRS_NEWS_TREE where  ISLEAF_ = 'false' AND PARENT_ID_ = '" + parentId + "'"
				+ " ORDER BY ORDER_DATE_ DESC";
		return this.getJdbcTemplate().query(sql, new NewsRowMapper());
	}

	public String getCurrentKey(NewsTree newsTree) throws Exception {
		String key = "";
		String sql = "select * from nrs_news_tree where NODE_ID_ = ?";
		NewsTree resultNewsTree = newsTree;
		while ("0".equals(resultNewsTree.getNodeCode()) == false) {
			key = "/" + resultNewsTree.getNodeCode() + key;
			List<NewsTree> list = this.getJdbcTemplate().query(sql, new Object[] { resultNewsTree.getParentId() },
					new NewsRowMapper());
			if (null != list && list.size() > 0)
				resultNewsTree = list.get(0);
		}
		return key;
	}
	 /**
     * 根据parent_id筛选数据库中父节点该字段的类型为分类的结果集
     * @param nodeId　用于筛选的parent_id
     * @return collection 筛选返回的结果集合
     */
	public Collection<NewsTree> queryCategoryTree(String nodeId) throws Exception {
		String sql = "";
		if (nodeId == null) {
			sql = "select top 100 percent * from NRS_NEWS_TREE where PARENT_ID_ is null";
		} else {
			sql = "select top 100 percent * from NRS_NEWS_TREE where  ISLEAF_ = 'false' and  PARENT_ID_ = '" + nodeId + "'";
		}

		sql += "  ORDER BY ORDER_DATE_ DESC";
		return this.getJdbcTemplate().query(sql, new NewsRowMapper());
	}
	 /**
     * 根据parent_id筛选数据库中父节点该字段的类型为新闻的结果集
     * @param nodeId　用于筛选的parent_id
     * @return pagination 返回封装成pagination对象的结果集
     */
	public void queryPaginationNews(Page<NewsTree> page, String nodeId)
			throws Exception {
		StringBuffer sql = new StringBuffer("select top 100 percent * from NRS_NEWS_TREE WHERE ISLEAF_ = 'true' ");
		StringBuffer countSql = new StringBuffer("select count(*) from NRS_NEWS_TREE WHERE ISLEAF_ = 'true' ");
		List<Object> params = new ArrayList<Object>();
		if(nodeId != null){
			if(StringUtils.hasText((CharSequence) nodeId)){
				sql.append(" and PARENT_ID_ = ? ");
				countSql.append(" and PARENT_ID_ = ? ");
				params.add(nodeId);
			}
		}
		sql.append("  ORDER BY ORDER_DATE_ DESC");
//		Page<NewsTree> pagination = new Page<NewsTree>(page.getPageSize(), page.getPageNo());
//		this.paginationQuery(sql.toString(), countSql.toString(), params.toArray(), pagination, new NewsRowMapper());	
		this.pagingQuery(page, sql.toString(), params.toArray(), countSql.toString(), new NewsRowMapper());
		
//		return pagination;
	}
	 /**
     * 根据parent_id筛选数据库中父节点该字段及后台dataset或 datatype设置page属性时
     * 传过来的page对象进行分页的结果集
     * @param nodeId　用于筛选的parent_id
     * @return pagination 返回封装成pagination对象的结果集
     */
	public void queryPaginationNewsTree(Page<NewsTree> page, String nodeId) throws Exception {
		StringBuffer sql = new StringBuffer("select top 100 percent * from NRS_NEWS_TREE WHERE 1=1 and NODE_CODE_ not in('news1','news2')");
		StringBuffer countSql = new StringBuffer("select count(*) from NRS_NEWS_TREE WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if(nodeId != null){
			if(StringUtils.hasText((CharSequence) nodeId)){
				sql.append(" and PARENT_ID_ = ? ");
				countSql.append(" and PARENT_ID_ = ? ");
				params.add(nodeId);
			}
		}
		else{
			sql.append(" and PARENT_ID_ is null ");
			countSql.append(" and PARENT_ID_ is null ");
		}
		sql.append("  ORDER BY ORDER_DATE_ DESC");
//		Page<NewsTree> pagination = new Page<NewsTree>(page.getPageNo(), page.getPageSize());
//		this.paginationQuery(sql.toString(), countSql.toString(), params.toArray(), pagination, new NewsRowMapper());
		this.pagingQuery(page, sql.toString(), params.toArray(), countSql.toString(), new NewsRowMapper());
//		return pagination;
	}
	 /**
     * 根据node_code筛选数据库中符合该字段的数据
     * @param validateData　用于筛选的node_code
     * @return list 返回查询的结果集
     */
	public List<Map<String, Object>> queryExistNodeCode(String validateData) throws Exception {
		String sql = "select * from NRS_NEWS_TREE where NODE_CODE_ = ? ";
		return this.getJdbcTemplate().queryForList(sql,new Object[]{validateData});
	}
	 /**
     * 按照旧的parent_id筛选出来的数据更新此parent_id为新的parent_id
     * @param oldParent　用于筛选的parent_id
     * @param nodeId　用于更改的parent_id 
     */
	public void updateNewsTreeParentId(String oldParent, String nodeId) throws Exception {
		 String sql = "update NRS_NEWS_TREE set PARENT_ID_= ? where PARENT_ID_ = ? ";
        this.getJdbcTemplate().update(sql, new Object[] { nodeId, oldParent });
		
	}

	public List<NewsTree> getMenuList(String rootId) throws Exception {
		String sql = "select top 100 percent * from NRS_NEWS_TREE where PARENT_ID_ = ? ORDER BY ORDER_DATE_ DESC ";
		return this.getJdbcTemplate().query(sql,new Object[]{rootId}, new NewsRowMapper());
	}
	//由newsId获取taskId
	public int getTaskIdBynewsId(String newsId) throws Exception {
		String sql = " SELECT max(ID_) FROM UFLO_TASK WHERE BUSINESS_ID_ = '"+ newsId + "'";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	//设置Task表的description
	public void updateTask(String businessId, String description) throws Exception {
		 String sql = "update UFLO_TASK set DESCRIPTION_= ? where BUSINESS_ID_= ?;" +
		 		"update UFLO_HIS_TASK set DESCRIPTION_= ? where BUSINESS_ID_= ?";
       this.getJdbcTemplate().update(sql, new Object[] { description, businessId, description, businessId });
		
	}
	//删除稿件相关流程实例
	public void deleteUFLO(String businessId) throws Exception {
		 String sql = "delete from UFLO_TASK where BUSINESS_ID_= ?;" +
		 		"delete from UFLO_PROCESS_INSTANCE where BUSINESS_ID_= ?";
       this.getJdbcTemplate().update(sql, new Object[] { businessId,businessId });
		
	}
	//获取部内动态和新闻报道下的新闻
	@SuppressWarnings({ "deprecation" })
	public void queryNews(Page<NewsTree> page,String username,Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreSql = new StringBuffer(" from NRS_NEWS_TREE where PARENT_ID_ in('classic_news','hot_news') ");
		if (null != username) {//我的稿件
			coreSql.append("and CREATE_USER_=:username");
			args.put("username",username);
		}
		else{//稿件汇总
			coreSql.append("and STATU_='publish'");
		}
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, null);
			if (null != result) {
				String sql1 = result.getAssemblySql().toString();
				sql1 = sql1.replaceAll("nodeTitle ", "NODE_TITLE_ ");//防止查询报错
				sql1 = sql1.replaceAll("parentId ", "PARENT_ID_ ");
				sql1 = sql1.replaceAll("createUser ", "CREATE_USER_ ");
				sql1 = sql1.replaceAll("createDate ", "CREATE_DATE_ ");
				sql1 = sql1.replaceAll("orderDate ", "ORDER_DATE_ ");
				sql1 = sql1.replaceAll("nodeCode ", "NODE_CODE_ ");
				coreSql.append(" and " + sql1);
				Collection<String> keys = result.getValueMap().keySet();
				for(String key : keys){
					args.put(key.toLowerCase(), result.getValueMap().get(key));//key转换成小写,防止查询报错
				}
			}
		}
		String sql = "select * " + coreSql.toString() + " order by(case STATU_ when 'managerApproving' then 1 when 'ministerApproving' then 2 when 'publish' then 4 else 0 end),ORDER_DATE_ desc";
		NamedParameterJdbcTemplate namedjdbcTemplate=this.getNamedParameterJdbcTemplate();
		String querySql=this.getDialect(this.getJdbcTemplate()).getPaginationSql(sql, page.getPageNo(), page.getPageSize());
		String countSql="select count(*) " + coreSql.toString();
		countSql = countSql.toLowerCase();//转换成全小写
		page.setEntities((Collection<NewsTree>)namedjdbcTemplate.query(querySql,args,new NewsRowMapper()));
	    page.setEntityCount(namedjdbcTemplate.queryForInt(countSql,args));				
		//this.pagingQuery(page, sql, new NewsRowMapper(), args);
	}
}
