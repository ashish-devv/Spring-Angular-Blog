package com.ashish.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {
	
	@Value("${spring.mail.host}")
	private String Host;
	
	@Value("${spring.mail.port}")
	private int port;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;
	
	public String getHost() {
		return Host;
	}
	public void setHost(String host) {
		Host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public EmailConfig(String host, int port, String username, String password) {
		super();
		Host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	public EmailConfig() {
		super();
		
	}

	
	
}
