package com.porfirio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REGIONS")
public class Region implements Serializable{
	

	@Id
	@Column(name="REGION_ID")
	private Long regionId;
	
	@Column(name="NAME")
	private String name;

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
