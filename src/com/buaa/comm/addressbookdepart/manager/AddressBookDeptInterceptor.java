package com.buaa.comm.addressbookdepart.manager;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bstek.bdf2.core.orm.jdbc.JdbcDao;
import com.bstek.bdf2.importexcel.exception.InterceptorException;
import com.bstek.bdf2.importexcel.interceptor.ICellDataInterceptor;

@Service("bdf2.addressBookDeptInterceptor")
public class AddressBookDeptInterceptor extends JdbcDao implements ICellDataInterceptor {
	public Object execute(Object cellValue) throws Exception {
		if (cellValue != null) {
			String dept = cellValue.toString();
			List<Map<String, Object>> defaultDept = this.getJdbcTemplate().queryForList("select * from Comm_AddressBookDepart where Name ='" + dept + "'");
			if (defaultDept.size() == 0) {
				throw new InterceptorException("部门信息错误！");
			}
		}
		else
			throw new InterceptorException("部门不为空！");
		return cellValue;
	}
	public String getName(){
		return "部门校验";
	}
}
