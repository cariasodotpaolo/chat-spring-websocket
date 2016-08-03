package com.gochat.service;

import java.sql.SQLException;
import java.util.List;

import com.gochat.domain.ChatUser;

/**
 * @author mpcariaso
 *
 */
public interface UserService {

	ChatUser getUserByUserName(String userName) throws SQLException;

	List<ChatUser> getUserChatList(String userName) throws SQLException;

	ChatUser getUserById(long id) throws SQLException;

}