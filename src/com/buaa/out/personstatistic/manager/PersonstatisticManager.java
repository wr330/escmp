
package com.buaa.out.personstatistic.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.buaa.out.domain.Personstatistic;
import com.buaa.out.personstatistic.dao.PersonstatisticDao;

@Component("personstatisticManager")
public class PersonstatisticManager {
	
	@Resource
	private PersonstatisticDao personstatisticDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public Collection<Personstatistic> queryPersonstatistic(Map<String, Object> parameter) throws Exception {
	    return personstatisticDao.queryPersonstatistic(parameter);
	}
	
}
