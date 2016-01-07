package com.bstek.bdf2.core.view.user;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.bdf2.export.interceptor.IDataInterceptor;
@Component
public class UserReportDataInterceptor extends HibernateDao implements IDataInterceptor {
    public String getName() {
        return "userReportDataInterceptor";
    }
    public String getDesc() {
        return "服务端表格数据拦截自定义";
    }
    public void interceptGridData(List<Map<String, Object>> list)
            throws Exception {

	    Session session = this.getSessionFactory().openSession();
        for (Map<String, Object> user : list) {
            if (user.get("administrator") != null) {
                user.put("administrator",((Boolean) user.get("administrator")) == true ? "是": "否");
            }
            if (user.get("enabled") != null) {
                user.put("enabled",((Boolean) user.get("enabled")) == true ? "是" : "否");
            }
            if (user.get("leagueMember") != null) {
                user.put("leagueMember",((Boolean) user.get("leagueMember")) == true ? "是": "否");
            }
            if (user.get("partyMember") != null) {
                user.put("partyMember",((Boolean) user.get("partyMember")) == true ? "是" : "否");
            }
            String username = user.get("username").toString();
		    String hql = "select NAME_ from BDF2_POSITION where ID_ in( select POSITION_ID_ from BDF2_USER_POSITION where USERNAME_ = '" + username + "')";
		    List<String> positions = session.createSQLQuery(hql).list();
		    String userposition = "";
			if (positions.size()>0) {
				for (String position : positions) {
					userposition += position + ",";
				}
				userposition = userposition.substring(0, userposition.length()-1);
			}
            user.put("positions",userposition);
            
		    hql = "select NAME_ from BDF2_DEPT where ID_ in( select DEPT_ID_ from BDF2_USER_DEPT where USERNAME_ = '" + username + "')";
		    List<String> depts = session.createSQLQuery(hql).list();
		    String userdept = "";
			if (depts.size()>0) {
				for (String dept : depts) {
					userdept += dept + ",";
				}
				userdept = userdept.substring(0, userdept.length()-1);
			}
            user.put("depts",userdept);
        }
    }
    public void interceptAutoFormData(List<Map<String, Object>> list)
            throws Exception {
    }
}