package com.bstek.viewmap.service;

import java.util.List;
import java.util.Map;

//import com.bstek.bdf.pagination.Pagination;
import com.bstek.dorado.data.provider.Page;
import com.bstek.viewmap.domain.ViewMap;

public interface ViewMapService {
	public Page<ViewMap> queryViewMap(int pageIndex, int pageSize) throws Exception;

	public void queryViewMap(Page<ViewMap> page, Map<String, Object> parameter) throws Exception;

	public void insertViewMap(ViewMap viewMap) throws Exception;

	public void updateViewMap(ViewMap viewMap) throws Exception;

	public void deleteViewMap(ViewMap viewMap) throws Exception;

	public ViewMap queryViewMapByNodeCode(String code) throws Exception;

	public List<Map<String, Object>> queryExistMapCode(String validateData)throws Exception;
}
