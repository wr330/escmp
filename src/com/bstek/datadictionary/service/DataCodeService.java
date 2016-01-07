package com.bstek.datadictionary.service;

import java.util.Collection;
import java.util.Map;

//import com.bstek.bdf.pagination.Pagination;
import com.bstek.datadictionary.domain.DataCode;
import com.bstek.dorado.data.provider.Page;

public interface DataCodeService {
 public void deleteDataCodeById(String dId)throws Exception;
 public void findDataCode(Map<String,Object> parameter,Page<DataCode> page)throws Exception;
 public Collection<DataCode> findDataCodeByParentCode(String pId)throws Exception;
 public void insertDataCode(DataCode dataCode)throws Exception;
 public void updateDataCode(DataCode dataCode)throws Exception;
}
