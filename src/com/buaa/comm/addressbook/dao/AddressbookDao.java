package com.buaa.comm.addressbook.dao;

import java.util.ArrayList;
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

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.bdf2.core.view.user.QueryUserData;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.buaa.comm.addressbookdepart.dao.AddressbookdepartDao;
import com.buaa.comm.domain.Addressbook;
import com.buaa.comm.domain.Addressbookdepart;

@Repository("addressbookDao")
public class AddressbookDao extends HibernateBaseDao {
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
	public AddressbookdepartDao addressbookdepartDao;
	public void queryAddressbook(Page<Addressbook> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Addressbook.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
	String addressbookdepart = (String)parameter.get("addressbookdepart");
	//String parentnode = (String)parameter.get("parentnode");
	
	String hql2="from "+Addressbookdepart.class.getName()+" u where 1=1";
	Map<String,Object> map=new HashMap<String,Object>();
	map.put("parentnode",addressbookdepart);
	hql2+=" and u.parentnode=:parentnode ";
	
	List<Addressbookdepart> bookdepart=this.query(hql2,map);
	int a =bookdepart.size();
	List<String> oids = new ArrayList<String>();
	oids.add(addressbookdepart);
	if(StringUtils.isNotEmpty( addressbookdepart )&&a==0){
		coreHql.append(" and a.addressbookdepart.oid like :oid ");
		args.put("oid","%" + addressbookdepart + "%");
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and "+ result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}

        
        String countHql = "select count(*) " + coreHql.toString();
         String hql = coreHql.toString();
		this.pagingQuery(page, hql, countHql, args);
	}
	else{
		oids=getAllOid(bookdepart,oids);
		String aa = "'";
		for(String id:oids)//拼接HQL语句实现in 查询
			aa += id + "','";
		coreHql.append(" and a.addressbookdepart.oid in (" + aa.substring(0, aa.length()-2) + ")");
			
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and "+ result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}

        
        String countHql = "select count(*) " + coreHql.toString();
        String hql = coreHql.toString();
        hql = userService.checkUser(hql);
		this.pagingQuery(page, hql, countHql, args);
	}
           }
		
		
	}
	/**
	 * 递归获取节点下所有子节点的Oid
	 * @param bookdepart
	 * @param oids
	 * @return
	 */
	public List<String> getAllOid(List<Addressbookdepart> bookdepart,List<String> oids){
		int a =bookdepart.size();
		String hql="from "+Addressbookdepart.class.getName()+" u where 1=1";
		Map<String,Object> map=new HashMap<String,Object>();
		if(a!=0){
		for(int i=0;i<a;i++){
		String bookdepartid=bookdepart.get(i).getOid();
		oids.add(bookdepartid);
		map.put("parentnode",bookdepartid);
		hql+=" and u.parentnode=:parentnode ";
		List<Addressbookdepart>bookdepartnew=this.query(hql,map);
		 getAllOid(bookdepartnew,oids);
		}
		}
		return oids;
	}
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Addressbook detail) throws Exception {
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
	public void updateData(Addressbook detail) throws Exception {
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
	public void deleteData(Addressbook detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}