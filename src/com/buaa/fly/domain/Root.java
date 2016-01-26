package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.List;




public class  Root implements Serializable {
	private static final long serialVersionUID = 1L;
    public Root(){}
 

	private String label ;

	private List<Ftypes> categories;
	private List<Subject> subject;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Subject> getOutlineExecution() {
		return subject;
	}

	public void setOutlineExecution(List<Subject> subject) {
		this.subject = subject;
	}

	public List<Ftypes> getCategories() {
		return categories;
	}

	public void setCategories(List<Ftypes> categories) {
		this.categories = categories;
	}

	

}