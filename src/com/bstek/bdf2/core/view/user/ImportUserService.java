package com.bstek.bdf2.core.view.user;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.util.StringUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.importexcel.ImportExcelJdbcDao;
import com.bstek.bdf2.importexcel.manager.ExcelModelManager;
import com.bstek.bdf2.importexcel.model.CellWrapper;
import com.bstek.bdf2.importexcel.model.ExcelDataWrapper;
import com.bstek.bdf2.importexcel.model.ExcelModel;
import com.bstek.bdf2.importexcel.model.ExcelModelDetail;
import com.bstek.bdf2.importexcel.model.GeneratePkStrategry;
import com.bstek.bdf2.importexcel.model.RowWrapper;
import com.bstek.bdf2.importexcel.processor.IExcelProcessor;
import com.bstek.bdf2.importexcel.processor.impl.DefaultExcelProcessor;

@Component("importUserService")
public class ImportUserService extends ImportExcelJdbcDao implements IExcelProcessor {
	
	@Autowired
	@Qualifier(ExcelModelManager.BEAN_ID)
	public ExcelModelManager excelModelManager;
	
	public static final String BEAN_ID = "bdf2.DefaultProcessor";
	public final Log logger = LogFactory.getLog(DefaultExcelProcessor.class);
	private String username;
	private String dept;
	
	@Override
	public int execute(ExcelDataWrapper excelDataWrapper) throws Exception {
		if (!excelDataWrapper.isValidate()) {
			throw new RuntimeException("当前数据没有通过验证,不能解析入库！");
		}
		ExcelModel excelModel = excelDataWrapper.getExcelModel();
		ExcelModelDetail excelModelDetail = null;
		if (StringUtils.hasText(excelModel.getPrimaryKey())) {
			excelModelDetail = excelModelManager.findExcelModelDetailByModelIdAndPrimaryKey(excelModel.getId(), excelModel.getPrimaryKey());
		}

		SupportSequenceDb type = this.validateDb(excelModel.getDatasourceName());
		if (type != null) {
			excelModel.setDbType(type.name());
		}
		Collection<RowWrapper> rowWrappers = excelDataWrapper.getRowWrappers();
		String tableName = excelDataWrapper.getTableName();
		int count = 0;
		for (RowWrapper rowWrapper : rowWrappers) {
			Collection<CellWrapper> cellWrappers = rowWrapper.getCellWrappers();
			// 系统定义主键策略
			if (excelModelDetail == null && cellWrappers.size() > 0) {
				if (StringUtils.hasText(excelModel.getPrimaryKey())) {
					if (excelModel.getPrimaryKeyType().equals(GeneratePkStrategry.VMID.name())) {
						CellWrapper cellWrapper = new CellWrapper();
						cellWrapper.setPrimaryKey(true);
						cellWrapper.setColumnName(excelModel.getPrimaryKey());
						cellWrapper.setValue(UUID.randomUUID().toString());
						cellWrappers.add(cellWrapper);
					} else if (excelModel.getPrimaryKeyType().equals(GeneratePkStrategry.UUID.name())) {
						CellWrapper cellWrapper = new CellWrapper();
						cellWrapper.setPrimaryKey(true);
						cellWrapper.setColumnName(excelModel.getPrimaryKey());
						cellWrapper.setValue(UUID.randomUUID().toString());
						cellWrappers.add(cellWrapper);
					}
				}
			}
			List<String> columnNameList = new ArrayList<String>();
			List<Object> columnValueList = new ArrayList<Object>();
			for (CellWrapper cellWrapper : cellWrappers) {
				String columnName = cellWrapper.getColumnName();
				if (StringUtils.hasText(columnName)) {
					Object columnValue = cellWrapper.getValue();
					if(columnValue != null && columnValue.getClass().getName().equals("java.lang.Double")){
						String value =columnValue.toString();
						columnValue = value.substring(0,value.indexOf("."));
					}
					if (columnName.equals("dept"))
						dept = columnValue == null ?null:columnValue.toString();
					else {
						columnNameList.add(columnName);
						if (columnName.equals("username"))
							username = columnValue.toString();
						if (columnName.equals("male")) {
							if ("女".equals(columnValue))
								columnValue = 0;
							else
								columnValue = 1;
						} else if (columnName.endsWith("member")) {
							if ("是".equals(columnValue))
								columnValue = 1;
							else
								columnValue = 0;
						}
						columnValueList.add(columnValue);
					}
					if (cellWrapper.isPrimaryKey() && excelModel.getPrimaryKeyType().equals(GeneratePkStrategry.ASSIGNED.name())) {
						String sql = "select count(1) from " + tableName + " where " + columnName + "=?";
						int sum = this.getJdbcTemplate(excelModel.getDatasourceName()).queryForInt(sql, new Object[] { columnValue });
						if (sum > 0) {
							throw new RuntimeException("数据库表[" + tableName + "]的字段[" + columnName + "]为主键，键值[" + columnValue + "]重复！");
						}
					}
				}

			}
			int n = this.insertRowWrapper2Table(excelModel, columnNameList, columnValueList);
			if (n == 1) {
				count++;
			}
		}
		logger.info("解析excel入库成功，导入[" + count + "]条数据！");
		return count;

	}

	@Override
	public String getName() {
		return "用户表解析入库处理类";
	}

	private int insertRowWrapper2Table(ExcelModel excelModel, List<String> columnNameList, List<Object> columnValueList) throws Exception {
		String tableName = excelModel.getTableName();
		StringBuffer sb = new StringBuffer("insert into ");
		sb.append(tableName + "( ");
		StringBuffer sbValues = new StringBuffer(" values(");
		int j = 1;
		if (StringUtils.hasText(excelModel.getPrimaryKey())) {
			if (excelModel.getPrimaryKeyType().equals(GeneratePkStrategry.SEQUENCE.name())) {
				if (excelModel.getDbType().equals(SupportSequenceDb.oracle.name())) {
					sb.append(excelModel.getPrimaryKey());
					sbValues.append(this.getOracleNextval(excelModel.getSequenceName()));
				} else if (excelModel.getDbType().equals(SupportSequenceDb.db2.name())) {
					sb.append(excelModel.getPrimaryKey());
					sbValues.append(this.getDB2Nextval(excelModel.getSequenceName()));
				}
				if (columnNameList.size() > 0 && columnValueList.size() > 0) {
					sb.append(",");
					sbValues.append(",");
				}
			}
		}
		for (String s : columnNameList) {
			if (columnNameList.size() == j) {
				sb.append(s);
				sbValues.append("?");
			} else {
				sb.append(s + ",");
				sbValues.append("?,");
			}
			j++;

		}
		String salt = String.valueOf(RandomUtils.nextInt(100));
		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
		String password = passwordEncoder.encodePassword(columnValueList.get(0).toString(), salt);
		sb.append(",Administrator,Enabled,CompanyId,Password,Salt)");
		sbValues.append(",0,1,'" + ContextHolder.getLoginUser().getCompanyId() + "','" + password + "','" + salt + "')");
		String sql = sb.append(sbValues).toString();
		int aa =  this.getJdbcTemplate(excelModel.getDatasourceName()).update(sql, columnValueList.toArray());
		InsertUserDept();
		return aa;
	}
	//导入用户部门信息
    private void InsertUserDept(){
    	if(username != null && dept != null){
    		List<Map<String, Object>> defaultDept = this.getJdbcTemplate().queryForList("select * from BDF2_DEPT where NAME_ ='" + dept + "'");
    		if(defaultDept.size()>0){
    		      String deptID = defaultDept.get(0).get("ID_").toString();
    		      String id = UUID.randomUUID().toString();
    		      String sql = "insert into BDF2_USER_DEPT (ID_,DEPT_ID_,USERNAME_) values('" + id + "','" + deptID + "','" + username +"')";
    		       this.getJdbcTemplate().execute(sql);
    		}
    	}
    }
	private SupportSequenceDb validateDb(String datasourceName) {
		return this.getJdbcTemplate(datasourceName).execute(new ConnectionCallback<SupportSequenceDb>() {
			public SupportSequenceDb doInConnection(Connection con) throws SQLException, DataAccessException {
				DatabaseMetaData databaseMetaData = con.getMetaData();
				String databaseProductName = databaseMetaData.getDatabaseProductName();
				if (org.apache.commons.lang.StringUtils.containsIgnoreCase(databaseProductName, SupportSequenceDb.oracle.name())) {
					return SupportSequenceDb.oracle;
				} else if (org.apache.commons.lang.StringUtils.containsIgnoreCase(databaseProductName, SupportSequenceDb.db2.name())) {
					return SupportSequenceDb.db2;
				}
				return null;
			}
		});
	}

	public String getOracleNextval(String sequenceName) {
		return sequenceName + ".nextval";
	}

	public String getDB2Nextval(String sequenceName) {
		return "NEXTVAL FOR " + sequenceName;
	}

	public ExcelModelManager getExcelModelManager() {
		return excelModelManager;
	}

	public void setExcelModelManager(ExcelModelManager excelModelManager) {
		this.excelModelManager = excelModelManager;
	}

	private enum SupportSequenceDb {
		oracle, db2
	}
}
