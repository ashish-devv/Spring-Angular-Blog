package com.ashish.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pid;
	
	@Column(nullable = false)
	private int id;
	
	@Column(nullable = false)
	private String authorName;
	
	@Column(nullable = false,columnDefinition="VARCHAR(1000)")
	private String postHeading;
	
	private String postTag;
	
	
	private String postHeadImage;
	
	@Column(columnDefinition="VARCHAR(2000)")
	private String postDescription;
	
	@Column(columnDefinition="MEDIUMTEXT")
	private String postContent;
	
	@Column(nullable = false)
	private boolean postStatus;
	
	@Column(nullable = false,updatable=false)
	@CreationTimestamp
	private Date postedon;

	
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostHeading() {
		return postHeading;
	}

	public void setPostHeading(String postHeading) {
		this.postHeading = postHeading;
	}

	public String getPostTag() {
		return postTag;
	}

	public void setPostTag(String postTag) {
		this.postTag = postTag;
	}

	public String getPostHeadImage() {
		return postHeadImage;
	}

	public void setPostHeadImage(String postHeadImage) {
		this.postHeadImage = postHeadImage;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public boolean isPostStatus() {
		return postStatus;
	}

	public void setPostStatus(boolean postStatus) {
		this.postStatus = postStatus;
	}

	

	public Date getPostedon() {
		return postedon;
	}

	public void setPostedon(Date postedon) {
		this.postedon = postedon;
	}

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(int pid, int id, String authorName, String postHeading, String postTag, String postHeadImage,
			String postDescription, String postContent, boolean postStatus, Date postedon) {
		super();
		this.pid = pid;
		this.id = id;
		this.authorName = authorName;
		this.postHeading = postHeading;
		this.postTag = postTag;
		this.postHeadImage = postHeadImage;
		this.postDescription = postDescription;
		this.postContent = postContent;
		this.postStatus = postStatus;
		this.postedon = postedon;
	}

	@Override
	public String toString() {
		return "Post [pid=" + pid + ", id=" + id + ", authorName=" + authorName + ", postHeading=" + postHeading
				+ ", postTag=" + postTag + ", postHeadImage=" + postHeadImage + ", postDescription=" + postDescription
				+ ", postContent=" + postContent + ", postStatus=" + postStatus + ", postedon=" + postedon + "]";
	}

	
	
	

}
