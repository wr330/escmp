package com.bstek.bdf2.core.view.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bstek.bdf2.core.orm.jdbc.JdbcDao;
import com.bstek.bdf2.importexcel.exception.InterceptorException;
import com.bstek.bdf2.importexcel.interceptor.ICellDataInterceptor;

@Service("bdf2.requiredDeptInterceptor")
public class RequiredDeptInterceptor extends JdbcDao implements ICellDataInterceptor {
	public Object execute(Object cellValue) throws Exception {
		if (cellValue != null) {
			String dept = cellValue.toString();
			List<Map<String, Object>> defaultDept = this.getJdbcTemplate().queryForList("select * from BDF2_DEPT where NAME_ ='" + dept + "'");
			if (defaultDept.size() == 0) {
				throw new InterceptorException("部门信息错误！");
			}
		}
		return cellValue;
	}
	public String getName(){
		return "部门校验";
	}
}
