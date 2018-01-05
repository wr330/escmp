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
		String sql = "select * from FLY_SFBAOXIAN where FTYPE_='" + ftype + "'";
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
		String sql = "select * from FLY_SFSCHEME where FTYPE_='" + ftype
				+ "'  order by MONTH_";
		return this.getJdbcTemplate().query(sql, new SFschemeMapper());
	}

	public class SFschemeMapper implements RowMapper<SFscheme> {

		public SFscheme mapRow(ResultSet rs, int index) throws SQLException {
			SFscheme data = new SFscheme();
			data.setMonth((Integer) rs.getObject("MONTH_"));
			data.setPlandays((Integer) rs.getObject("PLANDAYS_"));
			data.setFactdays((Integer) rs.getObject("FACTDAYS_"));
			data.setPlanjiaci((Integer) rs.getObject("PLANJIACI_"));
			data.setFactjiaci((Integer) rs.getObject("FACTJIACI_"));
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
			String ftype = (String) parameter.get("FTYPE_");
			String oid = UUID.randomUUID().toString();//生成主键
			//编写新增记录的SQL语句
			String sql = "INSERT INTO FLY_SFBAOXIAN(FTYPE_,ID_) VALUES('" + ftype + "','" + oid + "')";
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
			String ftype = (String) parameter.get("FTYPE_");
			Collection<SFBaoXian> items = (Collection<SFBaoXian>)parameter.get("FLY_SFBAOXIAN");
			String[] str = new String[7];
			int i = 0;
			//取得前台传来的数据，放到数组str中
			for(SFBaoXian item : items){
				str[i] = item.getValue();
				i++;
			}
			//对Fly_SFBaoXian表中数据进行更新保存
			String sql = "UPDATE FLY_SFBAOXIAN SET MACH_ = '" + str[0] 
					+ "', HEIGHT_ = '" + str[1] + "', MAXTABLE_ = '" + str[2]
					+ "', MINTABLE_ = '" + str[3] + "', ANGLE_ = '" + str[4] 
					+ "', OVERLOAD_ = '" + str[5] + "', NOVERLOAD_ = '" + str[6] 
					+ "' where FTYPE_='" + ftype + "'";
			this.getJdbcTemplate().update(sql);
		}				
	}
}
