package com.buaa.fly.sfstatistic.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.common.JdbcBaseDao;

@Component("sFBaoXianService")
public class SFBaoXianService extends JdbcBaseDao {

	/**
	  * 前台和数据库中“试飞包线统计”的数据类型不一致，因此用此方法进行类型转化
	  * @param ftype String类型,表示机型
	  */
	@DataProvider
	public Collection<SFBaoXian> loadSFBaoXian(String ftype) {
		ArrayList<SFBaoXian> items = new ArrayList<SFBaoXian>();
		String sql = "select * from Fly_SFBaoXian where ftype='" + ftype + "'";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		if (list.size() > 0) {
			String[][] array = {
					{ "mach", "height", "maxtable", "mintable", "angle",
							"overload", "Noverload" },
					{ "最大M数", "最大高度", "最大表速", "最小速度", "最大攻角", "最大过载", "最大负过载" } };
			for (int i = 0; i < 7; i++) {
				SFBaoXian item;
				//需要判断所取得数据是否为空
				if(list.get(0).get(array[0][i]) == null){
					item = new SFBaoXian(array[1][i], null);
				}
				else{
					String value = list.get(0).get(array[0][i]).toString();
					item = new SFBaoXian(array[1][i], value);
				}
				items.add(item);
			}
		}
		return items;
	}

	@DataProvider
	public Collection<SFscheme> loadSFscheme(String ftype) {
		String sql = "select * from Fly_SFscheme where ftype='" + ftype
				+ "'  order by Month";
		return this.getJdbcTemplate().query(sql, new SFschemeMapper());
	}

	public class SFschemeMapper implements RowMapper<SFscheme> {

		public SFscheme mapRow(ResultSet rs, int index) throws SQLException {
			SFscheme data = new SFscheme();
			data.setMonth((Integer) rs.getObject("Month"));
			data.setPlandays((Integer) rs.getObject("PlanDays"));
			data.setFactdays((Integer) rs.getObject("FactDays"));
			data.setPlanjiaci((Integer) rs.getObject("PlanjiaCi"));
			data.setFactjiaci((Integer) rs.getObject("FactJiaCi"));
			return data;
		}
	}
	
	/**
	 根据前台用户选择的机型，新增相应的试飞包线扩展统计表记录
	 @param parameter Map<String, Object>类型,表示机型
	 */
	@Expose
	public void saveBXtjInsert(Map<String, Object> parameter) {
		
		if (null != parameter && !parameter.isEmpty()) {
			String ftype = (String) parameter.get("ftype");
			String oid = UUID.randomUUID().toString();//生成主键
			//编写新增记录的SQL语句
			String sql = "INSERT INTO Fly_SFBaoXian(ftype,id) VALUES('" + ftype + "','" + oid + "')";
			this.getJdbcTemplate().update(sql);
		}		
		
	}
	
	/**
	 根据前台用户选择的机型和试飞包线扩展记录修改，在数据库的包线扩展统计表中保存相应的修改
	 @param parameter Map<String, Object>类型,表示机型与试飞包线信息记录
	 */
	@Expose
	@SuppressWarnings("unchecked")	
	public void saveBXtj(Map<String, Object> parameter) {		
		if (null != parameter && !parameter.isEmpty()) {
			String ftype = (String) parameter.get("ftype");
			Collection<SFBaoXian> items = (Collection<SFBaoXian>)parameter.get("SFBaoXian");
			String[] str = new String[7];
			int i = 0;
			//取得前台传来的数据，放到数组str中
			for(SFBaoXian item : items){
				str[i] = item.getValue();
				i++;
			}
			//对Fly_SFBaoXian表中数据进行更新保存
			String sql = "UPDATE Fly_SFBaoXian SET mach = '" + str[0] 
					+ "', height = '" + str[1] + "', maxtable = '" + str[2]
					+ "', mintable = '" + str[3] + "', angle = '" + str[4] 
					+ "', overload = '" + str[5] + "', Noverload = '" + str[6] 
					+ "' where ftype='" + ftype + "'";
			this.getJdbcTemplate().update(sql);
		}				
	}
}
