package com.buaa.comm.uflo.service;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.core.Configure;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Criterion;
import com.bstek.dorado.data.provider.Order;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.data.provider.filter.SingleValueFilterCriterion;
import com.bstek.uflo.model.HistoryTask;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.model.task.TaskType;
import com.bstek.uflo.query.HistoryTaskQuery;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.HistoryService;
import com.bstek.uflo.service.TaskService;
import com.bstek.uflo.utils.EnvironmentUtils;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("taskMaintain")
public class TaskMaintain
{

  @Autowired
  @Qualifier("uflo.taskService")
  private TaskService taskService;
  
  @DataProvider
  public Collection<Task> loadTask(Criteria criteria)
  {
    TaskQuery todoQuery1 = this.taskService.createTaskQuery();
    if ((criteria == null) || (criteria.getCriterions().isEmpty())) {
      todoQuery1.assignee(EnvironmentUtils.getEnvironment().getLoginUser());
    }
    buildTodoCriteriaQuery(criteria, todoQuery1);
    Collection<Task> result = todoQuery1.list();
    TaskQuery todoQuery2 = this.taskService.createTaskQuery();
    todoQuery2.addParticipator(EnvironmentUtils.getEnvironment().getLoginUser());
    todoQuery2.addTaskState(TaskState.Ready);
    buildParticipatingCriteriaQuery(criteria, todoQuery2);
    result.addAll(todoQuery2.list());
    return result;
  }
  
  private void buildParticipatingCriteriaQuery(Criteria criteria, TaskQuery todoQuery) {
    Map<String, String> propertyValueMap = getPropertyValueMap(criteria);
    for (String key : propertyValueMap.keySet()) {
      todoQuery.nameLike("%" + (String)propertyValueMap.get(key) + "%");
    }

    buildTodoCriteriaOrderQuery(criteria, todoQuery);
  }

  private void buildTodoCriteriaQuery(Criteria criteria, TaskQuery todoQuery) {
    Map<String, String> propertyValueMap = getPropertyValueMap(criteria);
    for (String key : propertyValueMap.keySet()) {
      if ("taskName".equals(key)) {
        todoQuery.nameLike("%" + (String)propertyValueMap.get(key) + "%");
        todoQuery.assignee(EnvironmentUtils.getEnvironment().getLoginUser());
      } else {
        String value = (String)propertyValueMap.get(key);
        if (TaskType.Participative.name().equals(value)) {
          todoQuery.addParticipator(EnvironmentUtils.getEnvironment().getLoginUser());
          todoQuery.assignee(EnvironmentUtils.getEnvironment().getLoginUser());
        } else if (TaskType.Normal.name().equals(value)) {
          todoQuery.assignee(EnvironmentUtils.getEnvironment().getLoginUser());
          todoQuery.taskType(TaskType.Normal);
        } else if (TaskType.Countersign.name().equals(value)) {
          todoQuery.assignee(EnvironmentUtils.getEnvironment().getLoginUser());
          todoQuery.taskType(TaskType.Countersign);
        }
      }
    }

    buildTodoCriteriaOrderQuery(criteria, todoQuery);
  }

  private void buildTodoCriteriaOrderQuery(Criteria criteria, TaskQuery todoQuery) {
    Map<String, Boolean> propertyOrderMap = getQueryOrder(criteria);
    for (String property : propertyOrderMap.keySet()) {
      if (((Boolean)propertyOrderMap.get(property)).booleanValue())
        todoQuery.addOrderAsc(property);
      else {
        todoQuery.addOrderDesc(property);
      }
    }
    if (propertyOrderMap.isEmpty()) {
      todoQuery.addOrderAsc("duedate");
      todoQuery.addOrderDesc("createDate");
    }
  }

  
  private Map<String, String> getPropertyValueMap(Criteria criteria)
  {
    Map propertyValueMap = new HashMap();
    if (criteria == null) {
      return propertyValueMap;
    }
    List<Criterion> criterions = criteria.getCriterions();
    for (Criterion criterion : criterions) {
      if ((criterion instanceof SingleValueFilterCriterion)) {
        SingleValueFilterCriterion singleValueFilterCriterion = (SingleValueFilterCriterion)criterion;
        propertyValueMap.put(singleValueFilterCriterion.getProperty(), (String)singleValueFilterCriterion.getValue());
      }
    }
    return propertyValueMap;
  }

  private Map<String, Boolean> getQueryOrder(Criteria criteria) {
    Map propertyOrderMap = new HashMap();
    if (criteria == null) {
      return propertyOrderMap;
    }
    List orderList = criteria.getOrders();
    for (int i = 0; i < orderList.size(); i++) {
      Order order = (Order)orderList.get(i);
      propertyOrderMap.put(order.getProperty(), Boolean.valueOf(!order.isDesc()));
    }
    return propertyOrderMap;
  }
}