package com.buaa.fly.outlineexecution.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buaa.fly.domain.Outlineexecution;
import com.buaa.fly.subject.dao.SubjectDao;
import com.common.JdbcBaseDao;
@Repository("outlineexecutionDaoforJDBC")
public class OutlineexecutionDaoforJDBC extends JdbcBaseDao{
@Resource
	private SubjectDao subjectDao;
	public Collection<Outlineexecution> query(Map<String, Object> parameter) throws Exception {
			
		String ftype =(String) parameter.get("ftype");
		String subject =(String) parameter.get("subject");
		String sql="with cte as(	select a.oid from Fly_Subject a where Oid='"+subject+"'	union all select k.oid from Fly_Subject k inner join cte c on c.oid=k.ParentNode)select *from Fly_OutlineExecution u where 1=1";

		if(StringUtils.isEmpty(ftype)){
			sql+=" and u.aircrafttype is NULL";
		}else
			sql+=" and u.aircrafttype ='" + ftype + "'";

			sql+=" and u.project in (select *from cte)";
			
			return this.getJdbcTemplate().query(sql, new OutlineexecutionRowMapper());

	}
	
	class  OutlineexecutionRowMapper implements RowMapper<Outlineexecution>{
	    public Outlineexecution mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Outlineexecution d=new Outlineexecution();
	        d.setAircraftStruct(rs.getString("AircraftStruct"));
	        d.setOid(rs.getString("oid"));
	        d.setAircrafttype(rs.getString("aircrafttype"));
	        d.setCombineFlights(rs.getInt("combineflights"));
	        d.setJiaci(rs.getInt("jiaci"));
	        d.setShijijiaci(rs.getInt("shijijiaci"));
	        d.setOutlineFlights(rs.getInt("outlineFlights"));
	        d.setRemainstate(rs.getString("remainstate"));
	        d.setTestMethod(rs.getString("testMethod"));
	        d.setCombineSubject(rs.getString("combineSubject"));
	        d.setCompletestate(rs.getString("completestate"));
	        d.setTestStatus(rs.getString("testStatus"));
	        d.setEngineState(rs.getString("engineState"));
	        d.setExecdate(rs.getDate("execdate"));
	        d.setMiji(rs.getString("miji"));
	        try {
				d.setProject(subjectDao.querySubjectbyId(rs.getString("project")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return d;
	    }
	}
}
