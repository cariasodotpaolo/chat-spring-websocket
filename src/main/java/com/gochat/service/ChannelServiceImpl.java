package com.gochat.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gochat.cache.ChannelCache;
import com.gochat.domain.ChatChannel;

/**
 * @author mpcariaso
 *
 */
@Service
public class ChannelServiceImpl implements ChannelService {
	
	@Resource
	private ChannelCache cache;
	
	/* (non-Javadoc)
	 * @see com.gochat.service.ChannelService#getChannels()
	 */
	@Override
	public List<ChatChannel> getChannels() {
		
		return cache.getChannels();
	}

}
