package com.gochat.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.gochat.domain.ChatUser;

/**
 * @author mpcariaso
 *
 * This is a substitute for caching engines such as ehcache or memcache
 */
@Repository
@Scope("singleton")
public class OnlineCache {
	
	private Map<String, ChatUser> onlineUsers;
	
	@PostConstruct
	public void initCache() {
		// TODO Auto-generated constructor stub
		onlineUsers = new HashMap<>();
	}
	
	public void setOnline(ChatUser chatUser) {
		
		synchronized (this) {
			onlineUsers.put(chatUser.getUserName(), chatUser);
		}		
	}
	
	public ChatUser getUser(String userName) {
		
		synchronized (this) {
			return onlineUsers.get(userName);
		}
		
	}
	
	public void setOffline(String userName) {
		
		synchronized (this) {
			ChatUser user = getUser(userName);
			
			if(user != null) {
				user.setOnline(false);
			}
			
			onlineUsers.remove(userName);
		}		
	}
}
