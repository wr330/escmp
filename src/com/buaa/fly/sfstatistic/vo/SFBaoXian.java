package com.buaa.fly.sfstatistic.vo;

public class SFBaoXian {
	private String parameter;
    private String value;
    
    public SFBaoXian(){}
    public SFBaoXian(String parameter,String value){
    	this.parameter = parameter;
    	this.value = value;
    }
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
