package com.bstek.datadictionary.pr;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

//import com.bstek.bdf.pagination.Pagination;
import com.bstek.datadictionary.domain.DataCode;
import com.bstek.datadictionary.service.DataCodeService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.entity.FilterType;
import com.bstek.dorado.data.provider.Page;
@Component
public class DataCodePR {
    
	private DataCodeService dataCodeManage;


	public DataCodeService getDataCodeManage() {
		return dataCodeManage;
	}
    @Resource(name ="defaultDataCodeServiceImp")
	public void setDataCodeManage(DataCodeService dataCodeManage) {
		this.dataCodeManage = dataCodeManage;
	}

	/**
	 * 数据字典分页查询
	 * 
	 * @param page
	 * @param parameter
	 * @throws Exception 
	 */
	@DataProvider
	public void findDataCode(Page<DataCode> page, Map<String, Object> parameter) throws Exception {
//		Page<DataCode> pagination = 
		dataCodeManage.findDataCode(parameter, page);
//		page.setEntities(pagination.getEntities());
//		page.setEntityCount(pagination.getEntityCount());
	}

	/**
	 * 根据父节点代码查询数据字典
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception 
	 */
	@DataProvider
	public Collection<DataCode> findDataCodeByParentCode(String code) throws Exception {
		return dataCodeManage.findDataCodeByParentCode(code);
	}

	/**
	 * 数据字典保存操作（包括新增、修改、删除）
	 * 
	 * @param colls
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataResolver
	public void saveAll(Collection<DataCode> colls) throws Exception {
		for (Iterator<DataCode> iter = EntityUtils.getIterator(colls,
				FilterType.DIRTY); iter.hasNext();) {
			DataCode dataCode = iter.next();
			EntityState state = EntityUtils.getState(dataCode);
			if (state.equals(EntityState.NEW)) {
				dataCodeManage.insertDataCode(dataCode);
			}
			if (state.equals(EntityState.MODIFIED)) {
				dataCodeManage.updateDataCode(dataCode);
			}
			if (state.equals(EntityState.DELETED)) {
				dataCodeManage.deleteDataCodeById(dataCode.getDcId());
			}
		}
	}
}