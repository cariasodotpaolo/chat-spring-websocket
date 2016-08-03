package com.gochat.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import com.gochat.dao.UserDao;
import com.gochat.domain.ChatUser;
import com.gochat.entity.UserEntity;

/**
 * @author mpcariaso
 *
 */
@Service
public class UserServiceImpl implements UserService {

	//private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserDao userDao;
	
	@Autowired
	private OnlineService onlineService;

	/* (non-Javadoc)
	 * @see com.gochat.service.UserService#getUserByUserName(java.lang.String)
	 */
	@Override
	public ChatUser getUserByUserName(String userName) throws SQLException {
				
		ChatUser chatUser = null;
		
		UserEntity userEntity = userDao.getUserByUserName(userName);
		
		if (userEntity != null) {
			chatUser = new ChatUser(userEntity.getUserId(),userEntity.getUserName(), userEntity.getFullName());
		}
		
		return chatUser;		
	}

	/* (non-Javadoc)
	 * @see com.gochat.service.UserService#getUserChatList(java.lang.String)
	 */
	@Override
	public List<ChatUser> getUserChatList(String userName) throws SQLException {
		
		List<ChatUser> chatUsers = new ArrayList<>();
		
		List<UserEntity> userEntities = userDao.getUserChatList(userName);
		
		for(UserEntity entity: userEntities) {			
			ChatUser chatUser = new ChatUser(entity.getUserId(), entity.getUserName(), entity.getFullName());
			
			if (onlineService.isUserOnline(entity.getUserName())) {
				chatUser.setOnline(true);
			}
			
			chatUsers.add(chatUser);
		}
		
		return chatUsers;
	}

	/* (non-Javadoc)
	 * @see com.gochat.service.UserService#getUserById(long)
	 */
	@Override
	public ChatUser getUserById(long id) throws SQLException {
		
		ChatUser chatUser = new ChatUser();		
		UserEntity userEntity = userDao.getUserById(id);
		
		if (userEntity != null) {
			chatUser.setFullName(userEntity.getFullName());
			chatUser.setUserId(userEntity.getUserId());
			chatUser.setUserName(userEntity.getUserName());
		}
		
		return chatUser;
	}
	
	
}
