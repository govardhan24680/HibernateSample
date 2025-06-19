package com.newidea.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Passport implements Serializable{

	@Id
	@GeneratedValue
	private int id;
	
	private String passportId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
