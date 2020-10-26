package com.ashish.blog.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Comments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	
	@Column(nullable = false)
	private int uid;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int pid;
	
	@Column(nullable = false)
	private String commentcontent;
	
	@Column(nullable = false,updatable=false)
	@CreationTimestamp
	private Date commenton;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}


	public Date getCommenton() {
		return commenton;
	}

	public void setCommenton(Date commenton) {
		this.commenton = commenton;
	}

	@Override
	public String toString() {
		return "Comments [cid=" + cid + ", uid=" + uid + ", name=" + name + ", pid=" + pid + ", commentcontent="
				+ commentcontent + ", commenton=" + commenton + "]";
	}

	public Comments(int cid, int uid, String name, int pid, String commentcontent, Date commenton) {
		super();
		this.cid = cid;
		this.uid = uid;
		this.name = name;
		this.pid = pid;
		this.commentcontent = commentcontent;
		this.commenton = commenton;
	}

	public Comments() {
		super();
	}
	
	

}
