package com.gochat.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.gochat.entity.UserEntity;

/**
 * @author mpcariaso
 *
 * This class is a substitute for a real database
 */
@Repository
@Scope("singleton")
public class UserDB {
	
	//users table
	private Map<String,UserEntity> users;
	
	//chat list table
	private Map<String,List<UserEntity>> userChatList;
	
	private int userSequenceId;
	
	@Resource(name = "bCryptPasswordEncoder")
	PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void init() {
		
		/*
		 * create initial users
		 */
		//passwordEncoder = new BCryptPasswordEncoder();
		users = new HashMap<>();
		userChatList = new HashMap<>();
		
		userSequenceId = 1;
		
		UserEntity user1 = new UserEntity();
		String userName1 = "john_smith";
		user1.setUserId(userSequenceId++);
		user1.setFullName("John Smith");
		user1.setUserName(userName1);
		user1.setPassword(passwordEncoder.encode("john_smith"));
		user1.setRoles(new ArrayList<>(Arrays.asList("USER")));
		
		UserEntity user2 = new UserEntity();
		String userName2 = "linda_mcbride";
		user2.setUserId(userSequenceId++);
		user2.setFullName("Linda McBride");
		user2.setUserName(userName2);
		user2.setPassword(passwordEncoder.encode("linda_mcbride"));
		user2.setRoles(new ArrayList<>(Arrays.asList("USER")));
		
		UserEntity user3 = new UserEntity();
		String userName3 = "marty_grant";
		user3.setUserId(userSequenceId++);
		user3.setFullName("Marty Grant");
		user3.setUserName(userName3);
		user3.setPassword(passwordEncoder.encode("marty_grant"));
		user3.setRoles(new ArrayList<>(Arrays.asList("USER")));
		
		UserEntity user4 = new UserEntity();
		String userName4 = "mary_loggins";
		user4.setUserId(userSequenceId++);
		user4.setFullName("Mary Loggins");
		user4.setUserName(userName4);
		user4.setPassword(passwordEncoder.encode("mary_loggins"));
		user4.setRoles(new ArrayList<>(Arrays.asList("USER")));
		
		UserEntity user5 = new UserEntity();
		String userName5 = "peter_jones";
		user5.setUserId(userSequenceId++);
		user5.setFullName("Peter Jones");
		user5.setUserName(userName5);
		user5.setPassword(passwordEncoder.encode("peter_jones"));
		user5.setRoles(new ArrayList<>(Arrays.asList("USER")));
		
		users.put(userName1, user1);
		users.put(userName2, user2);
		users.put(userName3, user3);
		users.put(userName4, user4);
		users.put(userName5, user5);
			    
	    userChatList.put(userName1, new ArrayList<>(Arrays.asList(user2,user3,user4,user5)));
	    userChatList.put(userName2, new ArrayList<>(Arrays.asList(user1,user3,user5)));
	    userChatList.put(userName3, new ArrayList<>(Arrays.asList(user1,user2,user4)));
	    userChatList.put(userName4, new ArrayList<>(Arrays.asList(user1,user3)));
	    userChatList.put(userName5, new ArrayList<>(Arrays.asList(user1,user2)));
	}
	
	public UserEntity getUser(String userName) {
		
		return users.get(userName);
	}
	
	public UserEntity getUserById(long id) {
		
		UserEntity user = null;
		
		for(Entry<String,UserEntity> entry: users.entrySet()) {
			if (entry.getValue().getUserId() == id) {
				user = entry.getValue();
				break;
			}
		}
		
		return user;
	}
	
	public synchronized UserEntity addUser(UserEntity user) {
		
		user.setUserId(++userSequenceId);
		
		users.put(user.getUserName(), user);
		
		return user;
		
	}
	
	public synchronized List<UserEntity> getChatList(String userName) {
		
		return userChatList.get(userName);
	}

}
