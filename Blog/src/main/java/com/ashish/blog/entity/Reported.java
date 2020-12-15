package com.ashish.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reported {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int rid;
	
	@Column(nullable = false)
	private int pid;

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Reported [rid=" + rid + ", pid=" + pid + "]";
	}

	public Reported(int rid, int pid) {
		super();
		this.rid = rid;
		this.pid = pid;
	}

	public Reported() {
		super();
		
	}
	
	
	
	
	
	
	

}
