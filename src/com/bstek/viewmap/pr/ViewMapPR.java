package com.bstek.viewmap.pr;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

//import com.bstek.bdf.pagination.Pagination;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.entity.FilterType;
import com.bstek.dorado.data.provider.Page;
import com.bstek.viewmap.domain.ViewMap;
import com.bstek.viewmap.service.ViewMapService;

@Component
public class ViewMapPR {
	private ViewMapService viewMapService;

	@DataProvider
	public void findViewMaps(Page<ViewMap> page, Map<String, Object> parameter) throws Exception {
//		Page<ViewMap> pagination = 
				viewMapService.queryViewMap(page, parameter);
//		page.setEntities(pagination.getEntities());
//		page.setEntityCount(pagination.getEntityCount());
	}
	@Expose
	public String checkMapCode(Map<String, Object> parameter)throws Exception{
		String entityId = (String) parameter.get("id");
		String validateData = (String) parameter.get("validateData");
		List<Map<String, Object>> resultsBiz = viewMapService.queryExistMapCode(validateData);
		if(resultsBiz.size()>0){
			if(StringUtils.hasText(entityId)== false || entityId.equals(resultsBiz.get(0).get("map_id").toString()) == false)
			return validateData+"已经存在";
			else
			return null;
		}else
			return  null;
	}

	@SuppressWarnings("unchecked")
	@DataResolver
	public void saveAllMaps(Collection<ViewMap> viewMaps) throws Exception {
		for (Iterator<ViewMap> iter = EntityUtils.getIterator(viewMaps, FilterType.DIRTY); iter.hasNext();) {
			ViewMap viewMap = iter.next();
			EntityState state = EntityUtils.getState(viewMap);
			if (state.equals(EntityState.NEW)) {
				viewMapService.insertViewMap(viewMap);
			} else if (state.equals(EntityState.MODIFIED)) {
				viewMapService.updateViewMap(viewMap);

			} else if (state.equals(EntityState.DELETED)) {
				viewMapService.deleteViewMap(viewMap);
			}

		}

	}

	public ViewMapService getViewMapService() {
		return viewMapService;
	}

	@Resource(name = "defaultViewMapServiceImp")
	public void setViewMapService(ViewMapService viewMapService) {
		this.viewMapService = viewMapService;
	}
}
