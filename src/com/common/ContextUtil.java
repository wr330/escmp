package com.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Criterion;
import com.bstek.dorado.data.provider.filter.SingleValueFilterCriterion;

/**
 * utils类，用于存放公共方法
 * 
 * @author andy
 * 
 */
public class ContextUtil {
	
	private final static String OPERATOR = "operator";
	private final static String VALUE = "value";

	private static final String EQ = "eq";
	private static final String NE = "ne";
	private static final String GT = "gt";
	private static final String LT = "lt";
	private static final String GE = "ge";
	private static final String LE = "le";
	private static final String LIKEEND = "likeEnd";
	private static final String LIKE = "like";
	private static final String LIKESTART = "likeStart";

	/**
	 * 根据criteria返回一个包含里面条件的SQL语句
	 * 
	 * @param criteria
	 *            参数
	 * @param args
	 *            保存参数,不能为null
	 * @param alias
	 *            别名
	 * @param isHibernate
	 *            是否hibernate模式，如果不是，需要配置字段与数据库中字段一致，调用buildFieldName方法
	 * @return
	 */
	public static String parseCriteria(Criteria criteria,
			List<Object> args, String alias, boolean isHibernate) {
		if (criteria == null || criteria.getCriterions().size() == 0) {
			return null;
		}
		List<Criterion> filterCriterions = criteria.getCriterions();
		int count = 0;
		StringBuffer sb = new StringBuffer();
		for (Criterion criterion : filterCriterions) {
			SingleValueFilterCriterion filterCriterion = (SingleValueFilterCriterion) criterion;
			Object value = filterCriterion.getValue();
			String property = filterCriterion.getProperty();
			String propertyName = property;
			if (!isHibernate) {
				propertyName = buildFieldName(property);
			}
			String operator = filterCriterion.getFilterOperator().name();

			if (count > 0)
				sb.append(" and ");

			if (StringUtils.hasText(alias))
				sb.append(alias + ".");

			if (LIKESTART.equals(operator)) {
				sb.append(propertyName + " like ? ");
				args.add(value + "%");
			}

			if (LIKE.equals(operator)) {
				sb.append(propertyName + " like ? ");
				args.add( "%" + value + "%");
			}
			if (LIKEEND.equals(operator)) {
				sb.append(propertyName + " like ? ");
				args.add("%" + value);
			}
			if (EQ.equals(operator)) {
				sb.append(propertyName + " = ? ");
				args.add(value);
			}
			if (NE.equals(operator)) {
				sb.append(propertyName + " <> ? ");
				args.add(value);
			}
			if (GE.equals(operator)) {
				sb.append(propertyName + " >= ? ");
				args.add( value);
			}
			if (LE.equals(operator)) {
				sb.append(propertyName + " <= ? ");
				args.add( value);
			}
			if (GT.equals(operator)) {
				sb.append(propertyName + " > ? ");
				args.add( value);
			}
			if (LT.equals(operator)) {
				sb.append(propertyName + " < ? ");
				args.add(value);
			}

			count++;
		}
		return sb.toString();
	}


	/**
	 * 如果不是hibernate则需要修改字段与数据库中保持一致， 默认该方面实现是修改userName与数据中user_name
	 * 如果不是这样形式请自己修改
	 * 
	 * @param name
	 * @return
	 */
	public static String buildFieldName(String name) {
		if (null == name)return name;
		StringBuffer sb = new StringBuffer();
		for (char ch : name.toCharArray()) {
			boolean upper = Character.isUpperCase(ch);
			//sb.append(Character.toUpperCase(ch));//转大写
			sb.append(ch);
			if (upper) {
				sb.append("_");
			}
		}
		//sb.append("_");//最后再加下划线
		return sb.toString();

	}
	
	/**
	 * <此方法在新版本中已经被上面的方法取缔>
	 * 将criteria 转换成一个键=String 值=HashMap的一个方法 后台根据键判断是不是HashMap根据直接拼写Sql
	 * 
	 * HashMap中是一个 运算符和一个值 operator=运算符 value=值 例如:"where userName"
	 * +operator+value;
	 * 
	 * @param criteria
	 * @param parameter
	 * @return
	 */
	public static Map<String, Object> transCriteriaToMap(Criteria criteria) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != criteria) {
			List<Criterion> filterCriterions = criteria.getCriterions();
			for (Criterion criterion : filterCriterions) {
				if (criterion instanceof SingleValueFilterCriterion) {
					SingleValueFilterCriterion filterCriterion = (SingleValueFilterCriterion) criterion;
					Object value = filterCriterion.getValue();
					String operator = filterCriterion.getFilterOperator()
							.name();
					String express = filterCriterion.getExpression();
					Map<String, Object> tempMap = new HashMap<String, Object>();

					if (express.indexOf("*") != -1)
						operator = " like ";
					if (express.indexOf("=") != -1)
						operator = " = ";
					if (express.indexOf(">") != -1)
						operator = " > ";
					if (express.indexOf("<") != -1)
						operator = " < ";
					if (express.indexOf("<>") != -1)
						operator = " <> ";
					if (express.indexOf(">=") != -1)
						operator = " >= ";
					if (express.indexOf("<=") != -1)
						operator = " <= ";

					tempMap.put(OPERATOR, operator);
					tempMap.put(VALUE, value);
					String property = filterCriterion.getProperty();
					map.put(property, tempMap);
				}
			}
		}
		return map;
	}

}