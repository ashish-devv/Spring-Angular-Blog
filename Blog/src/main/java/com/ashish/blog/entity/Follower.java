package com.ashish.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Follower {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fid;
	
	@Column(nullable = false)
	private int senderid;
	
	@Column(nullable = false)
	private int receiverid;

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getSenderid() {
		return senderid;
	}

	public void setSenderid(int senderid) {
		this.senderid = senderid;
	}

	public int getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(int receiverid) {
		this.receiverid = receiverid;
	}

	@Override
	public String toString() {
		return "Follower [fid=" + fid + ", senderid=" + senderid + ", receiverid=" + receiverid + "]";
	}

	public Follower(int fid, int senderid, int receiverid) {
		super();
		this.fid = fid;
		this.senderid = senderid;
		this.receiverid = receiverid;
	}

	public Follower() {
		super();
	}
	
	
	
	

}
