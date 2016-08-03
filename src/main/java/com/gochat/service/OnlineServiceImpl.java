package com.gochat.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gochat.cache.OnlineCache;
import com.gochat.domain.ChatUser;

/**
 * @author mpcariaso
 *
 */
@Service
public class OnlineServiceImpl implements OnlineService {

	@Resource
	private OnlineCache cache;

	/* (non-Javadoc)
	 * @see com.gochat.service.OnlineService#setOnline(com.gochat.domain.ChatUser)
	 */
	@Override
	public void setOnline(ChatUser chatUser) {
		chatUser.setOnline(true);
		cache.setOnline(chatUser);
	}

	/* (non-Javadoc)
	 * @see com.gochat.service.OnlineService#getUser(java.lang.String)
	 */
	@Override
	public boolean isUserOnline(String userName) {
		
		ChatUser user = cache.getUser(userName);
		
		if(user != null && user.isOnline()) {
			return true;
		} else return false;
	}

	/* (non-Javadoc)
	 * @see com.gochat.service.OnlineService#setOffline(java.lang.String)
	 */
	@Override
	public void setOffline(String userName) {		
		cache.setOffline(userName);
	}
	
	
}
