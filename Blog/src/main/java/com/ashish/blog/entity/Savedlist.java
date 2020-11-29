package com.ashish.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Savedlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sid;
	
	@Column(nullable = false)
	private int uid;
	
	@Column(nullable = false)
	private int pid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public Savedlist(int sid, int uid, int pid) {
		super();
		this.sid = sid;
		this.uid = uid;
		this.pid = pid;
	}

	public Savedlist() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Savedlist [sid=" + sid + ", uid=" + uid + ", pid=" + pid + "]";
	}
	
	

}
