package com.bstek.bdf2.core.view.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.bstek.bdf2.importexcel.exception.InterceptorException;
import com.bstek.bdf2.importexcel.interceptor.ICellDataInterceptor;

@Service("bdf2.requiredIPInterceptor")
public class RequiredIPInterceptor implements ICellDataInterceptor {
	public Object execute(Object cellValue) throws Exception {
		if (cellValue != null) {
			String ip = cellValue.toString();
			String check = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
			Pattern p = Pattern.compile(check);
			Matcher matcher = p.matcher(ip);
			if (!matcher.matches()) {
				throw new InterceptorException("不是正确的IP地址！");
			}
		}
		return cellValue;
	}
	public String getName(){
		return "IP地址格式校验";
	}
}
