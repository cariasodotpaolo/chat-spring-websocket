package com.gochat.domain;

/**
 * @author mpcariaso
 *
 */
public class ChatUser {

	private long userId;
    private String userName;
    private String fullName;
    private boolean online;
    
    public ChatUser(long userId, String userName, String fullName) {
		// TODO Auto-generated constructor stub
    	this.userId = userId;
    	this.userName = userName;
    	this.fullName = fullName;
    	this.online = false;
	}
    
    public ChatUser() {
		// TODO Auto-generated constructor stub
	}
    
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
}
