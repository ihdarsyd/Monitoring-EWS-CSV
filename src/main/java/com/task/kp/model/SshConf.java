package com.task.kp.model;

public class SshConf {
	private String host;
	private String userName;
	private String password;
	private String port;
	
	
	public SshConf(String host, String userName, String password) {
		this.host = host;
		this.userName = userName;
		this.password = password;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
}
