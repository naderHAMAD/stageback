package com.test.spring;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class Server {
	@Value("${server.username}")
	private String username;
	@Value("${server.password}")
	private String password;
	@Value("${server.ip}")
	private String ip;
	@Value("${server.processContainer}")
	private String container;
	@Value("${server.process}")
	private String process;
	
	
	@Override
	public String toString() {
		return "Server [username=" + username + ", password=" + password + "]";
	}
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	
	public String getIp() {
		return this.ip;
	}
	public String getContainer() {
		
		return this.container;
	}
	public String getprocess() {
		
		return this.process;
	}
	

}
