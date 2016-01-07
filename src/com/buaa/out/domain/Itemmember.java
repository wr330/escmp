package com.buaa.out.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name="Out_ItemMember")
public class  Itemmember implements Serializable {
	private static final long serialVersionUID = 1L;
    public Itemmember(){}
   public Itemmember(String id) {
      this.id=id;
 	}	

	private String id ;


	private Handover handover;
	private Supportitem supportitem;


	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}


	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "RecipientOid")
    public Handover getHandover() {
		return handover;
	}
	public void setHandover(Handover handover) {
		this.handover=handover;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "TID")
    public Supportitem getSupportitem() {
		return supportitem;
	}
	public void setSupportitem(Supportitem supportitem) {
		this.supportitem=supportitem;
	}

}