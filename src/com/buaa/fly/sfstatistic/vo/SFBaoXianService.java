package com.buaa.fly.sfstatistic.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.common.JdbcBaseDao;

@Component("sFBaoXianService")
public class SFBaoXianService  extends JdbcBaseDao {
	
	@DataProvider
	public Collection<SFBaoXian> loadSFBaoXian(String ftype){
		ArrayList<SFBaoXian> items = new ArrayList<SFBaoXian>();
		String sql = "select * from SFBaoXian where ftype='"+ ftype + "'";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		if(list.size()>0){
			   String[][] array = {{"mach","height","maxtable","mintable","angle","overload","Noverload"},
					                           {"最大M数", "最大高度","最大表速","最小速度","最大攻角","最大过载","最大负过载"}};
			   for(int i=0;i<7;i++){
			       String value = list.get(0).get(array[0][i]).toString();
			       SFBaoXian item = new SFBaoXian(array[1][i],value);
			       items.add(item);
			   }		       
		}
		return items;
	}
	@DataProvider
	public Collection<SFscheme> loadSFscheme(String ftype){
		String sql = "select * from SFscheme where ftype='"+ ftype + "'  order by Month";
		return this.getJdbcTemplate().query(sql,new SFschemeMapper());
	}

public class SFschemeMapper implements RowMapper<SFscheme>{

	public SFscheme mapRow(ResultSet rs, int index) throws SQLException {
		SFscheme data=new SFscheme();
				data.setMonth((Integer)rs.getObject("Month"));
				data.setPlandays((Integer)rs.getObject("PlanDays"));
				data.setFactdays((Integer)rs.getObject("FactDays"));
				data.setPlanjiaci((Integer)rs.getObject("PlanjiaCi"));
				data.setFactjiaci((Integer)rs.getObject("FactJiaCi"));
				return	data;
	}
}
}
