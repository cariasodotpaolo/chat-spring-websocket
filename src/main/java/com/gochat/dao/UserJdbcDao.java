package com.gochat.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gochat.entity.UserEntity;

/**
 * @author mpcariaso
 *
 */
@Repository
public class UserJdbcDao implements UserDao {
    
	private static final Logger logger = LoggerFactory.getLogger(UserJdbcDao.class);
    
    @Autowired
    private UserDB userDB;  
    
    @Override
    public UserEntity getUserByUserName(String userName) throws SQLException {      
        
    	try {
    		return userDB.getUser(userName);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		throw new SQLException(e.getMessage());
    	}
    }
    
    @Override
	public UserEntity getUserById(long id) throws SQLException {      
        
    	try {
    		return userDB.getUserById(id);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		throw new SQLException(e.getMessage());
    	}
    }
    
    @Override
	public List<UserEntity> getUserChatList(String userName) throws SQLException {
    	
    	try {
    	return userDB.getChatList(userName);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		throw new SQLException(e.getMessage());
    	}
    }
}
