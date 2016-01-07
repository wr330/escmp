package com.buaa.comm.uflo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;

@Component("newspublish.managerAssignmentHandler")
public class ManagerAssignmentHandler extends HibernateDao implements AssignmentHandler {

	@Override
	public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance,Context context) {
		List<String> manager = new ArrayList<String>();
		manager.add(NewsPublishService.manager);
		return manager;
	}
}
