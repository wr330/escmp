package com.bstek.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

//import com.bstek.bdf.pagination.JdbcDaoSupportExt;
//import com.bstek.bdf.pagination.Pagination;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.orm.jdbc.JdbcDao;
import com.bstek.dorado.data.provider.Page;
import com.bstek.newstree.domain.NewsComments;
import com.bstek.newstree.domain.NewsTree;
import com.bstek.newstree.service.NewsCommentsRowMapper;
import com.bstek.newstree.service.NewsRowMapper;

@Component
public class DefaultWebNewsServiceImp extends JdbcDao implements WebNewsService {

	 /**
     * 根据node_code筛选数据库中该字段的实体对象
     * @param code　用于筛选的node_code
     * @return newsTree 筛选返回的结果实体对象
     */
	public NewsTree getCurrentNodeByCode(String currentCode) throws Exception {
		String sql = "select * from nrs_news_tree where node_code = ?";
		List<NewsTree> list = this.getJdbcTemplate().query(sql, new Object[] { currentCode }, new NewsRowMapper());
		if (null != list && list.size() > 0)
			return list.get(0);
		return null;
	}

	 /**
     * 根据node_code筛选数据库中父节点该字段的类型为分类的结果集
     * @param code　用于筛选的node_code
     * @return collection 筛选返回的结果集合
     */
	public Collection<NewsTree> queryCategorysByCode(String code) throws Exception {
		String sql = "select top 100 percent * from nrs_news_tree where parent_id in (select node_id from nrs_news_tree where node_code = ? ) ORDER BY order_date DESC ";
		return this.getJdbcTemplate().query(sql, new Object[] { code }, new NewsRowMapper());
	}

	 /**
     * 查询node_code为'sign_news'(签约新闻)分类下的的按照时间倒序排 序的前10条新闻记录
     * @return list 返回查询的结果集
     */
	public List<NewsTree> getMainNewsList() throws Exception {
		String sql = "select top 10 * from nrs_news_tree where parent_id in (select node_id from nrs_news_tree where node_code = 'hot_news') " +
				" and isleaf = 'true' and statu = 'publish' ORDER BY order_date DESC";
		return this.getJdbcTemplate().query(sql, new NewsRowMapper());
	}
	public List<NewsTree> getClassicNewsList() throws Exception {
		String sql = "select top 10 * from nrs_news_tree where parent_id in (select node_id from nrs_news_tree where node_code = 'classic_news') " +
				" and isleaf = 'true' and statu = 'publish' ORDER BY order_date DESC";
		return this.getJdbcTemplate().query(sql, new NewsRowMapper());
	}
	 /**
     * 查询node_code为'nrs'(新闻发布系统简介)分类下的的按照时间倒序排 序的前3条新闻记录
     * @return list 返回查询的结果集
     */
	public List<NewsTree> getMainNrsGuideList() throws Exception {
		String sql = "select top 3 * from nrs_news_tree where parent_id in (select node_id from nrs_news_tree where node_code = 'nrs') " +
				" and isleaf = 'true' and statu = 'publish' ORDER BY order_date DESC";
		return this.getJdbcTemplate().query(sql, new NewsRowMapper());
	}

	 /**
     * 根据parent_id对应数据库条目的node_code分页筛选符合条件的数据
     * 返回封装该结果集的分页对象
     * @param pageIndex　用于分页查询的页号
     * @param pageSize　用于分页查询的每页大小
     * @param currentCode　用于 筛选的上一级编码
     * @return pagination 返回封装成pagination对象的结果集
     */
	public List<NewsTree> queryNewsByCurrentCode(int pageIndex, int pageSize, String currentCode)
			throws Exception {
		String sql = " SELECT top 100 percent" + " node_id, " + " node_title, " + " node_content, " + " parent_id, "
				+ " create_date, " + " update_date, " + " create_user, " + " update_user, " + " statu, " + " isleaf, "
				+ " order_date, " + " icon, " + " node_code " + " FROM " + " NRS_NEWS_TREE WHERE  statu = 'publish' ";
		String countSql = "SELECT count(*) FROM NRS_NEWS_TREE WHERE  statu = 'publish' ";
		StringBuffer conditionSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.hasText(currentCode)) {
			conditionSql.append(" and parent_id in (select node_id from NRS_NEWS_TREE where node_code = ? )");
			params.add(currentCode);
		}
		String orderStr = " ORDER BY order_date DESC";
//        Page<NewsTree> page = new Page<NewsTree>(pageSize, pageIndex);
//		Page<NewsTree> page = new Page<NewsTree>(pageSize, pageIndex);
//		this.pagingQuery(page, sql + conditionSql.toString(), params.toArray(), countSql + conditionSql.toString(), new NewsRowMapper());
//		try {
//			this.paginationQuery(sql + conditionSql.toString() + orderStr, countSql + conditionSql.toString(),
//					params.toArray(), page, new NewsRowMapper());
//		this.pagingQuery(page, sql + conditionSql.toString() + orderStr, params.toArray(), countSql + conditionSql.toString(), new NewsRowMapper());
//		} catch (Exception e) {
//	}
//		this.pagingQuery(page, sql);
		List<NewsTree> temp = this.getJdbcTemplate().query(sql + conditionSql.toString() + orderStr, new Object[] { currentCode }, new NewsRowMapper());
//		page.setEntities(temp);
//		page.setEntityCount(temp.size());
		return temp;
	}

	public void queryNewsByCode(Page<NewsTree> page, String Code)
			throws Exception {
		String sql = " SELECT top 100 percent" + " node_id, " + " node_title, " + " node_content, " + " parent_id, "
				+ " create_date, " + " update_date, " + " create_user, " + " update_user, " + " statu, " + " isleaf, "
				+ " order_date, " + " icon, " + " node_code " + " FROM " + " NRS_NEWS_TREE WHERE  statu = 'publish' ";
		String countSql = "SELECT count(*) FROM NRS_NEWS_TREE WHERE  statu = 'publish' ";
		StringBuffer conditionSql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.hasText(Code)) {
			conditionSql.append(" and parent_id in (select node_id from NRS_NEWS_TREE where node_code = ? )");
			params.add(Code);
		}
		String orderStr = " ORDER BY order_date DESC";
		try {
			this.pagingQuery(page, sql + conditionSql.toString() + orderStr, params.toArray(), countSql + conditionSql.toString(), new NewsRowMapper());
	} catch (Exception e) {
}
//		this.pagingQuery(page, sql + conditionSql.toString() + orderStr, params.toArray(), countSql + conditionSql.toString(), new NewsRowMapper());
	}
	
	public List<NewsTree> getMenuList(String rootId) throws Exception {
		String sql = "select top 100 percent * from nrs_news_tree where parent_id = ?  and node_code not in('news1','news2') ORDER BY order_date DESC ";
		return this.getJdbcTemplate().query(sql,new Object[]{rootId}, new NewsRowMapper());
	}

	 /**
     * 根据node_id筛选数据库中相应的唯一数据
     * @param parentId　用于筛选的node_id
     * @return newsTree 筛选返回的结果实体对象
     */
	public NewsTree getCurrentNewsById(String parentId) throws Exception {
		String sql = " SELECT " + " node_id, " + " node_title, " + " node_content, " + " parent_id, "
				+ " create_date, " + " update_date, " + " create_user, " + " update_user, " + " statu, " + " isleaf, "
				+ " order_date, " + " icon, " + " node_code " + " FROM " + " NRS_NEWS_TREE WHERE node_id = '"
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
		String sql = "select top 100 percent * from NRS_NEWS_TREE where  isleaf = 'false' AND parent_id = '" + parentId + "'"
				+ " ORDER BY order_date DESC";
		return this.getJdbcTemplate().query(sql, new NewsRowMapper());
	}

	@Override
	public Collection<NewsTree> getRssNewsList(String count) throws Exception{
		if(count == null){
			String sql = "select top 100 percent * from nrs_news_tree where parent_id in (select node_id from nrs_news_tree where node_code = 'hot_news') " +
					" and isleaf = 'true' and statu = 'publish' ORDER BY order_date DESC ";
			return this.getJdbcTemplate().query(sql, new NewsRowMapper());
		}else{
			String sql = "select top 0 * from nrs_news_tree where parent_id in (select node_id from nrs_news_tree where node_code = 'hot_news') " +
					" and isleaf = 'true' and statu = 'publish' ORDER BY order_date DESC"+count;
			return this.getJdbcTemplate().query(sql, new NewsRowMapper());
		}
		
	}

	@Override
	public Collection<NewsComments> queryCommentsByCode(String childCode) throws Exception{
		// TODO Auto-generated method stub
		String sql = "select top 100 percent * from nrs_comments where  NodeCode = '" + childCode + "'"
				+ " ORDER BY Date";
		return this.getJdbcTemplate().query(sql, new NewsCommentsRowMapper());
	}
	public void saveComments(String content, String nodeCode) throws Exception{
		String sql = "insert into nrs_comments(Id,Contents,UserName,Date,NodeCode) VALUES(?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, UUID.randomUUID().toString(), content, ContextHolder.getLoginUserName(),  new Date(), nodeCode);
	}
}
