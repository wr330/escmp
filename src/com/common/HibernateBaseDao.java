package com.common;

import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

/**
 * HIBERNATE数据持久化继承该类，用户根据自己使用的BDF版本调继承不同的父类。
 * 用户也可以自己定义实现方法
 * 
 *
 */
@Repository("hibernateBaseDao")
public class HibernateBaseDao extends HibernateDao {

}
