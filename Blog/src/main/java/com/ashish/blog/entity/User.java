package com.ashish.blog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uid;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false,updatable=false)
	@CreationTimestamp
	private Date joinedon;
	
	@Column(nullable = false)
	private String collegename;
	
	private String place;
	
	@Column(nullable = false)
	private String aboutme;
	
	@Column(nullable = false)
	private String profilepic;
	
	private String twitterid;
	
	private String fbid;
	
	private String instaid;
	
	private String githubid;
	
	@Column(nullable = false,unique = true,length = 30)
	private String email;
	
	@Column(nullable = false,unique = true)
	private String registration;
	
	@Column(nullable = false,length = 300)
	private String password;
	
	private String role;
	
	@Column(nullable = false)
	private boolean enabled;

	//constructors
	
	public User() {
		super();
		
	}

	//getter and setters
	
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

	

	public Date getJoinedon() {
		return joinedon;
	}

	public void setJoinedon(Date joinedon) {
		this.joinedon = joinedon;
	}

	public String getCollegename() {
		return collegename;
	}

	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}

	public String getTwitterid() {
		return twitterid;
	}

	public void setTwitterid(String twitterid) {
		this.twitterid = twitterid;
	}

	public String getFbid() {
		return fbid;
	}

	public void setFbid(String fbid) {
		this.fbid = fbid;
	}

	public String getInstaid() {
		return instaid;
	}

	public void setInstaid(String instaid) {
		this.instaid = instaid;
	}

	public String getGithubid() {
		return githubid;
	}

	public void setGithubid(String githubid) {
		this.githubid = githubid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

	public String getAboutme() {
		return aboutme;
	}

	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}

	

	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", joinedon=" + joinedon + ", collegename=" + collegename
				+ ", place=" + place + ", aboutme=" + aboutme + ", profilepic=" + profilepic + ", twitterid="
				+ twitterid + ", fbid=" + fbid + ", instaid=" + instaid + ", githubid=" + githubid + ", email=" + email
				+ ", registration=" + registration + ", password=" + password + ", role=" + role + ", enabled="
				+ enabled + "]";
	}

	public User(int uid, String name, Date joinedon, String collegename, String place, String aboutme,
			String profilepic, String twitterid, String fbid, String instaid, String githubid, String email,
			String registration, String password, String role, boolean enabled) {
		super();
		this.uid = uid;
		this.name = name;
		this.joinedon = joinedon;
		this.collegename = collegename;
		this.place = place;
		this.aboutme = aboutme;
		this.profilepic = profilepic;
		this.twitterid = twitterid;
		this.fbid = fbid;
		this.instaid = instaid;
		this.githubid = githubid;
		this.email = email;
		this.registration = registration;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}

	
	
	
	
	
	
	
	
	

}
