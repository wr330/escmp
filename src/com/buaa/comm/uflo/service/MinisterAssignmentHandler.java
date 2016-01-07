package com.buaa.comm.uflo.service;

import java.util.Collection;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;

@Component("newspublish.ministerAssignmentHandler")
public class MinisterAssignmentHandler extends HibernateDao implements AssignmentHandler {

	@Override
	public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance,Context context) {
		Session session = this.getSessionFactory().openSession();
		try{
			ApproverService approverService = new ApproverService();
			Collection<String> minister = approverService.getMinister(session);
			return minister;
		}finally{
			session.close();
		}
	}
}
