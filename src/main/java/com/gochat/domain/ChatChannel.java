package com.gochat.domain;

import java.util.Date;

/**
 * @author mpcariaso
 *
 */
public class ChatChannel {

	private Long id;
	private String name;
	private String displayName;
	private int maxCapacity;
	private Date dateCreated;
	private boolean active;
	
	public ChatChannel(Long id, String name, String displayName) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.displayName = displayName;
		this.maxCapacity = 30;
		this.dateCreated = new Date();
		this.active = true;
	}
	
	public ChatChannel() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
