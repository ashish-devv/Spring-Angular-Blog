package com.ashish.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Forgetpass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	
	@Column(nullable = false)
	private String uniquekey;
	
	@Column(nullable = false)
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Forgetpass [id=" + id + ", uniquekey=" + uniquekey + ", email=" + email + "]";
	}

	public Forgetpass(int id, String uniquekey, String email) {
		super();
		this.id = id;
		this.uniquekey = uniquekey;
		this.email = email;
	}

	public Forgetpass() {
		super();
	}
	
	
	

}
