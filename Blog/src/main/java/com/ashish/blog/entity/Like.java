package com.ashish.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "liketable")
public class Like {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int lid;
	
	@Column(nullable = false)
	private int userid;
	
	@Column(nullable = false)
	private int postid;

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getPostid() {
		return postid;
	}

	public void setPostid(int postid) {
		this.postid = postid;
	}

	@Override
	public String toString() {
		return "Like [lid=" + lid + ", userid=" + userid + ", postid=" + postid + "]";
	}

	public Like(int lid, int userid, int postid) {
		super();
		this.lid = lid;
		this.userid = userid;
		this.postid = postid;
	}

	public Like() {
		super();
	}
	
	
	
	
}
