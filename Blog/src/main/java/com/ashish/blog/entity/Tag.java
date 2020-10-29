package com.ashish.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tag_id;
	
	@Column(nullable = false)
	private String tag_name;
	
	@Column(nullable = false)
	private int tag_by_uid;
	
	@Column(nullable = false,updatable=false)
	@CreationTimestamp
	private Date tag_createdon;

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public int getTag_by_uid() {
		return tag_by_uid;
	}

	public void setTag_by_uid(int tag_by_uid) {
		this.tag_by_uid = tag_by_uid;
	}

	public Date getTag_createdon() {
		return tag_createdon;
	}

	public void setTag_createdon(Date tag_createdon) {
		this.tag_createdon = tag_createdon;
	}

	@Override
	public String toString() {
		return "Tag [tag_id=" + tag_id + ", tag_name=" + tag_name + ", tag_by_uid=" + tag_by_uid + ", tag_createdon="
				+ tag_createdon + "]";
	}

	public Tag(int tag_id, String tag_name, int tag_by_uid, Date tag_createdon) {
		super();
		this.tag_id = tag_id;
		this.tag_name = tag_name;
		this.tag_by_uid = tag_by_uid;
		this.tag_createdon = tag_createdon;
	}

	public Tag() {
		super();
		
	}
	
	
	
	
}
