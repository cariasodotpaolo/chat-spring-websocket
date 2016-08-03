package com.gochat.service;

import com.gochat.domain.ChatUser;

/**
 * @author mpcariaso
 *
 */
public interface OnlineService {

	public void setOnline(ChatUser chatUser);

	public boolean isUserOnline(String userName);

	public void setOffline(String userName);

}