package com.buaa.comm.jobstatistics.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.comm.domain.Joballot;
import com.buaa.comm.domain.Jobconcern;
import com.buaa.comm.domain.Jobstatistics;

@Repository("jobstatisticsDao")
public class JobstatisticsDao extends HibernateBaseDao {
	@Resource
	private QueryUserData userService;
	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryJobstatistics(Page<Jobstatistics> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Jobstatistics.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String writingperson = (String) parameter.get("writingperson");
			if (StringUtils.isNotEmpty(writingperson)) {
				coreHql.append(" and a.writingperson = :wp ");
				args.put("wp", writingperson);
			}
        	String user = (String) parameter.get("user");
			if (StringUtils.isNotEmpty(user)) {
				coreHql.append(" and a.sectionChief = :sc ");
				args.put("sc", user);
			}
			String status = (String) parameter.get("status");
			if (StringUtils.isNotEmpty(status)) {
				Integer sta = Integer.parseInt(status);
				coreHql.append(" and a.workStatus >= :ss ");
				args.put("ss", sta);
			}
        }
		
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and "+ result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}

        
        String countHql = "select count(*) " + coreHql.toString();
        String hql = coreHql.toString();
        hql = userService.checkUser(hql)+ "order by workStatus asc, arrangementdate desc";
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Jobstatistics detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setOid(UUID.randomUUID().toString());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据修改
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Jobstatistics detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据删除
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Jobstatistics detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	public Collection<Jobstatistics> queryConcernedJob(){
		String username = ContextHolder.getLoginUserName();
	    String hql = "from " + Jobstatistics.class.getName()+" a where a.sourcepersonid = '" + username + "'";//任务来源人为本人
	    hql += " or a.oid in (select b.jobstatistics from " + Jobconcern.class.getName()+" b where b.personid = '" + username + "')";//本人已关注
		String sql =  "select count(*) from " + RoleMember.class.getName() + " where GRANTED_ = 1 and ROLE_ID_ in (select id from " + Role.class.getName() + " where NAME_ ='部长') and USERNAME_ = '" + username + "'";
	    int count = this.queryForInt(sql);//验证当前用户是否为部长
	    if(count>0)//部长默认显示任务来源人为总师的项目		   
	    	hql += " or a.sourcepersonid in(select username from " + RoleMember.class.getName() + " where GRANTED_ = 1 and ROLE_ID_ in (select id from " + Role.class.getName() + " where NAME_ ='总师'))";
	    sql =  "select count(*) from " + RoleMember.class.getName() + " where GRANTED_ = 1 and ROLE_ID_ in (select id from " + Role.class.getName() + " where NAME_ ='主任') and USERNAME_ = '" + username + "'";
	    count = this.queryForInt(sql);//验证当前用户是否为主任
	    if(count>0)//主任默认显示其专业室负责的项目		   
	    	hql += " or a.departid in(select deptId from " + UserDept.class.getName() + " where USERNAME_ =  '" + username + "')";
	    hql += " or a.oid in (select c.jobstatistics from " + Joballot.class.getName()+" c where c.personid = '" + username + "')";//默认显示本人参与的项目
	    hql += " order by a.arrangementdate desc";
	    return this.query(hql);
	}    
}