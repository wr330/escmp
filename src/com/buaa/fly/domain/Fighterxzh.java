package com.buaa.fly.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.math.BigDecimal;
import com.buaa.fly.domain.Fpici;

@Entity
@Table(name = "FLY_FIGHTERXZH")
public class Fighterxzh implements Serializable {
	private static final long serialVersionUID = 1L;

	public Fighterxzh() {
	}

	private Flightrestrict flightRestrict;
	private String id;
	private String referenceName;

	@Id
	@Column(name = "OID_", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "REFERENCENAME_")
	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "FLIGHTRESTRICT_")
	public Flightrestrict getFlightRestrict() {
		return flightRestrict;
	}

	public void setFlightRestrict(Flightrestrict flightRestrict) {
		this.flightRestrict = flightRestrict;
	}
}