package com.buaa.out.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PersonstatisticKey implements Serializable{
	 private static final long serialVersionUID = -3304319243957837925L;  
	    private long id ;  
	    private String name ;  
	    /** 
	     * @return the id 
	     */  
	    public long getId() {  
	        return id;  
	    }  
	    /** 
	     * @param id the id to set 
	     */  
	    public void setId(long id) {  
	        this.id = id;  
	    }  
	    /** 
	     * @return the name 
	     */  
	    public String getName() {  
	        return name;  
	    }  
	    /** 
	     * @param name the name to set 
	     */  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	  
	    @Override  
	    public boolean equals(Object o) {  
	        if(o instanceof PersonstatisticKey){  
	        	PersonstatisticKey key = (PersonstatisticKey)o ;  
	            if(this.id == key.getId() && this.name.equals(key.getName())){  
	                return true ;  
	            }  
	        }  
	        return false ;  
	    }  
	      
	    @Override  
	    public int hashCode() {  
	        return this.name.hashCode();  
	    }  
}
