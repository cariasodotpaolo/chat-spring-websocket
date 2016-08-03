/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gochat.dao;

import com.gochat.entity.UserEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * @author mpcariaso
 *
 */
public interface UserDao {

    UserEntity getUserByUserName(String userName) throws SQLException;

	List<UserEntity> getUserChatList(String userName) throws SQLException;

	UserEntity getUserById(long id) throws SQLException;
    
}
