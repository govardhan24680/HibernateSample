package com.newidea.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Passport implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false) //will not allowed null value
	private String passportId;
	
	@Transient
	private String officeName; //column will not generate
	
	private String countryName; //column will generate
	
	

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", passportId=" + passportId + "]";
	}
	
	
}
